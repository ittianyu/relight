package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.ittianyu.relight.widget.Widget;

public abstract class WidgetPagerAdapter<T extends Widget> extends PagerAdapter {

    protected Context context;
    protected Lifecycle lifecycle;

    public WidgetPagerAdapter(Context context, Lifecycle lifecycle) {
        this.context = context;
        this.lifecycle = lifecycle;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        T widget = getItem(position);
        container.addView(widget.render());
        return widget;
    }

    protected abstract T getItem(int position);

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(((T) object).render());
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((T) object).render() == view;
    }

}
