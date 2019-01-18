package com.ittianyu.relight.widget.native_.material;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.design.button.MaterialButton;
import com.ittianyu.relight.widget.native_.BaseTextWidget;

public class MaterialButtonWidget extends BaseTextWidget<MaterialButton, MaterialButtonWidget> {

    public MaterialButtonWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    public MaterialButton createView(Context context) {
        return new MaterialButton(context);
    }

    public MaterialButtonWidget iconPadding(@Px int iconPadding) {
        view.setIconPadding(iconPadding);
        return self();
    }

    public MaterialButtonWidget iconSize(@Px int iconSize) {
        view.setIconSize(iconSize);
        return self();
    }

    public MaterialButtonWidget icon(Drawable icon) {
        view.setIcon(icon);
        return self();
    }

    public MaterialButtonWidget iconResource(@DrawableRes int iconResourceId) {
        view.setIconResource(iconResourceId);
        return self();
    }

    public MaterialButtonWidget iconTint(@Nullable ColorStateList iconTint) {
        view.setIconTint(iconTint);
        return self();
    }

    public MaterialButtonWidget iconTintResource(@ColorRes int iconTintResourceId) {
        view.setIconTintResource(iconTintResourceId);
        return self();
    }

    public MaterialButtonWidget iconTintMode(Mode iconTintMode) {
        view.setIconTintMode(iconTintMode);
        return self();
    }

    public MaterialButtonWidget rippleColor(@Nullable ColorStateList rippleColor) {
        view.setRippleColor(rippleColor);
        return self();
    }

    public MaterialButtonWidget rippleColorResource(@ColorRes int rippleColorResourceId) {
        view.setRippleColorResource(rippleColorResourceId);
        return self();
    }

    public MaterialButtonWidget strokeColor(@Nullable ColorStateList strokeColor) {
        view.setStrokeColor(strokeColor);
        return self();
    }

    public MaterialButtonWidget strokeColorResource(@ColorRes int strokeColorResourceId) {
        view.setStrokeColorResource(strokeColorResourceId);
        return self();
    }

    public MaterialButtonWidget strokeWidth(@Px int strokeWidth) {
        view.setStrokeWidth(strokeWidth);
        return self();
    }

    public MaterialButtonWidget strokeWidthResource(@DimenRes int strokeWidthResourceId) {
        view.setStrokeWidthResource(strokeWidthResourceId);
        return self();
    }

    public MaterialButtonWidget cornerRadius(@Px int cornerRadius) {
        view.setCornerRadius(cornerRadius);
        return self();
    }

    public MaterialButtonWidget cornerRadiusResource(@DimenRes int cornerRadiusResourceId) {
        view.setCornerRadiusResource(cornerRadiusResourceId);
        return self();
    }

    public MaterialButtonWidget iconGravity(int iconGravity) {
        view.setIconGravity(iconGravity);
        return self();
    }

}
