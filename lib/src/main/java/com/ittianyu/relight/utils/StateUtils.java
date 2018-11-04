package com.ittianyu.relight.utils;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;

import com.ittianyu.relight.view.AndroidRender;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.AndroidWidget;
import com.ittianyu.relight.widget.stateful.AsyncState;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

public class StateUtils {
    public static <V extends View> AsyncState<AndroidWidget<V>> create(AndroidRender<V> androidRender, Lifecycle lifecycle) {
        return new AsyncState<AndroidWidget<V>>() {
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
        return new AsyncState<T>() {
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
