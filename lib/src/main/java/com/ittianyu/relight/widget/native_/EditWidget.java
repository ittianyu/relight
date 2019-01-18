package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.widget.EditText;

public class EditWidget extends BaseEditWidget<EditText, EditWidget> {

    public EditWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    public EditText createView(Context context) {
        return new EditText(context);
    }

}
