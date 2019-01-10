package com.ittianyu.relight.widget.native_;


import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;

import com.ittianyu.relight.widget.Widget;

public class SwipeRefreshWidget extends ViewGroupWidget<SwipeRefreshLayout, SwipeRefreshWidget> {

    public SwipeRefreshWidget(Context context, Lifecycle lifecycle) {
        this(context, lifecycle, (Widget) null);
    }

    public SwipeRefreshWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    @Override
    public SwipeRefreshLayout createView(Context context) {
        return new SwipeRefreshLayout(context);
    }

    public SwipeRefreshWidget enabled(Boolean enabled) {
        view.setEnabled(enabled);
        return self();
    }

    public SwipeRefreshWidget size(Integer size) {
        view.setSize(size);
        return self();
    }

    public SwipeRefreshWidget refreshing(Boolean refreshing) {
        view.setRefreshing(refreshing);
        return self();
    }

    public SwipeRefreshWidget colorSchemeResources(@ColorRes int... colorResIds) {
        view.setColorSchemeResources(colorResIds);
        return self();
    }

    public SwipeRefreshWidget colorSchemeColors(@ColorInt int... colors) {
        view.setColorSchemeColors(colors);
        return self();
    }

    public SwipeRefreshWidget distanceToTriggerSync(Integer distance) {
        view.setDistanceToTriggerSync(distance);
        return self();
    }

    public SwipeRefreshWidget onRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        view.setOnRefreshListener(listener);
        return self();
    }

    public Boolean isRefreshing() {
        return view.isRefreshing();
    }
}
