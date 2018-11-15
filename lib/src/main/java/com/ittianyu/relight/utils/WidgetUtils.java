package com.ittianyu.relight.utils;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ittianyu.relight.view.AndroidRender;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.AndroidWidget;

import java.lang.reflect.Constructor;

public class WidgetUtils {

    public static <T extends View> AndroidWidget<T> createAndroidWidget(Context context, final AndroidRender<T> androidRender, Lifecycle lifecycle) {
        return new AndroidWidget<T>(context, lifecycle) {
            @Override
            public T createView(Context context) {
                return androidRender.createView(context);
            }

            @Override
            public void initView(T view) {
                androidRender.initView(view);
            }

            @Override
            public void updateView(T view) {
                androidRender.updateView(view);
            }

            @Override
            public void bindEvent(T view) {
                androidRender.bindEvent(view);
            }

            @Override
            public void initData() {
                androidRender.initData();
            }
        };
    }

    public static <T extends View> Widget<T> create(final T view) {
        return new Widget<T>() {
            @Override
            public T render() {
                return view;
            }
        };
    }

    public static <T extends View> T render(AppCompatActivity activity, Class<? extends Widget<T>> widgetClass, Object... params) {
        Class[] clazzs = new Class[params.length + 2];
        clazzs[0] = Context.class;
        clazzs[1] = Lifecycle.class;
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            clazzs[i + 2] = param.getClass();
        }

        Object[] ps = new Object[params.length + 2];
        ps[0] = activity;
        ps[1] = activity.getLifecycle();
        System.arraycopy(params, 0, ps, 2, params.length);
        try {
            Constructor<? extends Widget<T>> constructor = widgetClass.getConstructor(clazzs);
            Widget<T> widget = constructor.newInstance(ps);
            return widget.render();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
