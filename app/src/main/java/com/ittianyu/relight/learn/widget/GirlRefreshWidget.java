package com.ittianyu.relight.learn.widget;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ittianyu.relight.MainApplication;
import com.ittianyu.relight.R;
import com.ittianyu.relight.learn.adapter.GirlListAdapter;
import com.ittianyu.relight.learn.bean.GirlItemBean;
import com.ittianyu.relight.widget.native_.AndroidWidget;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public class GirlRefreshWidget extends AndroidWidget<View> implements BaseQuickAdapter.OnItemClickListener {
    private GirlListAdapter adapter = new GirlListAdapter();
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    public GirlRefreshWidget(Context context, Lifecycle lifecycle, List<GirlItemBean> data) {
        super(context, lifecycle);
        adapter.setNewData(data);
    }

    @Override
    public View createView(Context context) {
        View view = View.inflate(context, R.layout.activity_girl_list, null);
        refreshLayout = view.findViewById(R.id.girl_refresh);
        recyclerView = view.findViewById(R.id.girl_list);
        return view;
    }

    @Override
    public void initView(View view) {
        adapter.setOnItemClickListener(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    public GirlRefreshWidget setOnRefreshListener(OnRefreshListener listener) {
        refreshLayout.setOnRefreshListener(listener);
        return this;
    }

    public GirlRefreshWidget setOnLoadMoreListener(OnLoadMoreListener listener) {
        refreshLayout.setOnLoadMoreListener(listener);
        return this;
    }

    public void finishRefresh(boolean success, List<GirlItemBean> data) {
        adapter.setNewData(data);
        refreshLayout.finishRefresh(success);
    }

    public void finishLoadMore(boolean success, List<GirlItemBean> data) {
        adapter.addData(data);
        refreshLayout.finishLoadMore(success);
    }

    public void setNoMoreData(boolean noMoreData) {
        refreshLayout.setNoMoreData(noMoreData);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(MainApplication.getInstance(), "点击条目" + position, Toast.LENGTH_SHORT).show();
    }

}
