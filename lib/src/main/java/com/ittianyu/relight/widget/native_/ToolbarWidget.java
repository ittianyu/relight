package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.ittianyu.relight.utils.ViewUtils;
import com.ittianyu.relight.widget.ContainerWidget;
import com.ittianyu.relight.widget.Widget;

public class ToolbarWidget extends ViewGroupWidget<Toolbar, ToolbarWidget> {

    public ToolbarWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    @Override
    protected void initProps() {
        super.initProps();
        width = matchParent;
    }

    @Override
    public void updateProps(Toolbar view) {
        super.updateProps(view);
        for (Widget widget : children) {
            while (widget instanceof ContainerWidget) {
                widget = ((ContainerWidget) widget).getInnerWidget();
            }
            if (widget instanceof BaseAndroidWidget) {
                View v = widget.render();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                if (layoutParams instanceof Toolbar.LayoutParams) {
                    Integer layoutGravity = ((BaseAndroidWidget) widget).layoutGravity;
                    if (layoutGravity != null)
                        ViewUtils.setLayoutGravity((Toolbar.LayoutParams) layoutParams, v, layoutGravity);
                }
            }
        }
    }

    public ToolbarWidget titleMargin(int start, int top, int end, int bottom) {
        view.setTitleMargin(start, top, end, bottom);
        return self();
    }

    public ToolbarWidget titleMarginStart(int margin) {
        view.setTitleMarginStart(margin);
        return self();
    }

    public ToolbarWidget titleMarginEnd(int margin) {
        view.setTitleMarginEnd(margin);
        return self();
    }

    public ToolbarWidget titleMarginTop(int margin) {
        view.setTitleMarginTop(margin);
        return self();
    }

    public ToolbarWidget titleMarginBottom(int margin) {
        view.setTitleMarginBottom(margin);
        return self();
    }

    public boolean showOverflowMenu() {
        return view.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return view.hideOverflowMenu();
    }

    public ToolbarWidget logo(Drawable drawable) {
        view.setLogo(drawable);
        return self();
    }

    public ToolbarWidget logo(@DrawableRes int resId) {
        view.setLogo(resId);
        return self();
    }

    public ToolbarWidget logoDescription(@StringRes int resId) {
        view.setLogoDescription(resId);
        return self();
    }

    public ToolbarWidget collapseActionView() {
        view.collapseActionView();
        return self();
    }

    public ToolbarWidget title(@StringRes int resId) {
        view.setTitle(resId);
        return self();
    }

    public ToolbarWidget title(CharSequence title) {
        view.setTitle(title);
        return self();
    }

    public ToolbarWidget subtitle(@StringRes int resId) {
        view.setSubtitle(resId);
        return self();
    }

    public ToolbarWidget subtitle(CharSequence title) {
        view.setSubtitle(title);
        return self();
    }

    public ToolbarWidget titleTextAppearance(Context context, @StyleRes int resId) {
        view.setTitleTextAppearance(context, resId);
        return self();
    }

    public ToolbarWidget subtitleTextAppearance(Context context, @StyleRes int resId) {
        view.setSubtitleTextAppearance(context, resId);
        return self();
    }

    public ToolbarWidget titleTextColor(@ColorInt int color) {
        view.setTitleTextColor(color);
        return self();
    }

    public ToolbarWidget subtitleTextColor(@ColorInt int color) {
        view.setSubtitleTextColor(color);
        return self();
    }

    public ToolbarWidget navigationContentDescription(@StringRes int resId) {
        view.setNavigationContentDescription(resId);
        return self();
    }

    public ToolbarWidget navigationContentDescription(@Nullable CharSequence description) {
        view.setNavigationContentDescription(description);
        return self();
    }

    public ToolbarWidget navigationIcon(@DrawableRes int resId) {
        view.setNavigationIcon(resId);
        return self();
    }

    public ToolbarWidget navigationIcon(@Nullable Drawable icon) {
        view.setNavigationIcon(icon);
        return self();
    }

    public ToolbarWidget navigationOnClick(OnClickListener listener) {
        view.setNavigationOnClickListener(listener);
        return self();
    }

    public ToolbarWidget overflowIcon(@Nullable Drawable icon) {
        view.setOverflowIcon(icon);
        return self();
    }

    public ToolbarWidget inflateMenu(@MenuRes int resId) {
        view.inflateMenu(resId);
        return self();
    }

    public ToolbarWidget onMenuItemClick(Toolbar.OnMenuItemClickListener listener) {
        view.setOnMenuItemClickListener(listener);
        return self();
    }

    public ToolbarWidget contentInsetsRelative(int contentInsetStart, int contentInsetEnd) {
        view.setContentInsetsRelative(contentInsetStart, contentInsetEnd);
        return self();
    }

    public ToolbarWidget contentInsetsAbsolute(int contentInsetLeft, int contentInsetRight) {
        view.setContentInsetsAbsolute(contentInsetLeft, contentInsetRight);
        return self();
    }

    public ToolbarWidget contentInsetStartWithNavigation(int insetStartWithNavigation) {
        view.setContentInsetStartWithNavigation(insetStartWithNavigation);
        return self();
    }
}
