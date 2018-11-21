package com.ittianyu.relight.learn.widget;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.ittianyu.relight.R;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateful.lcee.CommonEmptyWidget;

/**
 * 空页面
 * <p>
 * Created by liyujiang on 2018/11/21 17:21
 */
public class GirlEmptyWidget extends CommonEmptyWidget {

    public GirlEmptyWidget(Context context, Lifecycle lifecycle, String text, View.OnClickListener onClickListener) {
        super(context, lifecycle, text, onClickListener);
    }

    @Override
    protected FrameWidget build(Context context) {
        return new FrameWidget(context, lifecycle,
                renderText()
        );
    }

    private TextWidget renderText() {
        return new TextWidget(context, lifecycle) {
            @Override
            protected void initProps() {
                super.initProps();
                width = wrapContent;
                height = wrapContent;
                textSize = dp(13);
                textColor = 0xFF333333;
                layoutGravity = Gravity.CENTER;
                text = GirlEmptyWidget.this.text;
                drawablePadding = dp(10);
                drawableTop = drawable(R.mipmap.ic_lcee_empty);
                onClickListener = GirlEmptyWidget.this.onClickListener;
            }
        };
    }

}
