package com.ittianyu.relight.widget.stateful.lcee;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

public class CommonEmptyWidget extends StatelessWidget<FrameLayout, FrameWidget> {
    protected String text;
    protected View.OnClickListener onClickListener;
    protected TextWidget textWidget;

    public CommonEmptyWidget(Context context, Lifecycle lifecycle) {
        this(context, lifecycle, null);
    }

    public CommonEmptyWidget(Context context, Lifecycle lifecycle, String text) {
        this(context, lifecycle, text, null);
    }

    public CommonEmptyWidget(Context context, Lifecycle lifecycle, String text, View.OnClickListener onClickListener) {
        super(context, lifecycle);
        this.text = text;
        this.onClickListener = onClickListener;
    }

    @Override
    protected FrameWidget build(Context context, Lifecycle lifecycle) {
        textWidget = new TextWidget(context, lifecycle);
        return new FrameWidget(context, lifecycle, textWidget);
    }

    @Override
    public void initWidget(FrameWidget widget) {
        textWidget.text(text)
                .wrapContent()
                .textSize(16.0f)
                .textColor(Color.BLACK)
                .layoutGravity(Gravity.CENTER)
                .clickable(false);
        widget.matchParent().onClickListener(onClickListener);
    }

    public CommonEmptyWidget text(String text) {
        this.text = text;
        textWidget.text(text);
        return this;
    }

    public CommonEmptyWidget onClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        widget.onClickListener(onClickListener);
        return this;
    }
}
