package com.ittianyu.relight.widget.stateful.lcee;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.ittianyu.relight.widget.native_.BaseAndroidWidget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

public class CommonLoadingWidget extends StatelessWidget<FrameLayout, FrameWidget> {

    public CommonLoadingWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected FrameWidget build(Context context) {
        return new FrameWidget(context, lifecycle,
                buildLoadingWidget()
        );
    }

    @Override
    public void initWidget(FrameWidget widget) {
        widget.matchParent();
    }

    private BaseAndroidWidget<ProgressBar, BaseAndroidWidget> buildLoadingWidget() {
        return new BaseAndroidWidget<ProgressBar, BaseAndroidWidget>(context, lifecycle) {
            @Override
            protected void initProps() {
                layoutGravity = Gravity.CENTER;
            }
        };
    }
}
