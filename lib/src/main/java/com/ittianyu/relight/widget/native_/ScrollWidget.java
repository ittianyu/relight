package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ScrollView;
import com.ittianyu.relight.widget.Widget;

public class ScrollWidget extends BaseFrameWidget<ScrollView, ScrollWidget> {

    public ScrollWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public ScrollWidget(Context context, Lifecycle lifecycle, Widget child) {
        super(context, lifecycle, child);
    }

    public ScrollWidget fillViewport(boolean fillViewport) {
        view.setFillViewport(fillViewport);
        return self();
    }

    public ScrollWidget smoothScrollingEnabled(boolean smoothScrollingEnabled) {
        view.setSmoothScrollingEnabled(smoothScrollingEnabled);
        return self();
    }

    public ScrollWidget executeKeyEvent(KeyEvent event) {
        view.executeKeyEvent(event);
        return self();
    }

    public ScrollWidget requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        view.requestDisallowInterceptTouchEvent(disallowIntercept);
        return self();
    }

    public ScrollWidget smoothScrollBy(int dx, int dy) {
        view.smoothScrollBy(dx, dy);
        return self();
    }

    public ScrollWidget smoothScrollTo(int x, int y) {
        view.smoothScrollTo(x, y);
        return self();
    }

    public ScrollWidget computeScroll() {
        view.computeScroll();
        return self();
    }

    public ScrollWidget requestChildFocus(View child, View focused) {
        view.requestChildFocus(child, focused);
        return self();
    }

    public ScrollWidget requestLayout() {
        view.requestLayout();
        return self();
    }

    public ScrollWidget fling(int velocityY) {
        view.fling(velocityY);
        return self();
    }

    public ScrollWidget scrollTo(int x, int y) {
        view.scrollTo(x, y);
        return self();
    }

    public ScrollWidget overScrollMode(int mode) {
        view.setOverScrollMode(mode);
        return self();
    }

}
