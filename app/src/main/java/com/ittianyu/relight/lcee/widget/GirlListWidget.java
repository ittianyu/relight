package com.ittianyu.relight.lcee.widget;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ittianyu.relight.MainApplication;
import com.ittianyu.relight.R;
import com.ittianyu.relight.lcee.adapter.GirlListAdapter;
import com.ittianyu.relight.widget.native_.AndroidWidget;

public class GirlListWidget extends AndroidWidget<View> implements BaseQuickAdapter.OnItemClickListener {
    private GirlListAdapter adapter = new GirlListAdapter();
    private GirlListLceeWidget lceeWidget;

    public GirlListWidget(Context context, Lifecycle lifecycle, GirlListLceeWidget lceeWidget) {
        super(context, lifecycle);
        this.lceeWidget = lceeWidget;
    }

    @Override
    public View createView(Context context) {
        return View.inflate(context, R.layout.widget_girl_list, null);
    }

    @Override
    public void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.girl_list);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        if (lceeWidget.getPageIndex() == 1) {
            adapter.setNewData(lceeWidget.getData());
        } else {
            adapter.addData(lceeWidget.getData());
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(MainApplication.getInstance(), "点击条目" + position, Toast.LENGTH_SHORT).show();
    }

}
