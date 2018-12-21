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

import com.ittianyu.relight.utils.DensityUtils;
import com.ittianyu.relight.utils.ViewUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

public abstract class BaseAndroidWidget<V extends View, T extends BaseAndroidWidget> extends AndroidWidget<V> {
    public static final int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT;

    public Integer id;
    public Drawable background;
    public Integer marginStart;
    public Integer marginEnd;
    public Integer marginTop;
    public Integer marginBottom;
    public Integer margin;
    public Integer paddingStart;
    public Integer paddingEnd;
    public Integer paddingTop;
    public Integer paddingBottom;
    public Integer padding;
    public Integer width = wrapContent;
    public Integer height = wrapContent;
    public Integer layoutGravity;
    public Integer weight;
    public Integer visibility;
    public View.OnClickListener onClickListener;

    public BaseAndroidWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    protected abstract void initProps();

    protected final T self() {
        //noinspection unchecked
        return (T) this;
    }

    public T id(int id) {
        this.id = id;
        view.setId(id);
        return self();
    }

    public T background(Drawable drawable) {
        background = drawable;
        view.setBackground(background);
        return self();
    }

    public T background(Bitmap bitmap) {
        return background(new BitmapDrawable(context.getResources(), bitmap));
    }

    public T backgroundResource(@DrawableRes int res) {
        return background(drawable(res));
    }

    public T backgroundColor(@ColorInt int color) {
        return background(new ColorDrawable(color));
    }

    public T margin(int px) {
        this.margin = px;
        setMargin(px);
        updateMargin();
        return self();
    }

    public T margin(float dp) {
        return margin(dp(dp));
    }

    public T marginStart(int px) {
        this.marginStart = px;
        updateMargin();
        return self();
    }

    public T marginStart(float dp) {
        return marginStart(dp(dp));
    }

    public T marginEnd(int px) {
        this.marginEnd = px;
        updateMargin();
        return self();
    }

    public T marginEnd(float dp) {
        return marginEnd(dp(dp));
    }

    public T marginTop(int px) {
        this.marginTop = px;
        updateMargin();
        return self();
    }

    public T marginTop(float dp) {
        return marginTop(dp(dp));
    }

    public T marginBottom(int px) {
        this.marginBottom = px;
        updateMargin();
        return self();
    }

    public T marginBottom(float dp) {
        return marginBottom(dp(dp));
    }

    public T padding(int px) {
        this.padding = px;
        setPadding(px);
        updatePadding();
        return self();
    }

    public T padding(float dp) {
        return padding(dp(dp));
    }

    public T paddingStart(int px) {
        this.paddingStart = px;
        updatePadding();
        return self();
    }

    public T paddingStart(float dp) {
        return paddingStart(dp(dp));
    }

    public T paddingEnd(int px) {
        this.paddingEnd = px;
        updatePadding();
        return self();
    }

    public T paddingEnd(float dp) {
        return paddingEnd(dp(dp));
    }

    public T paddingTop(int px) {
        this.paddingTop = px;
        updatePadding();
        return self();
    }

    public T paddingTop(float dp) {
        return paddingTop(dp(dp));
    }

    public T paddingBottom(int px) {
        this.paddingBottom = px;
        updatePadding();
        return self();
    }

    public T paddingBottom(float dp) {
        return paddingBottom(dp(dp));
    }

    public T paddingHorizontal(int px) {
        this.paddingStart = px;
        this.paddingEnd = px;
        updatePadding();
        return self();
    }

    public T paddingHorizontal(float dp) {
        return paddingHorizontal(dp(dp));
    }

    public T paddingVertical(int px) {
        this.paddingTop = px;
        this.paddingBottom = px;
        updatePadding();
        return self();
    }

    public T paddingVertical(float dp) {
        return paddingVertical(dp(dp));
    }



    public T width(int px) {
        this.width = px;
        updateSize();
        return self();
    }

    public T width(float dp) {
        return width(dp(dp));
    }

    public T height(int px) {
        this.height = px;
        updateSize();
        return self();
    }

    public T height(float dp) {
        return height(dp(dp));
    }

    public T wrapContent() {
        this.width = wrapContent;
        this.height = wrapContent;
        updateSize();
        return self();
    }

    public T matchParent() {
        this.width = matchParent;
        this.height = matchParent;
        updateSize();
        return self();
    }

    public T widthMatchAndHeightWrap() {
        this.width = matchParent;
        this.height = wrapContent;
        updateSize();
        return self();
    }

    public T onClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        view.setOnClickListener(onClickListener);
        return self();
    }

    public T layoutGravity(int layoutGravity) {
        this.layoutGravity = layoutGravity;
        return self();
    }

    public T weight(int weight) {
        this.weight = weight;
        return self();
    }

    private void updateMargin() {
        ViewUtils.setMargin(view, marginStart, marginTop, marginEnd, marginBottom);
    }

    private void updateSize() {
        ViewUtils.setSize(view, width, height);
    }

    private void updatePadding() {
        if (paddingStart == null)
            paddingStart = view.getPaddingStart();
        if (paddingTop == null)
            paddingTop = view.getPaddingTop();
        if (paddingEnd == null)
            paddingEnd = view.getPaddingEnd();
        if (paddingBottom == null)
            paddingBottom = view.getPaddingBottom();

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
    public void initView(V view) {
        super.initView(view);

        initProps();
        updateProps(view);
    }

    public void updateProps(V view) {
        if (id != null)
            id(id);
        if (margin != null)
            setMargin(margin);
        if (padding != null)
            setPadding(padding);
        if (background != null)
            background(background);
        updateSize();
        updateMargin();
        updatePadding();
        onClickListener(onClickListener);
        updateVisible();
    }

    private void updateVisible() {
        if (visibility != null) {
            view.setVisibility(visibility);
        }
    }

    @Override
    public V createView(Context context) {
        try {
            //noinspection unchecked
            Class<V> clazz = (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Constructor<V> constructor = clazz.getConstructor(Context.class);
            return constructor.newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
