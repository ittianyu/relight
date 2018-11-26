package com.ittianyu.relight.learn.widget;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.ittianyu.relight.R;
import com.ittianyu.relight.utils.DensityUtils;
import com.ittianyu.relight.widget.native_.ImageWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

/**
 * 出错页面
 * <p>
 * Created by liyujiang on 2018/11/21 17:25
 */
public class GirlErrorWidget extends StatelessWidget<LinearLayout, LinearWidget> {
    protected Lifecycle lifecycle;
    protected CharSequence text;
    protected View.OnClickListener onClickListener;
    protected ImageWidget imageWidget;
    protected TextWidget textWidget;

    public GirlErrorWidget(Context context, Lifecycle lifecycle, CharSequence text, View.OnClickListener onClickListener) {
        super(context);
        this.lifecycle = lifecycle;
        this.text = text;
        this.onClickListener = onClickListener;
    }

    @Override
    protected LinearWidget build(Context context) {
        imageWidget = new ImageWidget(context, lifecycle);
        textWidget = new TextWidget(context, lifecycle);
        return new LinearWidget(context, lifecycle, imageWidget, textWidget);
    }

    @Override
    public void initWidget(LinearWidget widget) {
        imageWidget.imageResource(R.mipmap.ic_lcee_error);
        imageWidget.onClickListener(onClickListener);
        textWidget.marginTop(DensityUtils.dip2px(context, 10));
        textWidget.text(text);
        textWidget.onClickListener(onClickListener);
        widget.matchParent();
        widget.orientation(LinearWidget.vertical);
        widget.gravity(Gravity.CENTER);
        widget.onClickListener(onClickListener);
    }

}
