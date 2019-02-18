package com.ittianyu.relight.utils;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;

import com.ittianyu.relight.view.AndroidRender;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.AndroidWidget;
import com.ittianyu.relight.widget.stateful.state.State;
import com.ittianyu.relight.widget.stateful.state.strategy.FilterStrategy;

public class StateUtils {

    public static <V extends View> State<AndroidWidget<V>> create(AndroidRender<V> androidRender, Lifecycle lifecycle) {
        return create(androidRender, lifecycle, null);
    }

    public static <V extends View> State<AndroidWidget<V>> create(final AndroidRender<V> androidRender,
                                                                           final Lifecycle lifecycle,
                                                                           FilterStrategy updateStateStrategy) {
        return new State<AndroidWidget<V>>(updateStateStrategy) {
            private AndroidWidget<V> widget;

            @Override
            public AndroidWidget<V> build(Context context, Lifecycle lifecycle) {
                widget = WidgetUtils.createAndroidWidget(context, androidRender, lifecycle);
                return widget;
            }
        };
    }

    public static <V extends View, T extends Widget<V>> State<T> create(T widget) {
        return create(widget, null);
    }

    public static <V extends View, T extends Widget<V>> State<T> create(final T widget, FilterStrategy updateStateStrategy) {
        return new State<T>(updateStateStrategy) {
            @Override
            public T build(Context context, Lifecycle lifecycle) {
                return widget;
            }
        };
    }
}
