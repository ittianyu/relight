package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ittianyu.relight.utils.DensityUtils;
import com.ittianyu.relight.utils.ViewUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

public abstract class BaseAndroidWidget<T extends View> extends AndroidWidget<T> {
    public static final int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT;

    public int id;
    public Drawable background;
    public int marginStart;
    public int marginEnd;
    public int marginTop;
    public int marginBottom;
    public int margin;
    public int paddingStart;
    public int paddingEnd;
    public int paddingTop;
    public int paddingBottom;
    public int padding;
    public int width = wrapContent;
    public int height = wrapContent;
    public int layoutGravity = -1;
    public int weight = -1;
    public int visibility = View.VISIBLE;
    public View.OnClickListener onClickListener;

    public BaseAndroidWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    protected abstract void initProps();

    public BaseAndroidWidget<T> id(int id) {
        this.id = id;
        render().setId(id);
        return this;
    }

    public BaseAndroidWidget<T> background(Drawable drawable) {
        background = drawable;
        if (background != null)
            view.setBackground(background);
        return this;
    }

    public BaseAndroidWidget<T> background(Bitmap bitmap) {
        return background(new BitmapDrawable(context.getResources(), bitmap));
    }

    public BaseAndroidWidget<T> backgroundResource(@DrawableRes int res) {
        return background(drawable(res));
    }

    public BaseAndroidWidget<T> backgroundColor(@ColorInt int color) {
        return background(new ColorDrawable(color));
    }

    public BaseAndroidWidget<T> margin(int px) {
        this.margin = px;
        setMargin(px);
        updateMargin();
        return this;
    }

    public BaseAndroidWidget<T> margin(float dp) {
        return margin(dp(dp));
    }

    public BaseAndroidWidget<T> marginStart(int px) {
        this.marginStart = px;
        updateMargin();
        return this;
    }

    public BaseAndroidWidget<T> marginStart(float dp) {
        return marginStart(dp(dp));
    }

    public BaseAndroidWidget<T> marginEnd(int px) {
        this.marginEnd = px;
        updateMargin();
        return this;
    }

    public BaseAndroidWidget<T> marginEnd(float dp) {
        return marginEnd(dp(dp));
    }

    public BaseAndroidWidget<T> marginTop(int px) {
        this.marginTop = px;
        updateMargin();
        return this;
    }

    public BaseAndroidWidget<T> marginTop(float dp) {
        return marginTop(dp(dp));
    }

    public BaseAndroidWidget<T> marginBottom(int px) {
        this.marginBottom = px;
        updateMargin();
        return this;
    }

    public BaseAndroidWidget<T> marginBottom(float dp) {
        return marginBottom(dp(dp));
    }

    public BaseAndroidWidget<T> padding(int px) {
        this.padding = px;
        setPadding(px);
        updatePadding();
        return this;
    }

    public BaseAndroidWidget<T> padding(float dp) {
        return padding(dp(dp));
    }

    public BaseAndroidWidget<T> paddingStart(int px) {
        this.paddingStart = px;
        updatePadding();
        return this;
    }

    public BaseAndroidWidget<T> paddingStart(float dp) {
        return paddingStart(dp(dp));
    }

    public BaseAndroidWidget<T> paddingEnd(int px) {
        this.paddingEnd = px;
        updatePadding();
        return this;
    }

    public BaseAndroidWidget<T> paddingEnd(float dp) {
        return paddingEnd(dp(dp));
    }

    public BaseAndroidWidget<T> paddingTop(int px) {
        this.paddingTop = px;
        updatePadding();
        return this;
    }

    public BaseAndroidWidget<T> paddingTop(float dp) {
        return paddingTop(dp(dp));
    }

    public BaseAndroidWidget<T> paddingBottom(int px) {
        this.paddingBottom = px;
        updatePadding();
        return this;
    }

    public BaseAndroidWidget<T> paddingBottom(float dp) {
        return paddingBottom(dp(dp));
    }

    public BaseAndroidWidget<T> width(int px) {
        this.width = px;
        updateSize();
        return this;
    }

    public BaseAndroidWidget<T> width(float dp) {
        return width(dp(dp));
    }

    public BaseAndroidWidget<T> height(int px) {
        this.height = px;
        updateSize();
        return this;
    }

    public BaseAndroidWidget<T> height(float dp) {
        return height(dp(dp));
    }

    public BaseAndroidWidget<T> wrapContent() {
        this.width = wrapContent;
        this.height = wrapContent;
        updateSize();
        return this;
    }

    public BaseAndroidWidget<T> matchParent() {
        this.width = matchParent;
        this.height = matchParent;
        updateSize();
        return this;
    }

    public BaseAndroidWidget<T> onClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        view.setOnClickListener(onClickListener);
        return this;
    }

    public BaseAndroidWidget<T> layoutGravity(int layoutGravity) {
        this.layoutGravity = layoutGravity;
        return this;
    }

    public BaseAndroidWidget<T> weight(int weight) {
        this.weight = weight;
        return this;
    }

    private void updateMargin() {
        ViewUtils.setMargin(view, marginStart, marginTop, marginEnd, marginBottom);
    }

    private void updateSize() {
        ViewUtils.setSize(view, width, height);
    }

    private void updatePadding() {
        view.setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom);
    }

    private void setMargin(int margin) {
        marginStart = margin;
        marginEnd = margin;
        marginTop = margin;
        marginBottom = margin;
    }

    private void setPadding(int padding) {
        paddingStart = padding;
        paddingEnd = padding;
        paddingTop = padding;
        paddingBottom = padding;
    }

    protected int dp(float dp) {
        return DensityUtils.dip2px(context, dp);
    }

    protected int dp(double dp) {
        return dp((float) dp);
    }

    protected int dp(int dp) {
        return dp((float) dp);
    }

    protected int sp(int sp) {
        return sp(sp);
    }

    protected int sp(double sp) {
        return sp((float) sp);
    }

    protected int sp(float sp) {
        return DensityUtils.sp2px(context, sp);
    }

    protected int color(int resId) {
        return ContextCompat.getColor(context, resId);
    }

    protected String string(int resId) {
        return context.getResources().getString(resId);
    }

    protected Drawable drawable(int resId) {
        return ContextCompat.getDrawable(context, resId);
    }

    @Override
    public void initView(T view) {
        initProps();
        updateProps(view);
    }

    public void updateProps(T view) {
        if (id != 0)
            view.setId(id);
        if (margin != 0)
            setMargin(margin);
        if (padding != 0)
            setPadding(padding);
        updateSize();
        updateMargin();
        updatePadding();
        onClickListener(onClickListener);
        updateVisible();
    }

    private void updateVisible() {
        view.setVisibility(visibility);
    }

    @Override
    public T createView(Context context) {
        try {
            //noinspection unchecked
            Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Constructor<T> constructor = clazz.getConstructor(Context.class);
            return constructor.newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
