package com.ittianyu.relight.widget.native_;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;
import android.widget.TextView;

import android.arch.lifecycle.Lifecycle;

import com.ittianyu.relight.utils.DensityUtils;

public class TextWidget extends BaseAndroidWidget<TextView> {

    protected Integer gravity;
    protected CharSequence text;
    protected Integer textColor;
    protected Integer textSize;
    protected Integer lines;
    protected Integer maxLines;
    protected Integer drawablePadding;
    protected Drawable drawableBottom;
    protected Drawable drawableLeft;
    protected Drawable drawableRight;
    protected Drawable drawableTop;

    public TextWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public TextWidget(Context context, Lifecycle lifecycle, CharSequence text) {
        super(context, lifecycle);
        this.text = text;
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
        if (gravity != null) {
            gravity(gravity);
        }
        if (text != null) {
            text(text);
        }
        if (textColor != null) {
            textColor(textColor);
        }
        if (textSize != null) {
            textSize(textSize);
        }
        if (lines != null) {
            lines(lines);
        }
        if (maxLines != null) {
            maxLines(maxLines);
        }
        if (drawablePadding != null) {
            drawablePadding(drawablePadding);
        }
        if (drawableLeft != null) {
            drawableLeft(drawableLeft);
        }
        if (drawableTop != null) {
            drawableTop(drawableTop);
        }
        if (drawableRight != null) {
            drawableRight(drawableRight);
        }
        if (drawableBottom != null) {
            drawableBottom(drawableBottom);
        }
    }

    public TextWidget text(CharSequence text) {
        this.text = text;
        if (null != text) {
            view.setText(text);
        }
        return this;
    }

    public TextWidget gravity(int gravity) {
        this.gravity = gravity;
        view.setGravity(gravity);
        return this;
    }

    public TextWidget textColor(int textColor) {
        this.textColor = textColor;
        view.setTextColor(textColor);
        return this;
    }

    public TextWidget textSize(int px) {
        this.textSize = px;
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        return this;
    }

    public TextWidget textSize(float sp) {
        return textSize(DensityUtils.sp2px(context, sp));
    }

    public TextWidget lines(int lines) {
        this.lines = lines;
        view.setLines(lines);
        return this;
    }

    public TextWidget maxLines(int maxLines) {
        this.maxLines = maxLines;
        view.setMaxLines(maxLines);
        return this;
    }

    public TextWidget drawablePadding(int res) {
        drawablePadding = res;
        view.setCompoundDrawablePadding(drawablePadding);
        return this;
    }

    public TextWidget drawableLeft(@DrawableRes int res) {
        return drawableLeft(drawable(res));
    }

    public TextWidget drawableLeft(Drawable drawable) {
        drawableLeft = drawable;
        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(),
            drawableLeft.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawableLeft, drawables[1], drawables[2], drawables[3]);
        return this;
    }

    public TextWidget drawableTop(@DrawableRes int res) {
        return drawableTop(drawable(res));
    }

    public TextWidget drawableTop(Drawable drawable) {
        drawableTop = drawable;
        drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(),
            drawableTop.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawables[0], drawableTop, drawables[2], drawables[3]);
        return this;
    }

    public TextWidget drawableRight(@DrawableRes int res) {
        return drawableRight(drawable(res));
    }

    public TextWidget drawableRight(Drawable drawable) {
        drawableRight = drawable;
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(),
            drawableRight.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawables[0], drawables[1], drawableRight, drawables[3]);
        return this;
    }

    public TextWidget drawableBottom(@DrawableRes int res) {
        return drawableBottom(drawable(res));
    }

    public TextWidget drawableBottom(Drawable drawable) {
        drawableBottom = drawable;
        drawableBottom.setBounds(0, 0, drawableBottom.getMinimumWidth(),
            drawableBottom.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawableBottom);
        return this;
    }

}
