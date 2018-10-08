package com.ittianyu.relight.widget.stateless;

import android.content.Context;
import android.view.View;

import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.BaseAndroidWidget;
import com.ittianyu.relight.widget.stateful.StatefulWidget;

public abstract class StatelessWidget<T extends View> implements Widget<T> {
    protected Context context;
    protected Widget<T> widget;

    public StatelessWidget(Context context) {
        this.context = context;
    }

    protected abstract Widget<T> build(Context context);

    public void update(Widget widget) {
        // stateless 的实例 widget 并不是直接渲染的 widget，所以这里对里面的实际 widget 进行获取
        if (widget instanceof StatelessWidget) {
            widget = ((StatelessWidget) widget).widget;
        }

        if (widget instanceof BaseAndroidWidget) {
            ((BaseAndroidWidget) widget).updateView(widget.render());
        } else if (widget instanceof StatefulWidget) {
            ((StatefulWidget) widget).setState(null);
        } else if (widget instanceof StatelessWidget) {
            ((StatelessWidget) widget).update(widget);
        }
    }

    public void updateProps(Widget widget) {
        // stateless 的实例 widget 并不是直接渲染的 widget，所以这里对里面的实际 widget 进行获取
        if (widget instanceof StatelessWidget) {
            widget = ((StatelessWidget) widget).widget;
        }

        if (widget instanceof BaseAndroidWidget) {
            ((BaseAndroidWidget) widget).updateProps(widget.render());
        } else if (widget instanceof StatefulWidget) {
            ((StatefulWidget) widget).updateProps(widget);
        } else if (widget instanceof StatelessWidget) {
            ((StatelessWidget) widget).updateProps(widget);
        }
    }

    @Override
    public T render() {
        if (null == widget)
            widget = build(context);
        return widget.render();
    }
}
