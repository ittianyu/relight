package com.ittianyu.relight.learn.widget;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.ittianyu.relight.MainApplication;
import com.ittianyu.relight.learn.bean.GirlItemBean;
import com.ittianyu.relight.learn.bean.GirlResponseBean;
import com.ittianyu.relight.learn.repository.GirlRepository;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.stateful.lcee.Status;
import com.ittianyu.relight.widget.stateful.lceerm.LceermWidget;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public class GirlLceermWidget extends LceermWidget implements OnRefreshListener, OnLoadMoreListener {
    private GirlRefreshWidget refreshWidget;
    private List<GirlItemBean> data;
    private int pageIndex = 1;
    private View.OnClickListener reloadListener;
    private String errorReason = null;

    public GirlLceermWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
        reloadListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        };
    }

    @Override
    protected Widget renderLoading() {
        return new GirlLoadingWidget(context, lifecycle, "加载中");
    }

    @Override
    protected Widget renderContent() {
        refreshWidget = new GirlRefreshWidget(context, lifecycle, data);
        refreshWidget.setOnRefreshListener(this).setOnLoadMoreListener(this);
        return refreshWidget;
    }

    @Override
    protected Widget renderEmpty() {
        return new GirlEmptyWidget(context, lifecycle, "暂无数据", reloadListener);
    }

    @Override
    protected Widget renderError() {
        return new GirlErrorWidget(context, lifecycle, "出错了: " + errorReason, reloadListener);
    }

    @Override
    protected void onRefreshError(Throwable throwable) {
        Toast.makeText(MainApplication.getInstance(), "刷新出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRefreshComplete() {
        refreshWidget.finishRefresh(errorReason == null, data);
    }

    @Override
    protected void onLoadMoreError(Throwable throwable) {
        Toast.makeText(MainApplication.getInstance(), "加载出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onLoadMoreEmpty() {
        refreshWidget.setNoMoreData(true);
    }

    @Override
    protected void onLoadMoreComplete() {
        refreshWidget.finishLoadMore(errorReason == null, data);
    }

    @NonNull
    private Status fetchData() {
        GirlResponseBean bean = GirlRepository.getInstance().fetchData(pageIndex);
        if (bean == null) {
            errorReason = "response bean is null";
            return Status.Error;
        }
        if (bean.isError()) {
            errorReason = bean.getErrorReason();
            return Status.Error;
        }
        data = bean.getResults();
        if (data == null || data.size() == 0) {
            return Status.Empty;
        }
        return Status.Content;
    }

    @Override
    protected Status onLoadData() {
        pageIndex = 1;
        return fetchData();
    }

    @Override
    public Status onLoadMore() {
        pageIndex++;
        return fetchData();
    }

    @Override
    public void initWidget(FrameWidget widget) {
        widget.matchParent();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        refreshWidget.setNoMoreData(false);
        refresh();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        loadMore();
    }

}
