package com.ittianyu.relight.widget.stateful.lcee;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.widget.FrameLayout;

import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.stateful.AsyncState;
import com.ittianyu.relight.widget.stateful.LifecycleStatefulWidget;

public abstract class LceeWidget extends LifecycleStatefulWidget<FrameLayout, FrameWidget> {
    protected Status status = Status.Loading;
    private Widget loading;
    private Widget content;
    private Widget empty;
    private Widget error;

    public LceeWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    abstract protected Widget renderLoading();
    abstract protected Widget renderContent();
    abstract protected Widget renderEmpty();
    abstract protected Widget renderError();

    /**
     * default cache the l c e e widget.
     * If false, will create a new widget when status changed
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
        onStatusChanged(status);
    }

    public void reload() {
        showLoading();
    }

    public void showLoading() {
        updateStatus(Status.Loading);
    }

    public void showContent() {
        updateStatus(Status.Content);
    }

    public void showEmpty() {
        updateStatus(Status.Empty);
    }

    public void showError() {
        updateStatus(Status.Error);
    }

    public void updateStatus(Status status) {
        setState(() -> {
            this.status = status;
        });
    }

    protected void onStatusChanged(Status status) {
        if (status == Status.Loading) {
            setStateAsync(() -> {
                try {
                    this.status = onLoadData();
                } catch (Exception e) {
                    e.printStackTrace();
                    this.status = Status.Error;
                }
            });
        }
    }

    /**
     * Running in non-main thread.
     * If some showError happen, it will auto set showError status
     * @return return the next status after data load complete
     */
    abstract protected Status onLoadData() throws Exception;

}
