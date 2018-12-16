package com.ittianyu.relight.widget.stateful;

import android.content.Context;
import android.view.View;

import com.ittianyu.relight.widget.StatefulContainerWidget;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.BaseAndroidWidget;
import com.ittianyu.relight.widget.stateful.state.SetState;
import com.ittianyu.relight.widget.stateful.state.State;
import com.ittianyu.relight.widget.stateful.state.listener.OnUpdateListener;
import com.ittianyu.relight.widget.stateful.state.strategy.CacheStrategy;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

public abstract class StatefulWidget<V extends View, T extends Widget<V>> extends Widget<V>
    implements StatefulContainerWidget<V, T>, OnUpdateListener, SetState {
    protected State<T> state;
    protected T widget;

    public StatefulWidget(Context context) {
        super(context);
    }

    abstract protected State<T> createState(Context context);

    @Override
    public V render() {
        if (null == state) {
            state = createState(context);
            state.setOnUpdateListener(this);
            state.init();
            widget = state.build(context);
            initWidget(widget);
        }
        return widget.render();
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
    public void update() {
        updateWidget(widget);
    }

    public void updateProps(Widget widget) {
        // stateless 的实例 widget 并不是直接渲染的 widget，所以这里对里面的实际 widget 进行获取
        if (widget instanceof StatefulWidget) {
            widget = ((StatefulWidget) widget).widget;
        }

        if (widget instanceof BaseAndroidWidget) {
            ((BaseAndroidWidget) widget).updateProps(widget.render());
        } else if (widget instanceof StatefulWidget) {
            ((StatefulWidget) widget).updateProps(widget);
        } else if (widget instanceof StatelessWidget) {
            ((StatelessWidget) widget).updateProps(widget);
        }
    }
}
