package com.ittianyu.relight.widget.stateful.lcee;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.atomic.BaseAndroidWidget;
import com.ittianyu.relight.widget.atomic.FrameWidget;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

import android.arch.lifecycle.Lifecycle;

public class CommonLoadingWidget extends StatelessWidget<FrameLayout> {
    protected Lifecycle lifecycle;

    public CommonLoadingWidget(Context context, Lifecycle lifecycle) {
        super(context);
        this.lifecycle = lifecycle;
    }

    @Override
    protected Widget<FrameLayout> build(Context context) {
        return new FrameWidget(context, lifecycle,
                buildLoadingWidget()
        ).matchParent();
    }

    private BaseAndroidWidget<ProgressBar> buildLoadingWidget() {
        return new BaseAndroidWidget<ProgressBar>(context, lifecycle) {
            @Override
            protected void initProps() {
                layoutGravity = Gravity.CENTER;
            }
        };
    }
}
