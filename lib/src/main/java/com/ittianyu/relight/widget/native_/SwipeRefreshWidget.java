package com.ittianyu.relight.widget.native_;


import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;

import com.ittianyu.relight.widget.Widget;

public class SwipeRefreshWidget extends ViewGroupWidget<SwipeRefreshLayout, SwipeRefreshWidget> {

    private Boolean enabled;
    private Integer size;
    private Boolean refreshing;
    private int[] colorResIds;
    private int[] colors;
    private Integer distance;
    private SwipeRefreshLayout.OnRefreshListener listener;

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

    @Override
    public void updateProps(SwipeRefreshLayout view) {
        super.updateProps(view);
        if (enabled != null) {
            enabled(enabled);
        }
        if (size != null) {
            size(size);
        }
        if (refreshing != null) {
            refreshing(refreshing);
        }
        if (colorResIds != null) {
            colorSchemeResources(colorResIds);
        }
        if (colors != null) {
            colorSchemeColors(colors);
        }
        if (distance != null) {
            distanceToTriggerSync(distance);
        }
    }

    public SwipeRefreshWidget enabled(Boolean enabled) {
        this.enabled = enabled;
        view.setEnabled(enabled);
        return self();
    }

    public SwipeRefreshWidget size(Integer size) {
        this.size = size;
        view.setSize(size);
        return self();
    }

    public SwipeRefreshWidget refreshing(Boolean refreshing) {
        this.refreshing = refreshing;
        view.setRefreshing(refreshing);
        return self();
    }

    public SwipeRefreshWidget colorSchemeResources(@ColorRes int... colorResIds) {
        this.colorResIds = colorResIds;
        view.setColorSchemeResources(colorResIds);
        return self();
    }

    public SwipeRefreshWidget colorSchemeColors(@ColorInt int... colors) {
        this.colors = colors;
        view.setColorSchemeColors(colors);
        return self();
    }

    public SwipeRefreshWidget distanceToTriggerSync(Integer distance) {
        this.distance = distance;
        view.setDistanceToTriggerSync(distance);
        return self();
    }

    public SwipeRefreshWidget onRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        this.listener = listener;
        view.setOnRefreshListener(listener);
        return self();
    }

    public Boolean isRefreshing() {
        return view.isRefreshing();
    }
}
