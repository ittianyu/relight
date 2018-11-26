package com.ittianyu.relight.widget.stateful.lceerm;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.widget.FrameLayout;

import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.stateful.AsyncState;
import com.ittianyu.relight.widget.stateful.LifecycleStatefulWidget;
import com.ittianyu.relight.widget.stateful.lcee.Status;

public abstract class LceermWidget extends LifecycleStatefulWidget<FrameLayout, FrameWidget> {
    protected Status status = Status.Loading;
    protected LoadType loadType = LoadType.FirstLoad;
    private Widget loading;
    private Widget content;
    private Widget empty;
    private Widget error;
    protected Throwable lastError;
    private Runnable loadingTask = () -> {
        try {
            this.status = onLoadData();
        } catch (Exception e) {
            lastError = e;
            this.status = Status.Error;
        }
    };
    private Runnable loadingMoreTask = () -> {
        try {
            this.status = onLoadMore();
        } catch (Exception e) {
            lastError = e;
            this.status = Status.Error;
        }
    };

    public LceermWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    abstract protected Widget renderLoading();

    abstract protected Widget renderContent();

    abstract protected Widget renderEmpty();

    abstract protected Widget renderError();

    abstract protected void onRefreshError(Throwable throwable);

    abstract protected void onRefreshComplete();

    abstract protected void onLoadMoreError(Throwable throwable);

    abstract protected void onLoadMoreEmpty();

    abstract protected void onLoadMoreComplete();

    /**
     * default cache the l c e e widget.
     * If false, will create a new widget when status changed
     *
     * @return
     */
    protected boolean cache() {
        return true;
    }

    @Override
    protected AsyncState<FrameWidget> createState(Context context) {
        return StateUtils.create(new FrameWidget(context, lifecycle));
    }

    @Override
    public void initWidget(FrameWidget widget) {
        widget.matchParent();
    }

    @Override
    public void updateWidget(FrameWidget widget) {
        updateWidget();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWidget();
    }

    private void updateWidget() {
        if (loadType == LoadType.Refresh && status != Status.Empty) {
            switch (status) {
                case Error:
                    onRefreshError(lastError);
                    onRefreshComplete();
                    break;
                case Content:
                    widget.updateView(widget.render());
                    onRefreshComplete();
                    break;
            }
            onStatusChanged(status, loadType);
            return;
        }
        // call complete when refresh empty
        if (loadType == LoadType.Refresh) {
            onRefreshComplete();
        }

        if (loadType == LoadType.LoadMore) {
            switch (status) {
                case Empty:
                    onLoadMoreEmpty();
                    onLoadMoreComplete();
                    break;
                case Error:
                    onLoadMoreError(lastError);
                    onLoadMoreComplete();
                    break;
                case Content:
                    widget.updateView(widget.render());
                    onLoadMoreComplete();
                    break;
            }
            onStatusChanged(status, loadType);
            return;
        }


        FrameWidget frameWidget = this.widget;
        frameWidget.removeAllChildren();
        switch (status) {
            case Loading: {
                // 如果不缓存 或者 loading == null 时，都直接调用 render 去创建一个新的
                if (!cache() || loading == null)
                    loading = renderLoading();
                frameWidget.addChild(loading);
                break;
            }
            case Content: {
                // 如果不缓存 或者 content == null 时，都直接调用 render 去创建一个新的
                if (!cache() || content == null)
                    content = renderContent();
                frameWidget.addChild(content);
                break;
            }
            case Empty: {
                // 如果不缓存 或者 empty == null 时，都直接调用 render 去创建一个新的
                if (!cache() || empty == null)
                    empty = renderEmpty();
                frameWidget.addChild(empty);
                break;
            }
            case Error: {
                // 如果不缓存 或者 error == null 时，都直接调用 render 去创建一个新的
                if (!cache() || error == null)
                    error = renderError();
                frameWidget.addChild(error);
                break;
            }
        }
        onStatusChanged(status, loadType);
    }

    public void reload() {
        showLoading(LoadType.FirstLoad);
    }

    public void refresh() {
        showLoading(LoadType.Refresh);
    }

    public void loadMore() {
        showLoading(LoadType.LoadMore);
    }

    public void showLoading(LoadType loadType) {
        updateStatus(Status.Loading, loadType);
    }

    public void showContent(LoadType loadType) {
        updateStatus(Status.Content, loadType);
    }

    public void showEmpty(LoadType loadType) {
        updateStatus(Status.Empty, loadType);
    }

    public void showError(LoadType loadType) {
        updateStatus(Status.Error, loadType);
    }

    public void updateStatus(Status status, LoadType loadType) {
        setState(() -> {
            this.status = status;
            this.loadType = loadType;
        });
    }

    protected void onStatusChanged(Status status, LoadType loadType) {
        if (status == Status.Loading) {
            switch (loadType) {
                case FirstLoad:
                case Refresh:
                    setStateAsync(loadingTask);
                    break;
                case LoadMore:
                    setStateAsync(loadingMoreTask);
                    break;
            }
        }
    }

    /**
     * Running in non-main thread.
     * If some showError happen, it will auto set showError status
     *
     * @return return the next status after data load complete
     */
    abstract protected Status onLoadData() throws Exception;

    /**
     * Running in non-main thread.
     * If some showError happen, it will auto set showError status
     *
     * @return return the next status after data load complete
     */
    abstract protected Status onLoadMore() throws Exception;


}
