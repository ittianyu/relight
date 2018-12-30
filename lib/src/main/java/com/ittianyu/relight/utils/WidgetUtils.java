package com.ittianyu.relight.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ittianyu.relight.view.AndroidRender;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.AndroidWidget;

import java.lang.reflect.Constructor;

public class WidgetUtils {

    public static <V extends View> AndroidWidget<V> createAndroidWidget(Context context, final AndroidRender<V> androidRender, Lifecycle lifecycle) {
        return new AndroidWidget<V>(context, lifecycle) {
            @Override
            public V createView(Context context) {
                return androidRender.createView(context);
            }

            @Override
            public void initView(V view) {
                androidRender.initView(view);
            }

            @Override
            public void initEvent(V view) {
                androidRender.initEvent(view);
            }

            @Override
            public void initData() {
                androidRender.initData();
            }
        };
    }

    public static <V extends View> Widget<V> create(Context context, Lifecycle lifecycle, final V view) {
        return new Widget<V>(context, lifecycle) {
            @Override
            public void update() {
            }

            @Override
            public V render() {
                return view;
            }
        };
    }

    public static <V extends View> Widget<V> create(Context context, Lifecycle lifecycle, Class<? extends Widget<V>> widgetClass, Object... params) {
        Class[] clazzs = new Class[params.length + 2];
        clazzs[0] = Context.class;
        clazzs[1] = Lifecycle.class;
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            clazzs[i + 2] = param.getClass();
        }

        Object[] ps = new Object[params.length + 2];
        ps[0] = context;
        ps[1] = lifecycle;
        for (int i = 0; i < params.length; i++) {
            ps[i + 2] = params[i];
        }
        try {
            Constructor<? extends Widget<V>> constructor = widgetClass.getConstructor(clazzs);
            return constructor.newInstance(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static <V extends View> V render(Fragment fragment, Class<? extends Widget<V>> widgetClass, Object... params) {
        return render(fragment.getActivity(), fragment, widgetClass, params);
    }

    public static <V extends View> V render(AppCompatActivity activity, Class<? extends Widget<V>> widgetClass, Object... params) {
        return render(activity, activity, widgetClass, params);
    }

    public static <V extends View> V render(Context context, LifecycleOwner lifecycleOwner, Class<? extends Widget<V>> widgetClass, Object... params) {
        return create(context, lifecycleOwner.getLifecycle(), widgetClass, params).render();
    }
}
