package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.widget.EditText;

public class BaseEditWidget<V extends EditText, T extends BaseEditWidget> extends BaseTextWidget<V, T> {

    public BaseEditWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public T inputType(int inputType) {
        view.setInputType(inputType);
        return self();
    }

    public T imeOptions(int imeOptions) {
        view.setImeOptions(imeOptions);
        return self();
    }

    public T hintText(CharSequence hintText) {
        view.setHint(hintText);
        return self();
    }

    public T hintTextColor(@ColorInt int hintTextColor) {
        view.setHintTextColor(hintTextColor);
        return self();
    }

    public T selection(int start, int stop) {
        view.setSelection(start, stop);
        return self();
    }

    public T selectAll() {
        view.selectAll();
        return self();
    }

    public T extendSelection(int index) {
        view.extendSelection(index);
        return self();
    }
    
}
