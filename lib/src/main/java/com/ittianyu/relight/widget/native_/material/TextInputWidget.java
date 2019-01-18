package com.ittianyu.relight.widget.native_.material;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.design.widget.TextInputLayout;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.BaseLinearWidget;

public class TextInputWidget extends BaseLinearWidget<TextInputLayout, TextInputWidget> {

    public TextInputWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    public TextInputWidget boxBackgroundMode(int boxBackgroundMode) {
        view.setBoxBackgroundMode(boxBackgroundMode);
        return self();
    }

    public TextInputWidget boxStrokeColor(@ColorInt int boxStrokeColor) {
        view.setBoxStrokeColor(boxStrokeColor);
        return self();
    }

    public TextInputWidget boxBackgroundColorResource(@ColorRes int boxBackgroundColorId) {
        view.setBoxBackgroundColorResource(boxBackgroundColorId);
        return self();
    }

    public TextInputWidget boxBackgroundColor(@ColorInt int boxBackgroundColor) {
        view.setBoxBackgroundColor(boxBackgroundColor);
        return self();
    }

    public TextInputWidget boxCornerRadiiResources(@DimenRes int boxCornerRadiusTopStartId, @DimenRes int boxCornerRadiusTopEndId, @DimenRes int boxCornerRadiusBottomEndId, @DimenRes int boxCornerRadiusBottomStartId) {
        view.setBoxCornerRadiiResources(boxCornerRadiusTopStartId, boxCornerRadiusTopEndId, boxCornerRadiusBottomEndId, boxCornerRadiusBottomStartId);
        return self();
    }

    public TextInputWidget boxCornerRadii(float boxCornerRadiusTopStart, float boxCornerRadiusTopEnd, float boxCornerRadiusBottomStart, float boxCornerRadiusBottomEnd) {
        view.setBoxCornerRadii(boxCornerRadiusTopStart, boxCornerRadiusTopEnd, boxCornerRadiusBottomStart, boxCornerRadiusBottomEnd);
        return self();
    }

    public TextInputWidget typeface(@Nullable Typeface typeface) {
        view.setTypeface(typeface);
        return self();
    }

    public TextInputWidget hint(@Nullable CharSequence hint) {
        view.setHint(hint);
        return self();
    }

    public TextInputWidget hintEnabled(boolean enabled) {
        view.setHintEnabled(enabled);
        return self();
    }

    public TextInputWidget hintTextAppearance(@StyleRes int resId) {
        view.setHintTextAppearance(resId);
        return self();
    }

    public TextInputWidget defaultHintTextColor(@Nullable ColorStateList textColor) {
        view.setDefaultHintTextColor(textColor);
        return self();
    }

    public TextInputWidget errorEnabled(boolean enabled) {
        view.setErrorEnabled(enabled);
        return self();
    }

    public TextInputWidget errorTextAppearance(@StyleRes int resId) {
        view.setErrorTextAppearance(resId);
        return self();
    }

    public TextInputWidget errorTextColor(@Nullable ColorStateList textColors) {
        view.setErrorTextColor(textColors);
        return self();
    }

    public TextInputWidget helperTextTextAppearance(@StyleRes int resId) {
        view.setHelperTextTextAppearance(resId);
        return self();
    }

    public TextInputWidget helperTextEnabled(boolean enabled) {
        view.setHelperTextEnabled(enabled);
        return self();
    }

    public TextInputWidget helperText(@Nullable CharSequence helperText) {
        view.setHelperText(helperText);
        return self();
    }

    public TextInputWidget helperTextColor(@Nullable ColorStateList textColors) {
        view.setHelperTextColor(textColors);
        return self();
    }

    public TextInputWidget error(@Nullable CharSequence errorText) {
        view.setError(errorText);
        return self();
    }

    public TextInputWidget counterEnabled(boolean enabled) {
        view.setCounterEnabled(enabled);
        return self();
    }

    public TextInputWidget counterMaxLength(int maxLength) {
        view.setCounterMaxLength(maxLength);
        return self();
    }

    public TextInputWidget hintAnimationEnabled(boolean enabled) {
        view.setHintAnimationEnabled(enabled);
        return self();
    }

    public TextInputWidget passwordVisibilityToggleDrawable(@DrawableRes int resId) {
        view.setPasswordVisibilityToggleDrawable(resId);
        return self();
    }

    public TextInputWidget passwordVisibilityToggleDrawable(@Nullable Drawable icon) {
        view.setPasswordVisibilityToggleDrawable(icon);
        return self();
    }

    public TextInputWidget passwordVisibilityToggleContentDescription(@StringRes int resId) {
        view.setPasswordVisibilityToggleContentDescription(resId);
        return self();
    }

    public TextInputWidget passwordVisibilityToggleContentDescription(@Nullable CharSequence description) {
        view.setPasswordVisibilityToggleContentDescription(description);
        return self();
    }

    public TextInputWidget passwordVisibilityToggleEnabled(boolean enabled) {
        view.setPasswordVisibilityToggleEnabled(enabled);
        return self();
    }

    public TextInputWidget setPasswordVisibilityToggleTintList(@Nullable ColorStateList tintList) {
        view.setPasswordVisibilityToggleTintList(tintList);
        return self();
    }

    public TextInputWidget setPasswordVisibilityToggleTintMode(@Nullable Mode mode) {
        view.setPasswordVisibilityToggleTintMode(mode);
        return self();
    }

    public TextInputWidget passwordVisibilityToggleRequested(boolean shouldSkipAnimations) {
        view.passwordVisibilityToggleRequested(shouldSkipAnimations);
        return self();
    }

    public TextInputWidget textInputAccessibilityDelegate(TextInputLayout.AccessibilityDelegate delegate) {
        view.setTextInputAccessibilityDelegate(delegate);
        return self();
    }


}
