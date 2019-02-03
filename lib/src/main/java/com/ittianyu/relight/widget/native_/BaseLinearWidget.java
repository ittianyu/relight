package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.ittianyu.relight.utils.ViewUtils;
import com.ittianyu.relight.widget.ContainerWidget;
import com.ittianyu.relight.widget.Widget;

public abstract class BaseLinearWidget<V extends LinearLayout, T extends BaseLinearWidget> extends ViewGroupWidget<V, T> {
    public static final Integer horizontal = LinearLayout.HORIZONTAL;
    public static final Integer vertical = LinearLayout.VERTICAL;

    public BaseLinearWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    public T orientation(Integer orientation) {
        view.setOrientation(orientation);
        return self();
    }

    public T vertical() {
        return orientation(vertical);
    }

    public T horizontal() {
        return orientation(horizontal);
    }

    public T gravity(Integer gravity) {
        view.setGravity(gravity);
        return self();
    }

    public T weightSum(Float weightSum) {
        view.setWeightSum(weightSum);
        return self();
    }

    @Override
    public void updateProps(V view) {
        super.updateProps(view);
        // update children layout params
        for (Widget widget : children) {
            if (widget instanceof ContainerWidget) {
                widget = ((ContainerWidget) widget).getInnerWidget();
            }
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
