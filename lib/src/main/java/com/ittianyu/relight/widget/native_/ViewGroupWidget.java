package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.ittianyu.relight.view.AndroidRender;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class ViewGroupWidget<V extends ViewGroup, T extends ViewGroupWidget> extends BaseAndroidWidget<V, T> {
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
    public void updateView(V view) {
        super.updateView(view);
        for (Widget widget : children) {
            if (widget instanceof AndroidRender) {
                ((AndroidRender) widget).updateView(widget.render());
            } else if (widget instanceof StatefulWidget) {
                ((StatefulWidget) widget).setState(null);
            } else if (widget instanceof StatelessWidget) {
                ((StatelessWidget) widget).update(widget);
            }
        }
    }

    @Override
    public void updateProps(V view) {
        updateChildrenProps();
        super.updateProps(view);
    }

    /**
     * call when add view which was removed
     */
    private void updateChildrenProps() {
        for (Widget widget : children) {
            if (widget instanceof BaseAndroidWidget) {
                ((BaseAndroidWidget) widget).updateProps(widget.render());
            } else if (widget instanceof StatelessWidget) {
                ((StatelessWidget) widget).updateProps(widget);
            } else if (widget instanceof StatefulWidget) {
                ((StatefulWidget) widget).updateProps(widget);
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
