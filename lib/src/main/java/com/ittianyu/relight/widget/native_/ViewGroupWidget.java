package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.ittianyu.relight.widget.ContainerWidget;
import com.ittianyu.relight.widget.Widget;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class ViewGroupWidget<V extends ViewGroup, T extends ViewGroupWidget>
        extends BaseAndroidWidget<V, T> {
    protected List<Widget> children = new LinkedList<>();
    private Widget[] tmpChildren;

    public ViewGroupWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle);
        tmpChildren = children;
    }

    @Override
    public V render() {
        // render children first
        List<View> views = null;
        if (tmpChildren != null) {
            views = new ArrayList<>(tmpChildren.length);
            for (Widget child: tmpChildren) {
                if (child == null) {
                    continue;
                }
                views.add(child.render());
                children.add(child);
            }
            tmpChildren = null;
        }
        // render self
        V view = super.render();
        // add children to ViewGroup
        if (views != null) {
            for (View v : views) {
                view.addView(v);
            }
        }
        return view;
    }

    @Override
    protected void initProps() {
    }

    @Override
    public void update() {
        for (Widget widget : children) {
            widget.update();
        }
    }

    /**
     * called when add child
     */
    public void updateChildrenProps() {
        for (Widget child : children) {
            Queue<Widget> widgets = new LinkedList<>();
            widgets.add(child);
            while (!widgets.isEmpty()) {
                Widget widget = widgets.poll();
                if (widget instanceof ViewGroupWidget) {
                    ((ViewGroupWidget) widget).updateChildrenProps();
                    ((ViewGroupWidget) widget).updateProps(widget.render());
                } else if (widget instanceof BaseAndroidWidget) {
                    ((BaseAndroidWidget) widget).updateProps(widget.render());
                } else if (widget instanceof ContainerWidget) {
                    Widget innerWidget = ((ContainerWidget) widget).getInnerWidget();
                    widgets.add(innerWidget);
                }
            }
        }
    }

    public T addChild(Widget widget) {
        return addChild(widget, true);
    }

    public T addChild(Widget widget, boolean updateProps) {
        view.addView(widget.render());
        children.add(widget);
        if (updateProps) {
            updateChildrenProps();
            updateProps(view);
        }
        return self();
    }

    public T addChildren(Widget... children) {
        return addChildren(true, children);
    }

    public T addChildren(boolean updateProps, Widget... children) {
        for (Widget widget : children) {
            addChild(widget, false);
        }
        if (updateProps) {
            updateChildrenProps();
            updateProps(view);
        }
        return self();
    }

    public T removeChild(Widget widget) {
        children.remove(weight);
        view.removeView(widget.render());
        return self();
    }

    public T removeAllChildren() {
        view.removeAllViews();
        children.clear();
        return self();
    }

}
