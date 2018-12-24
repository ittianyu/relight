package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;

public class ButtonWidget extends BaseTextWidget<Button, ButtonWidget> {

    public ButtonWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public ButtonWidget(Context context, Lifecycle lifecycle, CharSequence text) {
        super(context, lifecycle, text);
    }

    @Override
    public Button createView(Context context) {
        return new Button(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        System.out.println("lp:" + lp);
    }
}
