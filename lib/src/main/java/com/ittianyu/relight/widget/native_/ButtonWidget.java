package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.widget.Button;

public class ButtonWidget extends BaseTextWidget<Button, ButtonWidget> {

    public ButtonWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    public Button createView(Context context) {
        return new Button(context);
    }
}
