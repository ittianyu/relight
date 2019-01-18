package com.ittianyu.relight.widget.native_.material;

import android.animation.AnimatorInflater;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.AnimatorRes;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.annotation.StyleRes;
import android.support.design.animation.MotionSpec;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipDrawable;
import android.support.design.resources.TextAppearance;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils.TruncateAt;
import android.view.View.OnClickListener;
import android.widget.TextView.BufferType;
import com.ittianyu.relight.lib.R;
import com.ittianyu.relight.widget.native_.BaseAndroidWidget;

public class ChipWidget extends BaseAndroidWidget<Chip, ChipWidget> {
    private final ColorStateList defaultBackgroundColor;

    public enum Style {
        Action,
        Choice,
        Entry,
        Filter,
    }

    public ChipWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
        defaultBackgroundColor = context.getResources()
            .getColorStateList(R.color.mtrl_chip_background_color);
    }

    @Override
    public Chip createView(Context context) {
        return new Chip(context);
    }

    @Override
    protected void initProps() {

    }

    public ColorStateList defaultBackgroundColor() {
        return defaultBackgroundColor;
    }

    public ChipWidget style(Style style) {
        focusable(true);
        clickable(true);
        checkable(false);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            stateListAnimator(AnimatorInflater.loadStateListAnimator(context, R.animator.mtrl_chip_state_list_anim));
        }
        chipIconVisible(true);
        checkedIconVisible(true);
        closeIconVisible(true);
        checkedIconResource(R.drawable.ic_mtrl_chip_checked_circle);
        closeIconResource(R.drawable.ic_mtrl_chip_close_circle);
        textColor(context.getResources().getColorStateList(R.color.mtrl_chip_text_color));
        closeIconTint(context.getResources().getColorStateList(R.color.mtrl_chip_close_icon_tint));
        chipBackgroundColor(defaultBackgroundColor);
        chipStrokeColor(ColorStateList.valueOf(Color.parseColor("#00000000")));
        rippleColor(context.getResources().getColorStateList(R.color.mtrl_chip_ripple_color));
        chipMinHeight(dp(32));
        chipCornerRadius(dp(16));
        chipIconSize(dp(24));
        closeIconSize(dp(18));
        chipStartPadding(dp(4));
        iconStartPadding(0);
        iconEndPadding(0);
        textStartPadding(dp(8));
        textEndPadding(dp(6));
        closeIconStartPadding(dp(2));
        closeIconEndPadding(dp(2));
        chipEndPadding(dp(6));

        switch (style) {
            case Action:
                closeIconVisible(false);
                break;
            case Choice:
                checkable(true);
                chipIconVisible(false);
                checkedIconVisible(false);
                closeIconVisible(false);
                checkedIconResource(R.drawable.ic_mtrl_chip_checked_black);
                break;
            case Entry:
                checkable(true);
                break;
            case Filter:
                checkable(true);
                chipIconVisible(false);
                closeIconVisible(false);
                checkedIconResource(R.drawable.ic_mtrl_chip_checked_black);
        }
        return self();
    }

    public ChipWidget textColor(ColorStateList colors) {
        view.setTextColor(colors);
        return self();
    }

    public ChipWidget textColor(int color) {
        view.setTextColor(color);
        return self();
    }

    public ChipWidget chipDrawable(@NonNull ChipDrawable drawable) {
        view.setChipDrawable(drawable);
        return self();
    }

    public ChipWidget gravity(int gravity) {
        view.setGravity(gravity);
        return self();
    }

    public ChipWidget backgroundColor(int color) {
        view.setBackgroundColor(color);
        return self();
    }

    public ChipWidget backgroundResource(int resid) {
        view.setBackgroundResource(resid);
        return self();
    }

    public ChipWidget ellipsize(TruncateAt where) {
        view.setEllipsize(where);
        return self();
    }

    public ChipWidget maxWidth(@Px int maxWidth) {
        view.setMaxWidth(maxWidth);
        return self();
    }

    public ChipWidget checked(boolean checked) {
        view.setChecked(checked);
        return self();
    }

    public ChipWidget onCloseIconClick(OnClickListener listener) {
        view.setOnCloseIconClickListener(listener);
        return self();
    }

    public ChipWidget chipBackgroundColorResource(@ColorRes int id) {
        view.setChipBackgroundColorResource(id);
        return self();
    }

    public ChipWidget chipBackgroundColor(@Nullable ColorStateList chipBackgroundColor) {
        view.setChipBackgroundColor(chipBackgroundColor);
        return self();
    }

    public ChipWidget chipMinHeight(float minHeight) {
        view.setChipMinHeight(minHeight);
        return self();
    }

    public ChipWidget chipMinHeightResource(@DimenRes int id) {
        view.setChipMinHeightResource(id);
        return self();
    }

    public ChipWidget chipCornerRadiusResource(@DimenRes int id) {
        view.setChipCornerRadiusResource(id);
        return self();
    }

    public ChipWidget chipCornerRadius(float chipCornerRadius) {
        view.setChipCornerRadius(chipCornerRadius);
        return self();
    }

    public ChipWidget chipStrokeColorResource(@ColorRes int id) {
        view.setChipStrokeColorResource(id);
        return self();
    }

    public ChipWidget chipStrokeColor(@Nullable ColorStateList chipStrokeColor) {
        view.setChipStrokeColor(chipStrokeColor);
        return self();
    }

    public ChipWidget chipStrokeWidthResource(@DimenRes int id) {
        view.setChipStrokeWidthResource(id);
        return self();
    }

    public ChipWidget chipStrokeWidth(float chipStrokeWidth) {
        view.setChipStrokeWidth(chipStrokeWidth);
        return self();
    }

    public ChipWidget rippleColorResource(@ColorRes int id) {
        view.setRippleColorResource(id);
        return self();
    }

    public ChipWidget rippleColor(@Nullable ColorStateList rippleColor) {
        view.setRippleColor(rippleColor);
        return self();
    }

    public ChipWidget text(CharSequence text, BufferType type) {
        view.setText(text, type);
        return self();
    }

    public ChipWidget text(@Nullable CharSequence chipText) {
        view.setText(chipText);
        return self();
    }

    public ChipWidget textAppearanceResource(@StyleRes int id) {
        view.setTextAppearanceResource(id);
        return self();
    }

    public ChipWidget textAppearance(@Nullable TextAppearance textAppearance) {
        view.setTextAppearance(textAppearance);
        return self();
    }

    public ChipWidget textAppearance(Context context, int resId) {
        view.setTextAppearance(context, resId);
        return self();
    }

    public ChipWidget textAppearance(int resId) {
        view.setTextAppearance(resId);
        return self();
    }

    public boolean isChipIconVisible() {
        return view.isChipIconVisible();
    }

    public ChipWidget chipIconVisible(@BoolRes int id) {
        view.setChipIconVisible(id);
        return self();
    }

    public ChipWidget chipIconVisible(boolean chipIconVisible) {
        view.setChipIconVisible(chipIconVisible);
        return self();
    }

    public ChipWidget chipIconResource(@DrawableRes int id) {
        view.setChipIconResource(id);
        return self();
    }

    public ChipWidget chipIcon(@Nullable Drawable chipIcon) {
        view.setChipIcon(chipIcon);
        return self();
    }

    public ChipWidget chipIconTintResource(@ColorRes int id) {
        view.setChipIconTintResource(id);
        return self();
    }

    public ChipWidget chipIconTint(@Nullable ColorStateList chipIconTint) {
        view.setChipIconTint(chipIconTint);
        return self();
    }

    public ChipWidget chipIconSizeResource(@DimenRes int id) {
        view.setChipIconSizeResource(id);
        return self();
    }

    public ChipWidget chipIconSize(float chipIconSize) {
        view.setChipIconSize(chipIconSize);
        return self();
    }

    public boolean isCloseIconVisible() {
        return view.isCloseIconVisible();
    }

    public ChipWidget closeIconVisible(@BoolRes int id) {
        view.setCloseIconVisible(id);
        return self();
    }

    public ChipWidget closeIconVisible(boolean closeIconVisible) {
        view.setCloseIconVisible(closeIconVisible);
        return self();
    }

    public ChipWidget closeIconResource(@DrawableRes int id) {
        view.setCloseIconResource(id);
        return self();
    }

    public ChipWidget closeIcon(@Nullable Drawable closeIcon) {
        view.setCloseIcon(closeIcon);
        return self();
    }

    public ChipWidget closeIconTintResource(@ColorRes int id) {
        view.setCloseIconTintResource(id);
        return self();
    }

    public ChipWidget closeIconTint(@Nullable ColorStateList closeIconTint) {
        view.setCloseIconTint(closeIconTint);
        return self();
    }

    public ChipWidget closeIconSizeResource(@DimenRes int id) {
        view.setCloseIconSizeResource(id);
        return self();
    }

    public ChipWidget closeIconSize(float closeIconSize) {
        view.setCloseIconSize(closeIconSize);
        return self();
    }

    public ChipWidget closeIconContentDescription(@Nullable CharSequence closeIconContentDescription) {
        view.setCloseIconContentDescription(closeIconContentDescription);
        return self();
    }

    public boolean isCheckable() {
        return view.isCheckable();
    }

    public ChipWidget checkableResource(@BoolRes int id) {
        view.setCheckableResource(id);
        return self();
    }

    public ChipWidget checkable(boolean checkable) {
        view.setCheckable(checkable);
        return self();
    }

    public boolean isCheckedIconVisible() {
        return view.isCheckedIconVisible();
    }

    public ChipWidget checkedIconVisible(@BoolRes int id) {
        view.setCheckedIconVisible(id);
        return self();
    }

    public ChipWidget checkedIconVisible(boolean checkedIconVisible) {
        view.setCheckedIconVisible(checkedIconVisible);
        return self();
    }

    public ChipWidget checkedIconResource(@DrawableRes int id) {
        view.setCheckedIconResource(id);
        return self();
    }

    public ChipWidget checkedIcon(@Nullable Drawable checkedIcon) {
        view.setCheckedIcon(checkedIcon);
        return self();
    }

    public ChipWidget checkedIconTint(@Nullable ColorStateList checkedIconTint) {
        Drawable checkedIcon = view.getCheckedIcon();
        if (checkedIcon != null) {
            DrawableCompat.setTintList(checkedIcon, checkedIconTint);
        }
        return self();
    }

    public ChipWidget showMotionSpecResource(@AnimatorRes int id) {
        view.setShowMotionSpecResource(id);
        return self();
    }

    public ChipWidget showMotionSpec(@Nullable MotionSpec showMotionSpec) {
        view.setShowMotionSpec(showMotionSpec);
        return self();
    }

    public ChipWidget hideMotionSpecResource(@AnimatorRes int id) {
        view.setHideMotionSpecResource(id);
        return self();
    }

    public ChipWidget hideMotionSpec(@Nullable MotionSpec hideMotionSpec) {
        view.setHideMotionSpec(hideMotionSpec);
        return self();
    }

    public ChipWidget chipStartPaddingResource(@DimenRes int id) {
        view.setChipStartPaddingResource(id);
        return self();
    }

    public ChipWidget chipStartPadding(float chipStartPadding) {
        view.setChipStartPadding(chipStartPadding);
        return self();
    }

    public ChipWidget iconStartPaddingResource(@DimenRes int id) {
        view.setIconStartPaddingResource(id);
        return self();
    }

    public ChipWidget iconStartPadding(float iconStartPadding) {
        view.setIconStartPadding(iconStartPadding);
        return self();
    }

    public ChipWidget iconEndPaddingResource(@DimenRes int id) {
        view.setIconEndPaddingResource(id);
        return self();
    }

    public ChipWidget iconEndPadding(float iconEndPadding) {
        view.setIconEndPadding(iconEndPadding);
        return self();
    }

    public ChipWidget textStartPaddingResource(@DimenRes int id) {
        view.setTextStartPaddingResource(id);
        return self();
    }

    public ChipWidget textStartPadding(float textStartPadding) {
        view.setTextStartPadding(textStartPadding);
        return self();
    }

    public ChipWidget textEndPaddingResource(@DimenRes int id) {
        view.setTextEndPaddingResource(id);
        return self();
    }

    public ChipWidget textEndPadding(float textEndPadding) {
        view.setTextEndPadding(textEndPadding);
        return self();
    }

    public ChipWidget closeIconStartPaddingResource(@DimenRes int id) {
        view.setCloseIconStartPaddingResource(id);
        return self();
    }

    public ChipWidget closeIconStartPadding(float closeIconStartPadding) {
        view.setCloseIconStartPadding(closeIconStartPadding);
        return self();
    }

    public ChipWidget closeIconEndPaddingResource(@DimenRes int id) {
        view.setCloseIconEndPaddingResource(id);
        return self();
    }

    public ChipWidget closeIconEndPadding(float closeIconEndPadding) {
        view.setCloseIconEndPadding(closeIconEndPadding);
        return self();
    }

    public ChipWidget chipEndPaddingResource(@DimenRes int id) {
        view.setChipEndPaddingResource(id);
        return self();
    }

    public ChipWidget chipEndPadding(float chipEndPadding) {
        view.setChipEndPadding(chipEndPadding);
        return self();
    }

    public ChipDrawable chipDrawable() {
        return (ChipDrawable) view.getChipDrawable();
    }

    public ChipWidget tintList(@Nullable ColorStateList tint) {
        chipDrawable().setTintList(tint);
        return self();
    }
}
