package com.ittianyu.relight.widget.native_;

import android.animation.StateListAnimator;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.ittianyu.relight.utils.DensityUtils;
import com.ittianyu.relight.utils.ViewUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;

public abstract class BaseAndroidWidget<V extends View, T extends BaseAndroidWidget> extends AndroidWidget<V> {
    public static final Integer matchParent = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final Integer wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT;

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
    private OnClickListener onClick;
    private RelativeLayout.LayoutParams relativeParams;
    private boolean addOnAttachListener;
    private OnAttachStateChangeListener onAttachStateChangeListener = new OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(View v) {
            // no need to run updateProps if no LayoutParams(when it not attach to parent, it won't has it)
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (null != layoutParams) {
                updateProps(view);
            }
        }

        @Override
        public void onViewDetachedFromWindow(View v) {

        }
    };

    public BaseAndroidWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    protected abstract void initProps();

    @Override
    public void onStart() {
        if (!addOnAttachListener) {
            addOnAttachListener = true;
            view.addOnAttachStateChangeListener(onAttachStateChangeListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view.removeOnAttachStateChangeListener(onAttachStateChangeListener);
    }

    protected final T self() {
        //noinspection unchecked
        return (T) this;
    }

    public T id(Integer id) {
        view.setId(id);
        return self();
    }

    public T background(Drawable drawable) {
        view.setBackground(drawable);
        return self();
    }

    public T background(Bitmap bitmap) {
        return background(new BitmapDrawable(context.getResources(), bitmap));
    }

    public T backgroundResource(@DrawableRes Integer res) {
        return background(drawable(res));
    }

    public T backgroundColor(@ColorInt Integer color) {
        return background(new ColorDrawable(color));
    }

    public T margin(Integer px) {
        this.margin = px;
        setMargin(px);
        updateMargin();
        return self();
    }

    public T margin(Float dp) {
        return margin(dp(dp));
    }

    public T marginStart(Integer px) {
        this.marginStart = px;
        updateMargin();
        return self();
    }

    public T marginStart(Float dp) {
        return marginStart(dp(dp));
    }

    public T marginEnd(Integer px) {
        this.marginEnd = px;
        updateMargin();
        return self();
    }

    public T marginEnd(Float dp) {
        return marginEnd(dp(dp));
    }

    public T marginTop(Integer px) {
        this.marginTop = px;
        updateMargin();
        return self();
    }

    public T marginTop(Float dp) {
        return marginTop(dp(dp));
    }

    public T marginBottom(Integer px) {
        this.marginBottom = px;
        updateMargin();
        return self();
    }

    public T marginBottom(Float dp) {
        return marginBottom(dp(dp));
    }

    public T padding(Integer px) {
        this.padding = px;
        setPadding(px);
        updatePadding();
        return self();
    }

    public T padding(Float dp) {
        return padding(dp(dp));
    }

    public T paddingStart(Integer px) {
        this.paddingStart = px;
        updatePadding();
        return self();
    }

    public T paddingStart(Float dp) {
        return paddingStart(dp(dp));
    }

    public T paddingEnd(Integer px) {
        this.paddingEnd = px;
        updatePadding();
        return self();
    }

    public T paddingEnd(Float dp) {
        return paddingEnd(dp(dp));
    }

    public T paddingTop(Integer px) {
        this.paddingTop = px;
        updatePadding();
        return self();
    }

    public T paddingTop(Float dp) {
        return paddingTop(dp(dp));
    }

    public T paddingBottom(Integer px) {
        this.paddingBottom = px;
        updatePadding();
        return self();
    }

    public T paddingBottom(Float dp) {
        return paddingBottom(dp(dp));
    }

    public T paddingHorizontal(Integer px) {
        this.paddingStart = px;
        this.paddingEnd = px;
        updatePadding();
        return self();
    }

    public T paddingHorizontal(Float dp) {
        return paddingHorizontal(dp(dp));
    }

    public T paddingVertical(Integer px) {
        this.marginTop = px;
        this.marginBottom = px;
        updatePadding();
        return self();
    }

    public T paddingVertical(Float dp) {
        return paddingVertical(dp(dp));
    }

    public T marginVertical(Integer px) {
        this.marginTop = px;
        this.marginBottom = px;
        updateMargin();
        return self();
    }

    public T marginVertical(Float dp) {
        return marginVertical(dp(dp));
    }

    public T marginHorizontal(Integer px) {
        this.marginStart = px;
        this.marginEnd = px;
        updateMargin();
        return self();
    }

    public T marginHorizontal(Float dp) {
        return marginHorizontal(dp(dp));
    }

    public T width(Integer px) {
        this.width = px;
        updateSize();
        return self();
    }

    public T width(Float dp) {
        return width(dp(dp));
    }

    public T height(Integer px) {
        this.height = px;
        updateSize();
        return self();
    }

    public T height(Float dp) {
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

    public T clickable(Boolean clickable) {
        view.setClickable(clickable);
        return self();
    }

    public T onClick(View.OnClickListener onClickListener) {
        return onClickListener(onClickListener);
    }

    public OnClickListener onClick() {
        return onClick;
    }

    public T onClickListener(View.OnClickListener onClickListener) {
        this.onClick = onClickListener;
        view.setOnClickListener(onClickListener);
        return self();
    }

    public T layoutGravity(Integer layoutGravity) {
        this.layoutGravity = layoutGravity;
        return self();
    }

    public T weight(Integer weight) {
        this.weight = weight;
        ViewGroup.LayoutParams lp = view.getLayoutParams();
//        if (lp == null) {
//            lp = new LinearLayout.LayoutParams(width, height, weight);
//            view.setLayoutParams(lp);
//        } else
        if (lp instanceof LinearLayout.LayoutParams) {
            ViewUtils.setWeight((LinearLayout.LayoutParams) lp, view, weight);
        }
        return self();
    }

    public T addRule(Integer verb, Integer rule) {
        if (null == relativeParams) {
            relativeParams = new RelativeLayout.LayoutParams(wrapContent, wrapContent);
        }
        relativeParams.addRule(verb, rule);
        return self();
    }

    public T removeRule(Integer verb) {
        if (null != relativeParams) {
            relativeParams.removeRule(verb);
        }
        return self();
    }

    public T elevation(Float dp) {
        ViewCompat.setElevation(view, dp);
        return self();
    }

    public T backgroundTintList(ColorStateList list) {
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            view.setBackgroundTintList(list);
        }
        return self();
    }

    public T backgroundTintMode(@Nullable Mode tintMode) {
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            view.setBackgroundTintMode(tintMode);
        }
        return self();
    }

    public T foreground(Drawable foreground) {
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            view.setForeground(foreground);
        }
        return self();
    }

    public T foregroundGravity(Integer gravity) {
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            view.setForegroundGravity(gravity);
        }
        return self();
    }

    public T focusable(Boolean focusable) {
        view.setFocusable(focusable);
        return self();
    }

    @RequiresApi(api = VERSION_CODES.O)
    public T focusable(Integer focusable) {
        view.setFocusable(focusable);
        return self();
    }

    public T stateListAnimator(StateListAnimator stateListAnimator) {
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            view.setStateListAnimator(stateListAnimator);
        }
        return self();
    }

    public T setEnabled(Boolean enabled) {
        view.setEnabled(enabled);
        return self();
    }

    public T visibility(Integer visibility) {
        view.setVisibility(visibility);
        return self();
    }

    public T visibility(Boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return self();
    }

    public T gone() {
        return visibility(View.GONE);
    }

    public T visible() {
        return visibility(View.VISIBLE);
    }

    public T invisible() {
        return visibility(View.INVISIBLE);
    }

    public T fadingEdgeLength(int length) {
        view.setFadingEdgeLength(length);
        return self();
    }

    protected void mergeLayoutParams() {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (!(lp instanceof RelativeLayout.LayoutParams)) {
            return;
        }
        if (null == relativeParams) {
            return;
        }
        RelativeLayout.LayoutParams rlp = (LayoutParams) lp;
        int[] rules = relativeParams.getRules();
        for (int i = 0, n = rules.length; i < n; i++) {
            rlp.addRule(i, rules[i]);
        }
        rlp.alignWithParent = relativeParams.alignWithParent;
        view.setLayoutParams(rlp);
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

    private void setMargin(Integer margin) {
        marginStart = margin;
        marginEnd = margin;
        marginTop = margin;
        marginBottom = margin;
    }

    private void setPadding(Integer padding) {
        paddingStart = padding;
        paddingEnd = padding;
        paddingTop = padding;
        paddingBottom = padding;
    }

    protected Integer dp(Double dp) {
        return dp(dp.floatValue());
    }

    protected Integer dp(Integer dp) {
        return dp(dp.floatValue());
    }

    protected Integer dp(Float dp) {
        return DensityUtils.dip2px(context, dp);
    }

    protected Integer sp(Integer sp) {
        return sp(sp.floatValue());
    }

    protected Integer sp(Double sp) {
        return sp(sp.floatValue());
    }

    protected Integer sp(Float sp) {
        return DensityUtils.sp2px(context, sp);
    }

    protected Integer color(Integer resId) {
        return ContextCompat.getColor(context, resId);
    }

    protected String string(Integer resId) {
        return context.getResources().getString(resId);
    }

    protected Drawable drawable(Integer resId) {
        return ContextCompat.getDrawable(context, resId);
    }

    @Override
    public void initView(V view) {
        super.initView(view);
        initProps();
    }

    public void updateProps(V view) {
        updateSize();
        updateMargin();
    }

    @Override
    public V createView(Context context) {
        try {
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            if (null == parameterizedType) {
                return null;
            }
            //noinspection unchecked
            Class<V> clazz = (Class<V>) parameterizedType.getActualTypeArguments()[0];
            Constructor<V> constructor = clazz.getConstructor(Context.class);
            return constructor.newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
