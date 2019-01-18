package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.TextView;

import com.ittianyu.relight.utils.DensityUtils;

public class BaseTextWidget<V extends TextView, T extends BaseTextWidget> extends BaseAndroidWidget<V, T> {

    public BaseTextWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected void initProps() {
    }

    public T text(CharSequence text) {
        view.setText(text);
        return self();
    }

    public T text(String text) {
        return text((CharSequence) text);
    }

    public CharSequence text() {
        return view.getText();
    }

    public T gravity(Integer gravity) {
        view.setGravity(gravity);
        return self();
    }

    public T textColor(Integer textColor) {
        view.setTextColor(textColor);
        return self();
    }

    public T textSize(Integer px) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, px);
        return self();
    }

    public T textSize(Float sp) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
        return self();
    }

    public T textSize(Double sp) {
        return textSize(sp.floatValue());
    }

    public T singleLine(boolean singleLine) {
        view.setSingleLine(singleLine);
        return self();
    }

    public T lines(Integer lines) {
        view.setLines(lines);
        return self();
    }

    public T maxLines(Integer maxLines) {
        view.setMaxLines(maxLines);
        return self();
    }

    public T drawablePadding(Integer padding) {
        view.setCompoundDrawablePadding(padding);
        return self();
    }

    public T drawableLeft(@DrawableRes Integer res) {
        return drawableLeft(drawable(res));
    }

    public T drawableLeft(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
            drawable.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawable, drawables[1], drawables[2], drawables[3]);
        return self();
    }

    public T drawableTop(@DrawableRes Integer res) {
        return drawableTop(drawable(res));
    }

    public T drawableTop(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
            drawable.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawables[0], drawable, drawables[2], drawables[3]);
        return self();
    }

    public T drawableRight(@DrawableRes Integer res) {
        return drawableRight(drawable(res));
    }

    public T drawableRight(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
            drawable.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawables[0], drawables[1], drawable, drawables[3]);
        return self();
    }

    public T drawableBottom(@DrawableRes Integer res) {
        return drawableBottom(drawable(res));
    }

    public T drawableBottom(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
            drawable.getMinimumHeight());
        Drawable[] drawables = view.getCompoundDrawables();
        view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawable);
        return self();
    }

    public T allCaps(Boolean enable) {
        view.setAllCaps(enable);
        return self();
    }

    public T enabled(Boolean enabled) {
        view.setEnabled(enabled);
        return self();
    }

    public T ellipsize(TextUtils.TruncateAt ellipsize) {
        view.setEllipsize(ellipsize);
        return self();
    }

    public T setTextAppearance(int resId) {
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            view.setTextAppearance(resId);
        }
        return self();
    }

    public T typeface(@Nullable Typeface tf) {
        view.setTypeface(tf);
        return self();
    }

    public T typeface(@Nullable Typeface tf, int style) {
        view.setTypeface(tf, style);
        return self();
    }

}
