package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ittianyu.relight.utils.ViewUtils;
import com.ittianyu.relight.widget.Widget;

public class LinearWidget extends ViewGroupWidget<LinearLayout, LinearWidget> {
    public static final Integer horizontal = LinearLayout.HORIZONTAL;
    public static final Integer vertical = LinearLayout.VERTICAL;

    public LinearWidget(Context context, Lifecycle lifecycle) {
        this(context, lifecycle, (Widget) null);
    }

    public LinearWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    @Override
    public LinearLayout createView(Context context) {
        return new LinearLayout(context);
    }

    public LinearWidget orientation(Integer orientation) {
        view.setOrientation(orientation);
        return self();
    }

    public LinearWidget gravity(Integer gravity) {
        view.setGravity(gravity);
        return self();
    }

    @Override
    public void updateProps(LinearLayout view) {
        super.updateProps(view);
        // update children layout params
        for (Widget widget : children) {
            if (widget instanceof BaseAndroidWidget) {
                View v = widget.render();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                if (layoutParams instanceof LinearLayout.LayoutParams) {
                    // layout_gravity
                    Integer layoutGravity = ((BaseAndroidWidget) widget).layoutGravity;
                    if (layoutGravity != null) {
                        ViewUtils.setLayoutGravity((LinearLayout.LayoutParams) layoutParams, v, layoutGravity);
                    }

                    // weight
                    Integer weight = ((BaseAndroidWidget) widget).weight;
                    if (weight != null) {
                        ViewUtils.setWeight((LinearLayout.LayoutParams) layoutParams, v, weight);
                    }
                }
            }
        }
    }

}
