package com.ittianyu.relight.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ittianyu.relight.lib.R;
import com.ittianyu.relight.utils.WidgetInflateUtils;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.ViewGroupWidget;


public class WidgetView extends FrameLayout {

    private Widget widget;
    private boolean init;

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
        String widgetName = a.getString(R.styleable.WidgetView_widget);
        String params = a.getString(R.styleable.WidgetView_params);
        a.recycle();

        widget = WidgetInflateUtils.createWidget(widgetName, getContext(), null);
        addView(widget.render());
        init = true;
        if (TextUtils.isEmpty(params)) {
            return;
        }
        WidgetInflateUtils.initWidgetWithParams(getContext(), widget, params);
        WidgetInflateUtils.updateProps(widget);
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (!init) {
            super.addView(child, index, params);
            return;
        }
        if (!(widget instanceof ViewGroupWidget)) {
            return;
        }

//        if (!(child instanceof WidgetView)) {
        ViewGroup viewGroup = (ViewGroup) widget.render();
        viewGroup.addView(child, index, params);
//            return;
//        }
        if (child instanceof WidgetView) {
            Widget w = ((WidgetView) child).getWidget();
            WidgetInflateUtils.updateProps(w);
        }

//        Widget w = ((WidgetView) child).getWidget();
//        View v = w.render();
//        ViewParent parent = v.getParent();
//        if (parent != null) {
//            ((ViewGroup) parent).removeView(v);
//        }
//        ((ViewGroupWidget) widget).addChild(w);
//        widget.render().invalidate();
//        this.invalidate();
    }

    public Widget getWidget() {
        return widget;
    }
}
