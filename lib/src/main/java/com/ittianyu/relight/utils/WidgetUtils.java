package com.ittianyu.relight.utils;

import android.content.Context;
import android.view.View;

import com.ittianyu.relight.view.AndroidRender;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.atomic.AndroidWidget;

import java.lang.reflect.Constructor;

import android.support.v7.app.AppCompatActivity;
import android.arch.lifecycle.Lifecycle;

public class WidgetUtils {

    public static <T extends View> AndroidWidget<T> createAndroidWidget(Context context, AndroidRender<T> androidRender, Lifecycle lifecycle) {
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

    public static <T extends View> Widget<T> create(T view) {
        return () -> view;
    }

    public static <T extends View> T create(AppCompatActivity activity, Class<? extends Widget<T>> widgetClass, Object... params) {
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
        for (int i = 0; i < params.length; i++) {
            ps[i + 2] = params[i];
        }
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
