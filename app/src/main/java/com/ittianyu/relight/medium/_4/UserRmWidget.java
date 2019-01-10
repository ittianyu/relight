package com.ittianyu.relight.medium._4;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ittianyu.relight.common.adapter.UserItemAdapter;
import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.datasource.UserDataSource;
import com.ittianyu.relight.widget.native_.FloatingActionButtonWidget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.native_.RecyclerWidget;
import com.ittianyu.relight.widget.native_.SwipeRefreshWidget;
import com.ittianyu.relight.widget.stateful.rm.RmWidget;
import com.ittianyu.relight.widget.stateful.rm.RmStatus;

import java.util.Collections;
import java.util.List;

public class UserRmWidget extends RmWidget<FrameLayout, FrameWidget> {
    private SwipeRefreshWidget srw;
    private FloatingActionButtonWidget fabWidget;

    private List<UserBean> data = Collections.emptyList();
    private boolean noMoreData;

    private SwipeRefreshLayout.OnRefreshListener refresh = () -> {
        if (!refresh()) {
            srw.refreshing(false);
        }
    };
    private View.OnClickListener reloadListener;
    private Runnable showEmpty;


    public UserRmWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected FrameWidget build(Context context) {
        srw = new SwipeRefreshWidget(context, lifecycle,
            renderRecycler()
        ).matchParent();
        return new FrameWidget(context, lifecycle,
            srw,
            renderFab()
        );
    }

    @Override
    public void initWidget(FrameWidget widget) {
        srw.onRefreshListener(refresh).matchParent();
    }

    private RecyclerWidget renderRecycler() {
        return new RecyclerWidget<UserItemAdapter>(context, lifecycle) {
            @Override
            protected void initProps() {
                width = matchParent;
                height = matchParent;
                layoutManager(new LinearLayoutManager(context));
                adapter = new UserItemAdapter(lifecycle);

                // load more
                view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    private int mLastVisibleItemPosition;
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        if (adapter == null || noMoreData || isLoading())
                            return;
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof LinearLayoutManager) {
                            mLastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        }
                        if (newState == RecyclerView.SCROLL_STATE_IDLE
                                && mLastVisibleItemPosition + 1 == adapter.getItemCount()) {
                            loadMore();
                        }
                    }
                });
            }

            @Override
            public void update() {
                super.update();
                if (status == RmStatus.RefreshContent) {
                    adapter.setData(data);
                } else if (status == RmStatus.LoadMoreContent) {
                    adapter.addData(data);
                }
            }
        };
    }

    private FloatingActionButtonWidget renderFab() {
        fabWidget = new FloatingActionButtonWidget(context, lifecycle)
            .wrapContent()
            .layoutGravity(Gravity.END | Gravity.BOTTOM)
            .margin(16.f)
            .onClick(reloadListener);
        return fabWidget;
    }

    @Override
    protected void onRefreshing() {
        srw.refreshing(true);
    }

    @Override
    protected void onRefreshContent() {

    }

    @Override
    protected void onRefreshEmpty() {
        if (showEmpty != null) {
            showEmpty.run();
        }
    }

    @Override
    protected void onRefreshError() {
        Toast.makeText(context, "Refresh failed !", Toast.LENGTH_SHORT).show();
        lastError.printStackTrace();
    }

    @Override
    protected void onRefreshComplete() {
        srw.refreshing(false);
    }

    @Override
    protected void onLoadingMore() {

    }

    @Override
    protected void onLoadMoreContent() {

    }

    @Override
    protected void onLoadMoreEmpty() {
        Toast.makeText(context, "No more data !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onLoadMoreError() {
        Toast.makeText(context, "Load more data failed !", Toast.LENGTH_SHORT).show();
        lastError.printStackTrace();
    }

    @Override
    protected void onLoadMoreComplete() {
        // change load more UI if necessary
    }

    @Override
    public RmStatus onLoadData() throws Exception {
        noMoreData = false;
        data = UserDataSource.getInstance().getUsersFromRemote();
        if (data.isEmpty())
            return RmStatus.RefreshEmpty;
        return RmStatus.RefreshContent;
    }

    @Override
    protected RmStatus onLoadMore() throws Exception {
        data = UserDataSource.getInstance().getUsersFromRemote();
        if (data.isEmpty()) {
            noMoreData = true;
            return RmStatus.LoadMoreEmpty;
        }
        return RmStatus.LoadMoreContent;
    }

    public UserRmWidget onReloadListener(View.OnClickListener reloadListener) {
        this.reloadListener = reloadListener;
        if (fabWidget != null) {
            fabWidget.onClickListener(reloadListener);
        }
        return this;
    }

    public UserRmWidget showEmpty(Runnable runnable) {
        this.showEmpty = runnable;
        return this;
    }
}
