package com.ittianyu.relight.widget.stateless;

import android.content.Context;
import android.view.View;

import com.ittianyu.relight.widget.ContainerWidget;
import com.ittianyu.relight.widget.Widget;

public abstract class StatelessWidget<V extends View, T extends Widget<V>>
        extends Widget<V> implements ContainerWidget<V, T> {

    protected T widget;

    public StatelessWidget(Context context) {
        super(context);
    }

    protected abstract T build(Context context);

    @Override
    public V render() {
        if (widget != null) {
            return widget.render();
        }

        widget = build(context);
        if (widget == null)
            throw new IllegalStateException("can't build widget");
        V view = widget.render();
        if (view == null)
            throw new IllegalStateException("can't render view");
        initWidget(widget);
        update();
        return view;
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
