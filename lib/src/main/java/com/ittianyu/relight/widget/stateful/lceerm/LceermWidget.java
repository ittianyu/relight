package com.ittianyu.relight.widget.stateful.lceerm;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.WorkerThread;
import android.widget.FrameLayout;

import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateful.lcee.LceeStatus;
import com.ittianyu.relight.widget.stateful.state.State;

public abstract class LceermWidget extends StatefulWidget<FrameLayout, FrameWidget> {
    protected LceeStatus status = LceeStatus.Loading;
    protected LoadType loadType = LoadType.FirstLoad;
    private Widget loading;
    private Widget content;
    private Widget empty;
    private Widget error;
    protected Throwable lastError;
    private Runnable loadingTask = new Runnable() {
        @Override
        public void run() {
            try {
                switch (loadType) {
                    case Refresh:
                    case FirstLoad:
                        status = onLoadData();
                        break;
                    case LoadMore:
                        status = onLoadMore();
                        break;
                }
            } catch (Exception e) {
                lastError = e;
                status = LceeStatus.Error;
            }
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

    protected void onRefreshEmpty() {
        updateWidgetFirstLoad();
    }

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
    protected State<FrameWidget> createState(Context context, Lifecycle lifecycle) {
        return StateUtils.create(new FrameWidget(context, lifecycle));
    }

    @Override
    public void initWidget(FrameWidget widget) {
        widget.matchParent();
    }

    @Override
    public void update() {
        updateWidget();
        super.update();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWidget();
    }

    private void updateWidget() {
        if (loadType == LoadType.Refresh) {
            updateWidgetRefresh();
            return;
        }
        if (loadType == LoadType.LoadMore) {
            updateWidgetLoadMore();
            return;
        }
        updateWidgetFirstLoad();
    }

    private void updateWidgetLoadMore() {
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
                onLoadMoreComplete();
                break;
        }
        onStatusChanged(status, loadType);
    }

    private void updateWidgetRefresh() {
        // call complete when refresh empty
        if (status == LceeStatus.Empty) {
            onRefreshEmpty();
            onRefreshComplete();
            return;
        }

        switch (status) {
            case Error:
                onRefreshError(lastError);
                onRefreshComplete();
                break;
            case Content:
                onRefreshComplete();
                break;
        }
        onStatusChanged(status, loadType);
    }

    private void updateWidgetFirstLoad() {
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

    public boolean reload() {
        return showLoading(LoadType.FirstLoad);
    }

    public boolean refresh() {
        if (status != LceeStatus.Content) {
            throw new IllegalStateException("you can only call refresh when current status is Content !");
        }
        return showLoading(LoadType.Refresh);
    }

    public boolean loadMore() {
        return showLoading(LoadType.LoadMore);
    }

    public boolean showLoading(LoadType loadType) {
        return updateStatus(LceeStatus.Loading, loadType);
    }

    public boolean showContent(LoadType loadType) {
        return updateStatus(LceeStatus.Content, loadType);
    }

    public boolean showEmpty(LoadType loadType) {
        return updateStatus(LceeStatus.Empty, loadType);
    }

    public boolean showError(LoadType loadType) {
        return updateStatus(LceeStatus.Error, loadType);
    }

    public boolean updateStatus(final LceeStatus status, final LoadType loadType) {
        // don't allow update with same status.
        // For example, we don't allow loadMore when refreshing
        if (status == this.status)
            return false;
        setState(new Runnable() {
            @Override
            public void run() {
                LceermWidget.this.status = status;
                LceermWidget.this.loadType = loadType;
            }
        });
        return true;
    }

    @CallSuper
    protected void onStatusChanged(LceeStatus status, LoadType loadType) {
        if (status == LceeStatus.Loading) {
            setStateAsync(loadingTask);
        }
    }

    /**
     * Running in non-main thread.
     * If some showError happen, it will auto set showError status
     *
     * @return return the next status after data load complete
     */
    @WorkerThread
    abstract protected LceeStatus onLoadData() throws Exception;

    /**
     * Running in non-main thread.
     * If some showError happen, it will auto set showError status
     *
     * @return return the next status after data load complete
     */
    @WorkerThread
    abstract protected LceeStatus onLoadMore() throws Exception;


}
