package com.ittianyu.relight.learn.widget;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ittianyu.relight.R;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.BaseAndroidWidget;
import com.ittianyu.relight.widget.native_.ImageWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

/**
 * 出错页面
 * <p>
 * Created by liyujiang on 2018/11/21 17:25
 */
public class GirlLoadingWidget extends StatelessWidget<LinearLayout, LinearWidget> {
    protected Lifecycle lifecycle;
    protected CharSequence text;

    public GirlLoadingWidget(Context context, Lifecycle lifecycle, CharSequence text) {
        super(context);
        this.lifecycle = lifecycle;
        this.text = text;
    }

    @Override
    protected LinearWidget build(Context context) {
        return new LinearWidget(context, lifecycle,
                renderProgress(),
                renderText()
        );
    }

    private Widget renderProgress() {
        return new BaseAndroidWidget<ProgressBar>(context, lifecycle) {
            @Override
            protected void initProps() {
                width = dp(30);
                height = dp(30);
            }

            @Override
            public void updateProps(ProgressBar view) {
                super.updateProps(view);
                view.setIndeterminate(true);
                view.setIndeterminateDrawable(drawable(R.drawable.anim_lcee_loading));
            }
        };
    }

    private Widget renderText() {
        return new TextWidget(context, lifecycle) {
            @Override
            protected void initProps() {
                text = GirlLoadingWidget.this.text;
                marginTop = dp(10);
            }
        };
    }

    @Override
    public void initWidget(LinearWidget widget) {
        widget.matchParent();
        widget.orientation(LinearWidget.vertical);
        widget.gravity(Gravity.CENTER);
    }

}
