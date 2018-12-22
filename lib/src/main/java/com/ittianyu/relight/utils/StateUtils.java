package com.ittianyu.relight.utils;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;

import com.ittianyu.relight.view.AndroidRender;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.AndroidWidget;
import com.ittianyu.relight.widget.native_.LifecycleAndroidWidget;
import com.ittianyu.relight.widget.stateful.state.State;
import com.ittianyu.relight.widget.stateful.state.strategy.FilterStrategy;

public class StateUtils {

    public static <V extends View> State<AndroidWidget<V>> create(AndroidRender<V> androidRender, Lifecycle lifecycle) {
        return create(androidRender, lifecycle, State.DEFAULT_UPDATE_STATE_STRATEGY);
    }

    public static <V extends View> State<AndroidWidget<V>> create(AndroidRender<V> androidRender,
                                                                           Lifecycle lifecycle,
                                                                           FilterStrategy updateStateStrategy) {
        return new State<AndroidWidget<V>>(updateStateStrategy) {
            private LifecycleAndroidWidget<V> widget;

            @Override
            public AndroidWidget<V> build(Context context) {
                widget = WidgetUtils.createAndroidWidget(context, androidRender, lifecycle);
                return widget;
            }
        };
    }

    public static <V extends View, T extends Widget<V>> State<T> create(T widget) {
        return create(widget, State.DEFAULT_UPDATE_STATE_STRATEGY);
    }

    public static <V extends View, T extends Widget<V>> State<T> create(T widget, FilterStrategy updateStateStrategy) {
        return new State<T>(updateStateStrategy) {
            @Override
            public T build(Context context) {
                return widget;
            }
        };
    }
}
