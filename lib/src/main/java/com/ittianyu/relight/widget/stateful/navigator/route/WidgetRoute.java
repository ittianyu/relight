package com.ittianyu.relight.widget.stateful.navigator.route;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import com.ittianyu.relight.utils.WidgetUtils;
import com.ittianyu.relight.widget.Widget;
import java.util.UUID;

public class WidgetRoute<V extends View> implements Route<V> {
    private Class<? extends Widget<V>> widgetClass;
    private Object[] params;
    private String path;

    public WidgetRoute(Class<? extends Widget<V>> widgetClass, Object... params) {
        this(null, widgetClass, params);
    }

    public WidgetRoute(String path, Class<? extends Widget<V>> widgetClass, Object... params) {
        this.path = path;
        this.widgetClass = widgetClass;
        this.params = params;
    }

    @Override
    public Widget build(Context context, Lifecycle lifecycle) {
        return WidgetUtils.create(context, lifecycle, widgetClass, params);
    }

    @Override
    public String path() {
        if (path == null) {
            path = UUID.randomUUID().toString();
        }
        return path;
    }
}
