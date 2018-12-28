package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;

import com.ittianyu.relight.view.AndroidRender;
import com.ittianyu.relight.widget.Widget;

public abstract class AndroidWidget<V extends View> extends Widget<V>
        implements AndroidRender<V> {
    protected V view;
    private boolean init;

    public AndroidWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
        view = createView(context);
        if (view == null)
            throw new IllegalStateException("can't render view");
    }

    @Override
    public V render() {
        if (!init) {
            init = true;
            init();
            if (null != lifecycle) {
                lifecycle.addObserver(this);
            }
        }
        return view;
    }

    private void init() {
        initView(view);
        initEvent(view);
        initData();
    }

    @Override
    public void update() {
    }

    @Override
    public void initView(V view) {
    }

    @Override
    public void initEvent(V view) {
    }

    @Override
    public void initData() {
    }
}
