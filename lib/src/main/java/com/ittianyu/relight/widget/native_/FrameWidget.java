package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ittianyu.relight.utils.ViewUtils;
import com.ittianyu.relight.widget.Widget;

public class FrameWidget extends ViewGroupWidget<FrameLayout> {

    public FrameWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    @Override
    public FrameLayout createView(Context context) {
        return new FrameLayout(context);
    }

    @Override
    public void updateProps(FrameLayout view) {
        super.updateProps(view);
        for (Widget widget : children) {
            if (widget instanceof BaseAndroidWidget) {
                View v = widget.render();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                if (layoutParams instanceof FrameLayout.LayoutParams) {
                    int layoutGravity = ((BaseAndroidWidget) widget).layoutGravity;
                    ViewUtils.setLayoutGravity((FrameLayout.LayoutParams) layoutParams, v, layoutGravity);
                }
            }
        }
    }

}
