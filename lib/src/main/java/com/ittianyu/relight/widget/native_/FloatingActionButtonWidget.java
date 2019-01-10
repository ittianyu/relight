package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;

public class FloatingActionButtonWidget extends BaseImageWidget<FloatingActionButton, FloatingActionButtonWidget> {

    public FloatingActionButtonWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    public void updateProps(FloatingActionButton view) {
        super.updateProps(view);
    }

    public FloatingActionButtonWidget size(Integer size) {
        view.setSize(size);
        return self();
    }

    public FloatingActionButtonWidget backgroundTintList(ColorStateList list) {
        view.setBackgroundTintList(list);
        return self();
    }

    public FloatingActionButtonWidget rippleColorList(ColorStateList list) {
        view.setRippleColor(list);
        return self();
    }

    public FloatingActionButtonWidget rippleColor(Integer color) {
        view.setRippleColor(color);
        return self();
    }

    public FloatingActionButtonWidget hide() {
        return this.hide(null);
    }

    public FloatingActionButtonWidget hide(FloatingActionButton.OnVisibilityChangedListener listener) {
        view.hide(listener);
        return self();
    }

    public FloatingActionButtonWidget expanded(boolean expanded) {
        view.setExpanded(expanded);
        return self();
    }

    public boolean isExpanded() {
        return view.isExpanded();
    }

}
