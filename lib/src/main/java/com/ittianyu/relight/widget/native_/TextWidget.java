package com.ittianyu.relight.widget.native_;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;
import android.widget.TextView;

import android.arch.lifecycle.Lifecycle;

public class TextWidget extends BaseAndroidWidget<TextView> {
    protected int gravity = -1;
    protected CharSequence text;
    protected int textColor = -1;
    protected int textSize = -1;
    protected int lines = -1;
    protected int maxLines = -1;
    protected int drawablePadding = -1;
    protected Drawable drawableBottom = null;
    protected Drawable drawableLeft = null;
    protected Drawable drawableRight = null;
    protected Drawable drawableTop = null;

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
        drawablePadding(drawablePadding);
        drawableLeft(drawableLeft);
        drawableTop(drawableTop);
        drawableRight(drawableRight);
        drawableBottom(drawableBottom);
    }

    public TextWidget text(CharSequence text) {
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

    public TextWidget drawablePadding(int res) {
        drawablePadding = res;
        if (drawablePadding != -1)
            view.setCompoundDrawablePadding(drawablePadding);
        return this;
    }

    public TextWidget drawableLeft(@DrawableRes int res) {
        return drawableLeft(drawable(res));
    }

    public TextWidget drawableLeft(Drawable drawable) {
        drawableLeft = drawable;
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(),
                    drawableLeft.getMinimumHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawableLeft, drawables[1], drawables[2], drawables[3]);
        }
        return this;
    }

    public TextWidget drawableTop(@DrawableRes int res) {
        return drawableTop(drawable(res));
    }

    public TextWidget drawableTop(Drawable drawable) {
        drawableTop = drawable;
        if (drawableTop != null) {
            drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(),
                    drawableTop.getMinimumHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawables[0], drawableTop, drawables[2], drawables[3]);
        }
        return this;
    }

    public TextWidget drawableRight(@DrawableRes int res) {
        return drawableRight(drawable(res));
    }

    public TextWidget drawableRight(Drawable drawable) {
        drawableRight = drawable;
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(),
                    drawableRight.getMinimumHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawables[0], drawables[1], drawableRight, drawables[3]);
        }
        return this;
    }

    public TextWidget drawableBottom(@DrawableRes int res) {
        return drawableBottom(drawable(res));
    }

    public TextWidget drawableBottom(Drawable drawable) {
        drawableBottom = drawable;
        if (drawableBottom != null) {
            drawableBottom.setBounds(0, 0, drawableBottom.getMinimumWidth(),
                    drawableBottom.getMinimumHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawableBottom);
        }
        return this;
    }

}
