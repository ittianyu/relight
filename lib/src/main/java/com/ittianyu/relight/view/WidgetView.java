package com.ittianyu.relight.view;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.ittianyu.relight.R;
import com.ittianyu.relight.widget.Widget;

import java.lang.reflect.Constructor;

public class WidgetView extends FrameLayout {
    private String widget;

    public WidgetView(Context context) {
        super(context);
        init(null, 0);
    }

    public WidgetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public WidgetView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.WidgetView, defStyle, 0);
        widget = a.getString(
                R.styleable.WidgetView_widget);
        a.recycle();

        Widget widget = createWidget();
        addView(widget.render());
    }

    private Widget createWidget() {
        try {
            Class<?> clazz = Class.forName(widget);
            Constructor<?> constructor = clazz.getConstructor(Context.class, Lifecycle.class);
            return (Widget) constructor.newInstance(getContext(), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
