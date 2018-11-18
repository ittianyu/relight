package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ittianyu.relight.utils.ViewUtils;
import com.ittianyu.relight.widget.Widget;

public class LinearWidget extends ViewGroupWidget<LinearLayout> {
    public static final int horizontal = 0;
    public static final int vertical = 1;

    protected int orientation = horizontal;
    protected int gravity = -1;

    public LinearWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    @Override
    public LinearLayout createView(Context context) {
        return new LinearLayout(context);
    }

    public LinearWidget orientation(int orientation) {
        this.orientation = orientation;
        view.setOrientation(orientation);
        return this;
    }

    public LinearWidget gravity(int gravity) {
        this.gravity = gravity;
        view.setGravity(gravity);
        return this;
    }

    @Override
    public void updateProps(LinearLayout view) {
        super.updateProps(view);
        // set orientation
        orientation(orientation);

        gravity(gravity);

        // update children layout params
        for (Widget widget : children) {
            if (widget instanceof BaseAndroidWidget) {
                View v = widget.render();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                if (layoutParams instanceof LinearLayout.LayoutParams) {
                    // layout_gravity
                    int layoutGravity = ((BaseAndroidWidget) widget).layoutGravity;
                    ViewUtils.setLayoutGravity((LinearLayout.LayoutParams) layoutParams, v, layoutGravity);
                    // weight
                    int weight = ((BaseAndroidWidget) widget).weight;
                    if (weight != -1) {
                        ViewUtils.setWeight((LinearLayout.LayoutParams) layoutParams, v, layoutGravity);
                    }
                }
            }
        }
    }

}
