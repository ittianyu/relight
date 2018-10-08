package com.ittianyu.relight.widget.stateful.lcee;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

import android.arch.lifecycle.Lifecycle;

public class CommonEmptyWidget extends StatelessWidget<FrameLayout> {
    protected Lifecycle lifecycle;
    protected String text;
    protected View.OnClickListener onClickListener;
    private TextWidget textWidget;

    public CommonEmptyWidget(Context context, Lifecycle lifecycle) {
        this(context, lifecycle, null);
    }

    public CommonEmptyWidget(Context context, Lifecycle lifecycle, String text) {
        this(context, lifecycle, text, null);
    }

    public CommonEmptyWidget(Context context, Lifecycle lifecycle, String text, View.OnClickListener onClickListener) {
        super(context);
        this.lifecycle = lifecycle;
        this.text = text;
        this.onClickListener = onClickListener;
    }

    @Override
    protected Widget<FrameLayout> build(Context context) {
        textWidget = new TextWidget(context, lifecycle) {
            @Override
            protected void initProps() {
                super.initProps();
                text = CommonEmptyWidget.this.text;
                textSize = dp(14);
                textColor = Color.BLACK;
                layoutGravity = Gravity.CENTER;
            }
        };
        return new FrameWidget(context, lifecycle, textWidget).matchParent().setOnClickListener(onClickListener);
    }

    public CommonEmptyWidget setText(String text) {
        this.text = text;
        textWidget.text(text);
        return this;
    }

    public CommonEmptyWidget setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        ((FrameWidget) widget).setOnClickListener(onClickListener);
        return this;
    }
}
