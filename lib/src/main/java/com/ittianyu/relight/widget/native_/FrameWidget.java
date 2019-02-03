package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.widget.FrameLayout;

import com.ittianyu.relight.widget.Widget;

public class FrameWidget extends BaseFrameWidget<FrameLayout, FrameWidget> {

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

}
