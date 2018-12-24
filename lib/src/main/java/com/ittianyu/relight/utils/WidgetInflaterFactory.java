package com.ittianyu.relight.utils;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import com.ittianyu.relight.lib.R;
import com.ittianyu.relight.view.WidgetView;
import com.ittianyu.relight.widget.Widget;

public class WidgetInflaterFactory implements Factory2 {

    private static final String[] sClassPrefixList = new String[]{"android.widget.",
        "android.webkit.", "android.app."};

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
        String widgetName = attrs.getAttributeValue(R.styleable.WidgetView_widget);
        String params = attrs.getAttributeValue(R.styleable.WidgetView_params);

        Widget widget = WidgetInflateUtils.createWidget(widgetName, activity, activity.getLifecycle());
        WidgetInflateUtils.initWidgetWithParams(context, widget, params);
        View view = widget.render();
        view.setTag(widget);
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
