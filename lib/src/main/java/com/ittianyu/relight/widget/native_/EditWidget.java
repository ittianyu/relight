package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.widget.EditText;

public class EditWidget extends BaseTextWidget<EditText, EditWidget> {

    protected Integer inputType;
    protected Integer imeOptions;
    protected Boolean singleLine;
    protected CharSequence hintText;
    protected Integer hintTextColor;

    public EditWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public EditWidget(Context context, Lifecycle lifecycle, CharSequence text) {
        super(context, lifecycle, text);
    }

    @Override
    public EditText createView(Context context) {
        return new EditText(context);
    }

    @Override
    public void updateProps(EditText view) {
        super.updateProps(view);
        if (inputType != null) {
            inputType(inputType);
        }
        if (imeOptions != null) {
            imeOptions(imeOptions);
        }
        if (singleLine != null) {
            singleLine(singleLine);
        }
        if (hintText != null) {
            hintText(hintText);
        }
        if (hintTextColor != null) {
            hintTextColor(hintTextColor);
        }
    }

    public EditWidget inputType(int inputType) {
        this.inputType = inputType;
        view.setInputType(inputType);
        return self();
    }

    public EditWidget imeOptions(int imeOptions) {
        this.imeOptions = imeOptions;
        view.setImeOptions(imeOptions);
        return self();
    }

    public EditWidget singleLine(boolean singleLine) {
        this.singleLine = singleLine;
        view.setSingleLine(singleLine);
        return self();
    }

    public EditWidget hintText(CharSequence hintText) {
        this.hintText = hintText;
        view.setHint(hintText);
        return self();
    }

    public EditWidget hintTextColor(@ColorInt int hintTextColor) {
        this.hintTextColor = hintTextColor;
        view.setHintTextColor(hintTextColor);
        return self();
    }

}
