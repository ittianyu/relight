package com.ittianyu.relight.widget.stateful.rm;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;

import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.stateful.state.State;
import com.ittianyu.relight.widget.stateful.StatefulWidget;

public abstract class RmWidget<V extends View, T extends Widget<V>> extends StatefulWidget<V, T> {
    protected RmStatus status = RmStatus.RefreshContent;
    protected Throwable lastError;
    private Runnable loadingTask = new Runnable() {
        @Override
        public void run() {
            try {
                if (status == RmStatus.Refreshing) {
                    status = onLoadData();
                } else {
                    status = onLoadMore();
                }
            } catch (Exception e) {
                lastError = e;
                if (status == RmStatus.Refreshing) {
                    status = RmStatus.RefreshError;
                } else {
                    status = RmStatus.LoadMoreError;
                }
            }
        }
    };

    public RmWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<T> createState(Context context) {
        return StateUtils.create(build(context));
    }

    protected abstract T build(Context context);

    @Override
    public void update() {
        super.update();
        updateWidget();
    }

    private void updateWidget() {
        switch (status) {
            case Refreshing: {
                onRefreshing();
                break;
            }
            case RefreshContent: {
                onRefreshContent();
                break;
            }
            case RefreshEmpty: {
                onRefreshEmpty();
                break;
            }
            case RefreshError: {
                onRefreshError();
                break;
            }
            case LoadingMore: {
                onLoadingMore();
                break;
            }
            case LoadMoreContent: {
                onLoadMoreContent();
                break;
            }
            case LoadMoreEmpty: {
                onLoadMoreEmpty();
                break;
            }
            case LoadMoreError: {
                onLoadMoreError();
                break;
            }
        }
        if (status == RmStatus.RefreshContent ||
                status == RmStatus.RefreshEmpty ||
                status == RmStatus.RefreshError) {
            onRefreshComplete();
        } else if (status == RmStatus.LoadMoreContent ||
                status == RmStatus.LoadMoreEmpty ||
                status == RmStatus.LoadMoreError) {
            onLoadMoreComplete();
        }

        onStatusChanged(status);
    }

    public boolean refresh() {
        return showRefreshing();
    }

    public boolean loadMore() {
        return showLoadingMore();
    }

    public boolean showRefreshing() {
        return updateStatus(RmStatus.Refreshing);
    }

    public boolean showRefreshContent() {
        return updateStatus(RmStatus.RefreshContent);
    }

    public boolean showRefreshEmpty() {
        return updateStatus(RmStatus.RefreshEmpty);
    }

    public boolean showRefreshError() {
        return updateStatus(RmStatus.RefreshError);
    }

    public boolean showLoadingMore() {
        return updateStatus(RmStatus.LoadingMore);
    }

    public boolean showLoadMoreContent() {
        return updateStatus(RmStatus.LoadMoreContent);
    }

    public boolean showLoadMoreEmpty() {
        return updateStatus(RmStatus.LoadMoreEmpty);
    }

    public boolean showLoadMoreError() {
        return updateStatus(RmStatus.LoadMoreError);
    }

    public boolean updateStatus(final RmStatus status) {
        if (status == this.status)
            return false;
        setState(new Runnable() {
            @Override
            public void run() {
                RmWidget.this.status = status;
            }
        });
        return true;
    }

    public boolean isLoading() {
        return status == RmStatus.Refreshing || status == RmStatus.LoadingMore;
    }

    protected void onStatusChanged(RmStatus status) {
        switch (status) {
            case Refreshing:
            case LoadingMore:
                setStateAsync(loadingTask);
                break;
        }
    }

    protected abstract void onRefreshing();

    protected abstract void onRefreshContent();

    protected abstract void onRefreshEmpty();

    protected abstract void onRefreshError();

    protected abstract void onRefreshComplete();

    protected abstract void onLoadingMore();

    protected abstract void onLoadMoreContent();

    protected abstract void onLoadMoreEmpty();

    protected abstract void onLoadMoreError();

    protected abstract void onLoadMoreComplete();

    /**
     * Running in non-main thread.
     * If some error happen, it will auto set RefreshError status
     * @return return the next status after data load complete
     */
    abstract protected RmStatus onLoadData() throws Exception;

    /**
     * Running in non-main thread.
     * If some error happen, it will auto set LoadMoreError status
     * @return return the next status after data load complete
     */
    abstract protected RmStatus onLoadMore() throws Exception;

}
