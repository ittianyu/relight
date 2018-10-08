package com.ittianyu.relight.widget.stateful.lcee;

import android.content.Context;
import android.widget.FrameLayout;

import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.atomic.FrameWidget;
import com.ittianyu.relight.widget.stateful.AsyncState;
import com.ittianyu.relight.widget.stateful.LifecycleStatefulWidget;

import android.arch.lifecycle.Lifecycle;

public abstract class LceeWidget extends LifecycleStatefulWidget<FrameLayout> {
    protected Status status = Status.Loading;

    public LceeWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    abstract protected Widget renderLoading();
    abstract protected Widget renderContent();
    abstract protected Widget renderEmpty();
    abstract protected Widget renderError();


    @Override
    protected AsyncState<FrameLayout> createState(Context context) {
        return new AsyncState<FrameLayout>() {
            @Override
            public Widget<FrameLayout> build(Context context) {
                return new FrameWidget(context, lifecycle).matchParent();
            }

            @Override
            public void update() {
                updateWidget();
            }

        };
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWidget();
    }

    private void updateWidget() {
        FrameWidget frameWidget = (FrameWidget) this.widget;
        frameWidget.removeAllChildren();
        switch (status) {
            case Loading: {
                frameWidget.addChild(renderLoading());
                break;
            }
            case Content: {
                frameWidget.addChild(renderContent());
                break;
            }
            case Empty: {
                frameWidget.addChild(renderEmpty());
                break;
            }
            case Error: {
                frameWidget.addChild(renderError());
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
