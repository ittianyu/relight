package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

public class RecyclerWidget<V extends RecyclerView.Adapter> extends BaseAndroidWidget<RecyclerView, RecyclerWidget> {
    public RecyclerView.LayoutManager layoutManager;
    public V adapter;

    public RecyclerWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public RecyclerWidget layoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        view.setLayoutManager(layoutManager);
        return self();
    }

    public RecyclerWidget adapter(V adapter) {
        this.adapter = adapter;
        view.setAdapter(adapter);
        return self();
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
        if (null != layoutManager)
            view.setLayoutManager(layoutManager);
        if (null != adapter)
            view.setAdapter(adapter);
    }
}
