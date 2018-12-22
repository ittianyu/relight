package com.ittianyu.relight.widget.stateful;

import android.content.Context;
import android.view.View;

import com.ittianyu.relight.widget.ContainerWidget;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.stateful.state.SetState;
import com.ittianyu.relight.widget.stateful.state.State;
import com.ittianyu.relight.widget.stateful.state.strategy.CacheStrategy;

public abstract class StatefulWidget<V extends View, T extends Widget<V>> extends Widget<V>
        implements ContainerWidget<V, T>, SetState {
    protected State<T> state;
    protected T widget;

    public StatefulWidget(Context context) {
        super(context);
    }

    abstract protected State<T> createState(Context context);

    @Override
    public V render() {
        if (state != null) {
            return widget.render();
        }

        state = createState(context);
        if (state == null)
            throw new IllegalStateException("can't create state");
        state.init();
        widget = state.build(context);
        if (widget == null)
            throw new IllegalStateException("can't build widget");
        state.setOnUpdateListener(this);
        V view = widget.render();
        if (view == null)
            throw new IllegalStateException("can't render view");
        initWidget(widget);
        update();
        return view;
    }

    @Override
    public void setState(Runnable func) {
        state.setState(func);
    }

    @Override
    public void setStateAsync(Runnable func) {
        state.setStateAsync(func);
    }

    @Override
    public void setStateAsyncWithCache(Runnable cacheFunc, Runnable func) {
        state.setStateAsyncWithCache(cacheFunc, func);
    }

    @Override
    public void setStateAsyncWithCache(CacheStrategy cacheStrategy, Runnable cacheFunc,
                                       Runnable func) {
        state.setStateAsyncWithCache(cacheStrategy, cacheFunc, func);
    }

    @Override
    public T getInnerWidget() {
        return widget;
    }

    @Override
    public void update() {
        widget.update();
    }
}
