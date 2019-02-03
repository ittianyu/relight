package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import com.ittianyu.relight.widget.Widget;

public class HorizontalScrollWidget extends BaseFrameWidget<HorizontalScrollView, HorizontalScrollWidget> {

    public HorizontalScrollWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public HorizontalScrollWidget(Context context, Lifecycle lifecycle, Widget child) {
        super(context, lifecycle, child);
    }

    public HorizontalScrollWidget fillViewport(boolean fillViewport) {
        view.setFillViewport(fillViewport);
        return self();
    }

    public HorizontalScrollWidget smoothScrollingEnabled(boolean smoothScrollingEnabled) {
        view.setSmoothScrollingEnabled(smoothScrollingEnabled);
        return self();
    }

    public HorizontalScrollWidget executeKeyEvent(KeyEvent event) {
        view.executeKeyEvent(event);
        return self();
    }

    public HorizontalScrollWidget requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        view.requestDisallowInterceptTouchEvent(disallowIntercept);
        return self();
    }

    public HorizontalScrollWidget smoothScrollBy(int dx, int dy) {
        view.smoothScrollBy(dx, dy);
        return self();
    }

    public HorizontalScrollWidget smoothScrollTo(int x, int y) {
        view.smoothScrollTo(x, y);
        return self();
    }

    public HorizontalScrollWidget computeScroll() {
        view.computeScroll();
        return self();
    }

    public HorizontalScrollWidget requestChildFocus(View child, View focused) {
        view.requestChildFocus(child, focused);
        return self();
    }

    public HorizontalScrollWidget requestLayout() {
        view.requestLayout();
        return self();
    }

    public HorizontalScrollWidget fling(int velocityY) {
        view.fling(velocityY);
        return self();
    }

    public HorizontalScrollWidget scrollTo(int x, int y) {
        view.scrollTo(x, y);
        return self();
    }

    public HorizontalScrollWidget setOverScrollMode(int mode) {
        view.setOverScrollMode(mode);
        return self();
    }

}
