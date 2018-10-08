package com.ittianyu.relight.widget.atomic;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

public class RecyclerWidget extends BaseAndroidWidget<RecyclerView> {
    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView.Adapter adapter;

    public RecyclerWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public RecyclerWidget layoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        view.setLayoutManager(layoutManager);
        return this;
    }

    public RecyclerWidget adapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        view.setAdapter(adapter);
        return this;
    }

    @Override
    public RecyclerView createView(Context context) {
        return new RecyclerView(context);
    }

    @Override
    protected void initProps() {
    }

    @Override
    public void updateProps(RecyclerView view) {
        super.updateProps(view);
        view.setLayoutManager(layoutManager);
        view.setAdapter(adapter);
    }
}
