package com.ittianyu.relight.utils;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory2;
import android.view.View;

import com.ittianyu.relight.view.WidgetView;
import com.ittianyu.relight.widget.Widget;

public class WidgetInflaterFactory implements Factory2 {

    private static final String[] sClassPrefixList = new String[]{"android.widget.",
        "android.webkit.", "android.app."};

    public static LayoutInflater getLayoutInflater(AppCompatActivity activity) {
        LayoutInflater inflater = LayoutInflater.from(activity).cloneInContext(activity);
        LayoutInflaterCompat.setFactory2(inflater, new WidgetInflaterFactory());
        return inflater;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if (!WidgetView.class.getName().equals(name)) {
            try {
                return createView(name, context, attrs);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        AppCompatActivity activity = ContextUtils.getAppCompatActivity(context);
        if (null == activity) {
            throw new IllegalStateException("please use AppCompatActivity context to inflate");
        }
        // create widget
        String widgetName = null;
        String params = null;
        int id = 0;
        int count = attrs.getAttributeCount();
        for (int i = 0; i < count; i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            if ("widget".equals(attrName)) {
                widgetName = attrValue;
            } else if ("params".equals(attrName)) {
                params = attrValue;
            } else if ("id".equals(attrName)) {
                id = Integer.parseInt(attrValue.substring(1));
            }
        }
        Widget widget = WidgetInflateUtils.createWidget(widgetName, activity, activity.getLifecycle());
        WidgetInflateUtils.initWidgetWithParams(context, widget, params);
        View view = widget.render();
        view.setTag(widget);
        view.setId(id);
        return view;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }

    protected View createView(String name, Context context, AttributeSet attrs)
        throws Exception {
        String[] prefixList = sClassPrefixList;
        int count = prefixList.length;

        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < count; ++i) {
            String prefix = prefixList[i];

            try {
                View view = inflater.createView(name, prefix, attrs);
                if (view != null) {
                    return view;
                }
            } catch (Exception var8) {
                ;
            }
        }

        return inflater.createView(name, "android.view.", attrs);
    }



}
