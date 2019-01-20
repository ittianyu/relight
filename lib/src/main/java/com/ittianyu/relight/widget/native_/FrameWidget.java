package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ittianyu.relight.utils.ViewUtils;
import com.ittianyu.relight.widget.ContainerWidget;
import com.ittianyu.relight.widget.Widget;

public class FrameWidget extends ViewGroupWidget<FrameLayout, FrameWidget> {

    public FrameWidget(Context context, Lifecycle lifecycle) {
        this(context, lifecycle, (Widget) null);
    }

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
            while (widget instanceof ContainerWidget) {
                widget = ((ContainerWidget) widget).getInnerWidget();
            }
            if (widget instanceof BaseAndroidWidget) {
                View v = widget.render();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                if (layoutParams instanceof FrameLayout.LayoutParams) {
                    Integer layoutGravity = ((BaseAndroidWidget) widget).layoutGravity;
                    if (layoutGravity != null)
                        ViewUtils.setLayoutGravity((FrameLayout.LayoutParams) layoutParams, v, layoutGravity);
                }
            }
        }
    }

}
