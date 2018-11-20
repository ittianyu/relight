package com.ittianyu.relight.widget.native_;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TextView;

import android.arch.lifecycle.Lifecycle;

public class TextWidget extends BaseAndroidWidget<TextView> {
    protected int gravity = -1;
    protected String text;
    protected int textColor = -1;
    protected int textSize = -1;
    protected int lines = -1;
    protected int maxLines = -1;


    public TextWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public TextWidget(Context context, Lifecycle lifecycle, String text) {
        super(context, lifecycle);
        text(text);
    }

    @Override
    public TextView createView(Context context) {
        return new TextView(context);
    }

    @Override
    protected void initProps() {
    }

    @Override
    public void updateProps(TextView view) {
        super.updateProps(view);
        gravity(gravity);
        text(text);
        textColor(textColor);
        textSize(textSize);
        lines(lines);
        maxLines(maxLines);
    }

    public TextWidget text(String text) {
        this.text = text;
        if (null != text)
            view.setText(text);
        return this;
    }

    public TextWidget gravity(int gravity) {
        this.gravity = gravity;
        if (gravity != -1)
            view.setGravity(gravity);
        return this;
    }

    public TextWidget textColor(int textColor) {
        this.textColor = textColor;
        if (textColor != -1)
            view.setTextColor(textColor);
        return this;
    }

    public TextWidget textSize(int px) {
        this.textSize = px;
        if (textSize != -1)
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        return this;
    }

    public TextWidget lines(int lines) {
        this.lines = lines;
        if (lines != -1)
            view.setLines(lines);
        return this;
    }

    public TextWidget maxLines(int maxLines) {
        this.maxLines = maxLines;
        if (maxLines != -1)
            view.setMaxLines(maxLines);
        return this;
    }

}
