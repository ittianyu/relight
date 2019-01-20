package com.ittianyu.relight.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION_CODES;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class ShapeDrawableBuilder {
    public static final int RECTANGLE = GradientDrawable.RECTANGLE;
    public static final int OVAL = GradientDrawable.OVAL;
    public static final int LINE = GradientDrawable.LINE;
    public static final int RING = GradientDrawable.RING;

    private GradientDrawable drawable = new GradientDrawable();

    public GradientDrawable build() {
        return drawable;
    }

    /**
     *
     * @param shape ShapeDrawableBuilder.RECTANGLE OVAL LINE RING
     * @return
     */
    public ShapeDrawableBuilder shape(int shape) {
        drawable.setShape(shape);
        return this;
    }

    public ShapeDrawableBuilder rectangle() {
        return shape(RECTANGLE);
    }

    public ShapeDrawableBuilder oval() {
        return shape(OVAL);
    }

    public ShapeDrawableBuilder line() {
        return shape(LINE);
    }

    public ShapeDrawableBuilder ring() {
        return shape(RING);
    }

    public ShapeDrawableBuilder dither(boolean dither) {
        drawable.setDither(dither);
        return this;
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public ShapeDrawableBuilder tint(int tintColor) {
        drawable.setTint(tintColor);
        return this;
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public ShapeDrawableBuilder tintList(ColorStateList tint) {
        drawable.setTintList(tint);
        return this;
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public ShapeDrawableBuilder tintMode(PorterDuff.Mode tintMode) {
        drawable.setTintMode(tintMode);
        return this;
    }

    public ShapeDrawableBuilder useLevel(boolean useLevel) {
        drawable.setUseLevel(useLevel);
        return this;
    }

    public ShapeDrawableBuilder visible(boolean visible, boolean restart) {
        drawable.setVisible(visible, restart);
        return this;
    }

    public ShapeDrawableBuilder gradientCenter(float x, float y) {
        drawable.setGradientCenter(x, y);
        return this;
    }

    public ShapeDrawableBuilder gradientRadius(float gradientRadius) {
        drawable.setGradientRadius(gradientRadius);
        return this;
    }

    /**
     * The type of the gradient: {@link GradientDrawable.LINEAR_GRADIENT},
     *      {@link GradientDrawable.RADIAL_GRADIENT} or {@link GradientDrawable.SWEEP_GRADIENT}
     * @param gradient
     * @return
     */
    public ShapeDrawableBuilder gradientType(int gradient) {
        drawable.setGradientType(gradient);
        return this;
    }

    public ShapeDrawableBuilder color(int argb) {
        drawable.setColor(argb);
        return this;
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public ShapeDrawableBuilder color(ColorStateList colorStateList) {
        drawable.setColor(colorStateList);
        return this;
    }

    public ShapeDrawableBuilder size(int width, int height) {
        drawable.setSize(width, height);
        return this;
    }

    public ShapeDrawableBuilder size(Context context, float width, float height) {
        return size(DensityUtils.dip2px(context, width), DensityUtils.dip2px(context, height));
    }

    public ShapeDrawableBuilder width(int width) {
        return size(width, LayoutParams.MATCH_PARENT);
    }

    public ShapeDrawableBuilder width(Context context, float dp) {
        return width(DensityUtils.dip2px(context, dp));
    }

    public ShapeDrawableBuilder height(int height) {
        return size(LayoutParams.MATCH_PARENT, height);
    }

    public ShapeDrawableBuilder height(Context context, float dp) {
        return height(DensityUtils.dip2px(context, dp));
    }

    public ShapeDrawableBuilder radius(float radius) {
        drawable.setCornerRadius(radius);
        return this;
    }

    public ShapeDrawableBuilder radius(float[] radii) {
        drawable.setCornerRadii(radii);
        return this;
    }

    public ShapeDrawableBuilder stroke(int width, @ColorInt int color) {
        drawable.setStroke(width, color);
        return this;
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public ShapeDrawableBuilder stroke(int width, ColorStateList colorStateList) {
        drawable.setStroke(width, colorStateList);
        return this;
    }

    public ShapeDrawableBuilder stroke(int width, @ColorInt int color, float dashWidth, float dashGap) {
        drawable.setStroke(width, color, dashWidth, dashGap);
        return this;
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public ShapeDrawableBuilder stroke(int width, ColorStateList colorStateList, float dashWidth, float dashGap) {
        drawable.setStroke(width, colorStateList, dashWidth, dashGap);
        return this;
    }

    public static class RadiusBuilder {
        float[] radii = new float[8];

        public RadiusBuilder topLeft(float top, float left) {
            radii[0] = top;
            radii[1] = left;
            return this;
        }

        public RadiusBuilder topRight(float top, float right) {
            radii[2] = top;
            radii[3] = right;
            return this;
        }

        public RadiusBuilder bottomRight(float bottom, float right) {
            radii[4] = bottom;
            radii[5] = right;
            return this;
        }

        public RadiusBuilder bottomLeft(float bottom, float left) {
            radii[6] = bottom;
            radii[7] = left;
            return this;
        }

        public float[] build() {
            return radii;
        }
    }

}
