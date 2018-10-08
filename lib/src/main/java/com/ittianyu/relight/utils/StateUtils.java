package com.ittianyu.relight.utils;

import android.content.Context;
import android.view.View;

import com.ittianyu.relight.view.AndroidRender;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.AndroidWidget;
import com.ittianyu.relight.widget.stateful.AsyncState;

import android.arch.lifecycle.Lifecycle;

public class StateUtils {
    public static <T extends View> AsyncState<T> create(AndroidRender<T> androidRender, Lifecycle lifecycle) {
        return new AsyncState<T>() {
            private AndroidWidget<T> widget;

            @Override
            public Widget<T> build(Context context) {
                widget = WidgetUtils.createAndroidWidget(context, androidRender, lifecycle);
                return widget;
            }

            @Override
            public void update() {
                androidRender.updateView(widget.render());
            }
        };
    }

    public static <T extends View> AsyncState<T> create(AndroidWidget<T> androidWidget) {
        return new AsyncState<T>() {
            @Override
            public Widget<T> build(Context context) {
                return androidWidget;
            }

            @Override
            public void update() {
                androidWidget.updateView(androidWidget.render());
            }
        };
    }
}
