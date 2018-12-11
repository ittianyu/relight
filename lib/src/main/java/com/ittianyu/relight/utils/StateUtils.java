package com.ittianyu.relight.utils;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;

import com.ittianyu.relight.view.AndroidRender;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.AndroidWidget;
import com.ittianyu.relight.widget.stateful.state.AsyncState;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

public class StateUtils {

    public static <V extends View> AsyncState<AndroidWidget<V>> create(AndroidRender<V> androidRender, Lifecycle lifecycle) {
        return create(androidRender, lifecycle, AsyncState.DEFAULT_UPDATE_STATE_STRATEGY);
    }

    public static <V extends View> AsyncState<AndroidWidget<V>> create(AndroidRender<V> androidRender,
                                                                       Lifecycle lifecycle, AsyncState.UpdateStateStrategy updateStateStrategy) {
        return new AsyncState<AndroidWidget<V>>(updateStateStrategy) {
            private AndroidWidget<V> widget;

            @Override
            public AndroidWidget<V> build(Context context) {
                widget = WidgetUtils.createAndroidWidget(context, androidRender, lifecycle);
                return widget;
            }

            @Override
            public void update() {
                super.update();
                androidRender.updateView(widget.render());
            }
        };
    }

    public static <V extends View, T extends Widget<V>> AsyncState<T> create(T widget) {
        return create(widget, AsyncState.DEFAULT_UPDATE_STATE_STRATEGY);
    }

    public static <V extends View, T extends Widget<V>> AsyncState<T> create(T widget, AsyncState.UpdateStateStrategy updateStateStrategy) {
        return new AsyncState<T>(updateStateStrategy) {
            @Override
            public T build(Context context) {
                return widget;
            }

            @Override
            public void update() {
                super.update();
                if (widget instanceof AndroidWidget) {
                    ((AndroidWidget) widget).updateView(widget.render());
                } else if (widget instanceof StatelessWidget) {
                    ((StatelessWidget) widget).update(widget);
                } else if (widget instanceof StatefulWidget) {
                    ((StatefulWidget) widget).update();
                }
            }
        };
    }
}
