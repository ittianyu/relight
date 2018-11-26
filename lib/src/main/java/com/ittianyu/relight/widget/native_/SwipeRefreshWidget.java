package com.ittianyu.relight.widget.native_;


import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;

import com.ittianyu.relight.widget.Widget;

public class SwipeRefreshWidget extends ViewGroupWidget<SwipeRefreshLayout> {
    private boolean enabled = true;
    private int size = SwipeRefreshLayout.DEFAULT;
    private boolean refreshing;
    private int[] colorResIds;
    private int[] colors;
    private int distance;
    private SwipeRefreshLayout.OnRefreshListener listener;

    public SwipeRefreshWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    @Override
    public SwipeRefreshLayout createView(Context context) {
        return new SwipeRefreshLayout(context);
    }

    @Override
    public void updateProps(SwipeRefreshLayout view) {
        super.updateProps(view);
        enabled(enabled);
        size(size);
        refreshing(refreshing);
        colorSchemeResources(colorResIds);
        colorSchemeColors(colors);
        distanceToTriggerSync(distance);
    }

    public SwipeRefreshWidget enabled(boolean enabled) {
        this.enabled = enabled;
        view.setEnabled(enabled);
        return this;
    }

    public SwipeRefreshWidget size(int size) {
        this.size = size;
        view.setSize(size);
        return this;
    }

    public SwipeRefreshWidget refreshing(boolean refreshing) {
        this.refreshing = refreshing;
        view.setRefreshing(refreshing);
        return this;
    }

    public SwipeRefreshWidget colorSchemeResources(@ColorRes int... colorResIds) {
        if (null != colorResIds) {
            this.colorResIds = colorResIds;
            view.setColorSchemeResources(colorResIds);
        }
        return this;
    }

    public SwipeRefreshWidget colorSchemeColors(@ColorInt int... colors) {
        if (null != colors) {
            this.colors = colors;
            view.setColorSchemeColors(colors);
        }
        return this;
    }

    public SwipeRefreshWidget distanceToTriggerSync(int distance) {
        if (distance != 0) {
            this.distance = distance;
            view.setDistanceToTriggerSync(distance);
        }
        return this;
    }

    public SwipeRefreshWidget onRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        this.listener = listener;
        view.setOnRefreshListener(listener);
        return this;
    }

    public boolean isRefreshing() {
        return view.isRefreshing();
    }
}
