package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.TextView;

import com.ittianyu.relight.utils.DensityUtils;

public class BaseTextWidget<V extends TextView, T extends BaseTextWidget> extends BaseAndroidWidget<V> {

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
    protected Boolean allCaps;
    protected Boolean enabled;
    protected TextUtils.TruncateAt ellipsize;

    public BaseTextWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public BaseTextWidget(Context context, Lifecycle lifecycle, CharSequence text) {
        super(context, lifecycle);
        this.text = text;
    }

    @Override
    protected void initProps() {
    }

    @Override
    public void updateProps(V view) {
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
        if (enabled != null) {
            enabled(enabled);
        }
        if (ellipsize != null) {
            ellipsize(ellipsize);
        }
    }

    protected final T self() {
        //noinspection unchecked
        return (T) this;
    }

    public T text(CharSequence text) {
        this.text = text;
        view.setText(text);
        return self();
    }

    public T gravity(int gravity) {
        this.gravity = gravity;
        view.setGravity(gravity);
        return self();
    }

    public T textColor(int textColor) {
        this.textColor = textColor;
        view.setTextColor(textColor);
        return self();
    }

    public T textSize(int px) {
        this.textSize = px;
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        return self();
    }

    public T textSize(float sp) {
        return textSize(DensityUtils.sp2px(context, sp));
    }

    public T lines(int lines) {
        this.lines = lines;
        view.setLines(lines);
        return self();
    }

    public T maxLines(int maxLines) {
        this.maxLines = maxLines;
        view.setMaxLines(maxLines);
        return self();
    }

    public T drawablePadding(int res) {
        drawablePadding = res;
        view.setCompoundDrawablePadding(drawablePadding);
        return self();
    }

    public T drawableLeft(@DrawableRes int res) {
        return drawableLeft(drawable(res));
    }

    public T drawableLeft(Drawable drawable) {
        drawableLeft = drawable;
        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(),
                drawableLeft.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawableLeft, drawables[1], drawables[2], drawables[3]);
        return self();
    }

    public T drawableTop(@DrawableRes int res) {
        return drawableTop(drawable(res));
    }

    public T drawableTop(Drawable drawable) {
        drawableTop = drawable;
        drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(),
                drawableTop.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawables[0], drawableTop, drawables[2], drawables[3]);
        return self();
    }

    public T drawableRight(@DrawableRes int res) {
        return drawableRight(drawable(res));
    }

    public T drawableRight(Drawable drawable) {
        drawableRight = drawable;
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(),
                drawableRight.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawables[0], drawables[1], drawableRight, drawables[3]);
        return self();
    }

    public T drawableBottom(@DrawableRes int res) {
        return drawableBottom(drawable(res));
    }

    public T drawableBottom(Drawable drawable) {
        drawableBottom = drawable;
        drawableBottom.setBounds(0, 0, drawableBottom.getMinimumWidth(),
                drawableBottom.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawableBottom);
        return self();
    }

    public T allCaps(boolean enable) {
        this.allCaps = enable;
        view.setAllCaps(allCaps);
        return self();
    }

    public T enabled(boolean enabled) {
        this.enabled = enabled;
        view.setEnabled(enabled);
        return self();
    }

    public T ellipsize(TextUtils.TruncateAt ellipsize) {
        this.ellipsize = ellipsize;
        view.setEllipsize(ellipsize);
        return self();
    }

}
