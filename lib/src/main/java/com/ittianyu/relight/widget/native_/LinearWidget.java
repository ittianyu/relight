package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutCompat.DividerMode;
import android.widget.LinearLayout;

import com.ittianyu.relight.widget.Widget;

public class LinearWidget extends BaseLinearWidget<LinearLayout, LinearWidget> {
    public static final Integer horizontal = LinearLayout.HORIZONTAL;
    public static final Integer vertical = LinearLayout.VERTICAL;

    public LinearWidget(Context context, Lifecycle lifecycle) {
        this(context, lifecycle, (Widget) null);
    }

    public LinearWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    @Override
    public LinearLayout createView(Context context) {
        return new LinearLayout(context);
    }

    public LinearWidget dividerDrawable(Drawable divider) {
        view.setDividerDrawable(divider);
        return self();
    }

    public LinearWidget showDividers(@DividerMode int showDividers) {
        view.setShowDividers(showDividers);
        return self();
    }

}
