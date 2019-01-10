package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.widget.TextView;

public class TextWidget extends BaseTextWidget<TextView, TextWidget> {

    public TextWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    public TextView createView(Context context) {
        return new TextView(context);
    }

}
