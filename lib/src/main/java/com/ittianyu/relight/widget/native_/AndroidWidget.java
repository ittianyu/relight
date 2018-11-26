package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;

import com.ittianyu.relight.view.AndroidRender;
import com.ittianyu.relight.widget.LifecycleWidget;

public abstract class AndroidWidget<T extends View> implements AndroidRender<T>, LifecycleWidget<T> {
    protected T view;
    protected Context context;
    protected final Lifecycle lifecycle;
    private boolean addObserver = false;

    public AndroidWidget(Context context, Lifecycle lifecycle) {
        this.context = context;
        this.lifecycle = lifecycle;
        view = createView(context);
        if (view == null)
            throw new IllegalStateException("can't render view");
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onStart() {
        initView(view);
        bindEvent(view);
        initData();
        updateView(view);
    }

    public void bindEvent(T view) {
    }

    public void initData() {
    }

    public void updateView(T view) {
    }

    @Override
    public void onDestroy() {
    }

    public T render() {
        if (!addObserver) {
            addObserver = true;
            lifecycle.addObserver(this);
        }
        return view;
    }
}
