package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;
import android.widget.Button;

import com.ittianyu.relight.utils.DensityUtils;

public class ButtonWidget extends BaseAndroidWidget<Button> {

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
    private Boolean allCaps;

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
        if (allCaps != null) {
            allCaps(allCaps);
        }
    }

    public ButtonWidget text(CharSequence text) {
        this.text = text;
        view.setText(text);
        return this;
    }

    public ButtonWidget gravity(int gravity) {
        this.gravity = gravity;
        view.setGravity(gravity);
        return this;
    }

    public ButtonWidget textColor(int textColor) {
        this.textColor = textColor;
        view.setTextColor(textColor);
        return this;
    }

    public ButtonWidget textSize(int px) {
        this.textSize = px;
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        return this;
    }

    public ButtonWidget textSize(float sp) {
        return textSize(DensityUtils.sp2px(context, sp));
    }

    public ButtonWidget lines(int lines) {
        this.lines = lines;
        view.setLines(lines);
        return this;
    }

    public ButtonWidget maxLines(int maxLines) {
        this.maxLines = maxLines;
        view.setMaxLines(maxLines);
        return this;
    }

    public ButtonWidget drawablePadding(int res) {
        drawablePadding = res;
        view.setCompoundDrawablePadding(drawablePadding);
        return this;
    }

    public ButtonWidget drawableLeft(@DrawableRes int res) {
        return drawableLeft(drawable(res));
    }

    public ButtonWidget drawableLeft(Drawable drawable) {
        drawableLeft = drawable;
        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(),
            drawableLeft.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawableLeft, drawables[1], drawables[2], drawables[3]);
        return this;
    }

    public ButtonWidget drawableTop(@DrawableRes int res) {
        return drawableTop(drawable(res));
    }

    public ButtonWidget drawableTop(Drawable drawable) {
        drawableTop = drawable;
        drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(),
            drawableTop.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawables[0], drawableTop, drawables[2], drawables[3]);
        return this;
    }

    public ButtonWidget drawableRight(@DrawableRes int res) {
        return drawableRight(drawable(res));
    }

    public ButtonWidget drawableRight(Drawable drawable) {
        drawableRight = drawable;
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(),
            drawableRight.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawables[0], drawables[1], drawableRight, drawables[3]);
        return this;
    }

    public ButtonWidget drawableBottom(@DrawableRes int res) {
        return drawableBottom(drawable(res));
    }

    public ButtonWidget drawableBottom(Drawable drawable) {
        drawableBottom = drawable;
        drawableBottom.setBounds(0, 0, drawableBottom.getMinimumWidth(),
            drawableBottom.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawableBottom);
        return this;
    }

    public ButtonWidget allCaps(boolean enable) {
        this.allCaps = enable;
        view.setAllCaps(allCaps);
        return this;
    }

}
