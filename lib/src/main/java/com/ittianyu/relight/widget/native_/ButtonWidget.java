package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;
import android.widget.Button;

import com.ittianyu.relight.utils.DensityUtils;

public class ButtonWidget extends BaseAndroidWidget<Button> {
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

    public ButtonWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public ButtonWidget(Context context, Lifecycle lifecycle, String text) {
        super(context, lifecycle);
        text(text);
    }

    @Override
    public Button createView(Context context) {
        return new Button(context);
    }

    @Override
    protected void initProps() {
    }

    @Override
    public void updateProps(Button view) {
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

    public ButtonWidget text(CharSequence text) {
        this.text = text;
        if (null != text)
            view.setText(text);
        return this;
    }

    public ButtonWidget gravity(int gravity) {
        this.gravity = gravity;
        if (gravity != -1)
            view.setGravity(gravity);
        return this;
    }

    public ButtonWidget textColor(int textColor) {
        this.textColor = textColor;
        if (textColor != -1)
            view.setTextColor(textColor);
        return this;
    }

    public ButtonWidget textSize(int px) {
        this.textSize = px;
        if (textSize != -1)
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        return this;
    }

    public ButtonWidget textSize(float sp) {
        return textSize(DensityUtils.sp2px(context, sp));
    }

    public ButtonWidget lines(int lines) {
        this.lines = lines;
        if (lines != -1)
            view.setLines(lines);
        return this;
    }

    public ButtonWidget maxLines(int maxLines) {
        this.maxLines = maxLines;
        if (maxLines != -1)
            view.setMaxLines(maxLines);
        return this;
    }

    public ButtonWidget drawablePadding(int res) {
        drawablePadding = res;
        if (drawablePadding != -1)
            view.setCompoundDrawablePadding(drawablePadding);
        return this;
    }

    public ButtonWidget drawableLeft(@DrawableRes int res) {
        return drawableLeft(drawable(res));
    }

    public ButtonWidget drawableLeft(Drawable drawable) {
        drawableLeft = drawable;
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(),
                    drawableLeft.getMinimumHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawableLeft, drawables[1], drawables[2], drawables[3]);
        }
        return this;
    }

    public ButtonWidget drawableTop(@DrawableRes int res) {
        return drawableTop(drawable(res));
    }

    public ButtonWidget drawableTop(Drawable drawable) {
        drawableTop = drawable;
        if (drawableTop != null) {
            drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(),
                    drawableTop.getMinimumHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawables[0], drawableTop, drawables[2], drawables[3]);
        }
        return this;
    }

    public ButtonWidget drawableRight(@DrawableRes int res) {
        return drawableRight(drawable(res));
    }

    public ButtonWidget drawableRight(Drawable drawable) {
        drawableRight = drawable;
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(),
                    drawableRight.getMinimumHeight());
            Drawable[] drawables = view.getCompoundDrawables();
            view.setCompoundDrawables(drawables[0], drawables[1], drawableRight, drawables[3]);
        }
        return this;
    }

    public ButtonWidget drawableBottom(@DrawableRes int res) {
        return drawableBottom(drawable(res));
    }

    public ButtonWidget drawableBottom(Drawable drawable) {
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
