package com.ittianyu.relight.widget.native_;

import android.content.Context;
import android.view.View;

import com.ittianyu.relight.view.AndroidRender;
import com.ittianyu.relight.widget.Widget;

public abstract class AndroidWidget<V extends View> extends Widget<V> implements AndroidRender<V> {
    protected V view;
    private boolean init;

    public AndroidWidget(Context context) {
        super(context);
        view = createView(context);
        if (view == null)
            throw new IllegalStateException("can't render view");
    }

    @Override
    public V render() {
        if (!init) {
            init = true;
            init();
        }
        return view;
    }

    private void init() {
        initView(view);
        bindEvent(view);
        initData();
        updateView(view);
    }
}
