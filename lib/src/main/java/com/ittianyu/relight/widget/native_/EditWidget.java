package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.widget.EditText;

public class EditWidget extends BaseTextWidget<EditText, EditWidget> {

    public EditWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    public EditText createView(Context context) {
        return new EditText(context);
    }

    public EditWidget inputType(int inputType) {
        view.setInputType(inputType);
        return self();
    }

    public EditWidget imeOptions(int imeOptions) {
        view.setImeOptions(imeOptions);
        return self();
    }

    public EditWidget singleLine(boolean singleLine) {
        view.setSingleLine(singleLine);
        return self();
    }

    public EditWidget hintText(CharSequence hintText) {
        view.setHint(hintText);
        return self();
    }

    public EditWidget hintTextColor(@ColorInt int hintTextColor) {
        view.setHintTextColor(hintTextColor);
        return self();
    }

}
