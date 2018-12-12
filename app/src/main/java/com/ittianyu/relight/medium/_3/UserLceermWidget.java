package com.ittianyu.relight.medium._3;

import android.accounts.NetworkErrorException;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.ittianyu.relight.common.adapter.UserItemAdapter;
import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.datasource.UserDataSource;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.BaseAndroidWidget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.native_.RecyclerWidget;
import com.ittianyu.relight.widget.native_.SwipeRefreshWidget;
import com.ittianyu.relight.widget.stateful.lcee.CommonEmptyWidget;
import com.ittianyu.relight.widget.stateful.lcee.CommonLoadingWidget;
import com.ittianyu.relight.widget.stateful.lcee.LceeStatus;
import com.ittianyu.relight.widget.stateful.lceerm.LceermWidget;

import java.util.Collections;
import java.util.List;

public class UserLceermWidget extends LceermWidget {
    private List<UserBean> data = Collections.emptyList();
    private SwipeRefreshWidget srw;
    private boolean noMoreData;
    private View.OnClickListener reload = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reload();
        }
    };
    private SwipeRefreshLayout.OnRefreshListener refresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (!refresh()) {
                srw.refreshing(false);
            }
        }
    };

    public UserLceermWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected Widget renderLoading() {
        return new CommonLoadingWidget(context, lifecycle);
    }

    @Override
    protected Widget renderContent() {
        srw = new SwipeRefreshWidget(context, lifecycle,
                new FrameWidget(context, lifecycle,
                        renderRecycler(),
                        renderFab()
                )
        );
        return srw.onRefreshListener(refresh).matchParent();
    }

    @Override
    protected Widget renderEmpty() {
        return new CommonEmptyWidget(context, lifecycle, "No data. Click to reload", reload);
    }

    @Override
    protected Widget renderError() {
        lastError.printStackTrace();
        return new CommonEmptyWidget(context, lifecycle, "Network error. Click to reload", reload);
    }

    @Override
    protected void onRefreshError(Throwable throwable) {
        Toast.makeText(context, "Refresh failed !", Toast.LENGTH_SHORT).show();
        lastError.printStackTrace();
    }

    @Override
    protected void onRefreshComplete() {
        srw.refreshing(false);
    }

    @Override
    protected void onLoadMoreError(Throwable throwable) {
        Toast.makeText(context, "Load more data failed !", Toast.LENGTH_SHORT).show();
        lastError.printStackTrace();
    }

    @Override
    protected void onLoadMoreEmpty() {
        Toast.makeText(context, "No more data !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onLoadMoreComplete() {
        // change load more UI if necessary
    }

    @Override
    protected LceeStatus onLoadData() throws NetworkErrorException {
        noMoreData = false;
        data = UserDataSource.getInstance().getUsersFromRemote();
        if (data.isEmpty())
            return LceeStatus.Empty;
        return LceeStatus.Content;
    }

    @Override
    protected LceeStatus onLoadMore() throws NetworkErrorException {
        data = UserDataSource.getInstance().getUsersFromRemote();
        if (data.isEmpty()) {
            noMoreData = true;
            return LceeStatus.Empty;
        }
        return LceeStatus.Content;
    }

    private RecyclerWidget renderRecycler() {
        return new RecyclerWidget<UserItemAdapter>(context, lifecycle) {
            @Override
            protected void initProps() {
                width = matchParent;
                height = matchParent;
                layoutManager = new LinearLayoutManager(context);
                adapter = new UserItemAdapter(lifecycle);

                // load more
                view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    private int mLastVisibleItemPosition;
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        if (adapter == null || noMoreData || status == LceeStatus.Loading)
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
            public void updateView(RecyclerView view) {
                // attention: must check status before update view data
                if (status != LceeStatus.Content)
                    return;
                switch (loadType) {
                    case FirstLoad:
                    case Refresh:
                        adapter.setData(data);
                        break;
                    case LoadMore:
                        adapter.addData(data);
                        break;
                }
            }
        };
    }

    private BaseAndroidWidget<FloatingActionButton> renderFab() {
        return new BaseAndroidWidget<FloatingActionButton>(context, lifecycle) {
            @Override
            protected void initProps() {
                layoutGravity = Gravity.END | Gravity.BOTTOM;
                margin = dp(16);
                wrapContent();
            }
        }.onClickListener(reload);
    }

}
