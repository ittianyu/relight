package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.ittianyu.relight.utils.ViewUtils;
import com.ittianyu.relight.widget.ContainerWidget;
import com.ittianyu.relight.widget.Widget;

public class BaseFrameWidget<V extends FrameLayout, T extends BaseFrameWidget> extends ViewGroupWidget<V, T> {

    public BaseFrameWidget(Context context, Lifecycle lifecycle) {
        this(context, lifecycle, (Widget) null);
    }

    public BaseFrameWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    @Override
    public void updateProps(V view) {
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
