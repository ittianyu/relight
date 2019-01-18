package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import com.ittianyu.relight.widget.Widget;

public class WidgetPager extends ViewGroupWidget<ViewPager, WidgetPager> {

    public WidgetPager(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public WidgetPager adapter(@Nullable WidgetPagerAdapter adapter) {
        view.setAdapter(adapter);
        return self();
    }

    public WidgetPager addOnAdapterChangeListener(@NonNull ViewPager.OnAdapterChangeListener listener) {
        view.addOnAdapterChangeListener(listener);
        return self();
    }

    public WidgetPager removeOnAdapterChangeListener(@NonNull ViewPager.OnAdapterChangeListener listener) {
        view.removeOnAdapterChangeListener(listener);
        return self();
    }

    public WidgetPager currentItem(int item) {
        view.setCurrentItem(item);
        return self();
    }

    public WidgetPager currentItem(int item, boolean smoothScroll) {
        view.setCurrentItem(item, smoothScroll);
        return self();
    }

    public int currentItem() {
        return view.getCurrentItem();
    }

    public WidgetPager addOnPageChangeListener(@NonNull ViewPager.OnPageChangeListener listener) {
        view.addOnPageChangeListener(listener);
        return self();
    }

    public WidgetPager removeOnPageChangeListener(@NonNull ViewPager.OnPageChangeListener listener) {
        view.removeOnPageChangeListener(listener);
        return self();
    }

    public WidgetPager clearOnPageChangeListeners() {
        view.clearOnPageChangeListeners();
        return self();
    }

    public WidgetPager pageTransformer(boolean reverseDrawingOrder, @Nullable ViewPager.PageTransformer transformer) {
        view.setPageTransformer(reverseDrawingOrder, transformer);
        return self();
    }

    public WidgetPager pageTransformer(boolean reverseDrawingOrder, @Nullable ViewPager.PageTransformer transformer, int pageLayerType) {
        view.setPageTransformer(reverseDrawingOrder, transformer, pageLayerType);
        return self();
    }

    public WidgetPager offscreenPageLimit(int limit) {
        view.setOffscreenPageLimit(limit);
        return self();
    }

    public WidgetPager pageMargin(int marginPixels) {
        view.setPageMargin(marginPixels);
        return self();
    }

    public WidgetPager pageMarginDrawable(@Nullable Drawable d) {
        view.setPageMarginDrawable(d);
        return self();
    }

    public WidgetPager pageMarginDrawable(@DrawableRes int resId) {
        view.setPageMarginDrawable(resId);
        return self();
    }


}
