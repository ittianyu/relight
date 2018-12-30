package com.ittianyu.relight.widget.stateful.navigator;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import com.ittianyu.relight.utils.ContextUtils;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateful.navigator.route.Route;
import com.ittianyu.relight.widget.stateful.state.State;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;

public class Navigator extends StatefulWidget<FrameLayout, FrameWidget> {
    private static final Map<String, Navigator> map = new WeakHashMap<>();

    private final boolean finishWhenEmpty;
    private Stack<Widget> stack = new Stack<>();
    private Map<String, Route> routeMap = new HashMap<>();

    public Navigator(Context context, Lifecycle lifecycle, String name, Route initRoute, Route... routes) {
        this(context, lifecycle, name, true, initRoute, routes);
    }

    public Navigator(Context context, Lifecycle lifecycle, String name, boolean finishWhenEmpty, Route initRoute, Route... routes) {
        super(context, lifecycle);
        map.put(name, this);
        stack.push(initRoute.build(context, lifecycle));
        this.finishWhenEmpty = finishWhenEmpty;
        bindRoute(initRoute);
        if (routes == null) {
            return;
        }
        for (Route route : routes) {
            bindRoute(route);
        }
    }

    private void bindRoute(Route route) {
        routeMap.put(route.path(), route);
    }

    @Override
    protected State<FrameWidget> createState(Context context) {
        return StateUtils.create(new FrameWidget(context, lifecycle));
    }

    @Override
    public void update() {
        updateWidget();
        super.update();
    }

    private void updateWidget() {
        widget.removeAllChildren();
        widget.addChild(stack.peek());
    }

    public void push(Widget widget) {
        stack.push(widget);
        setState(null);
    }

    public void push(String path) {
        Route route = getRoute(path);
        push(route.build(context, lifecycle));
    }

    @NonNull
    private Route getRoute(String path) {
        Route route = routeMap.get(path);
        if (null == route) {
            throw new IllegalStateException("can't find the route which path is :" + path);
        }
        return route;
    }

    public void pop() {
        if (stack.isEmpty()) {
            return;
        }
        stack.pop();
        if (stack.isEmpty() && finishWhenEmpty) {
            ContextUtils.getActivity(context).finish();
        } else {
            setState(null);
        }
    }

    public static Navigator get(String name) {
        return map.get(name);
    }

    public static void push(String name, Widget widget) {
        Navigator navigator = getNavigator(name);
        navigator.push(widget);
    }

    public static void push(String name, String path) {
        Navigator navigator = getNavigator(name);
        navigator.push(path);
    }

    @NonNull
    private static Navigator getNavigator(String name) {
        Navigator navigator = get(name);
        if (navigator == null) {
            throw new IllegalStateException("can't find the navigator named " + name);
        }
        return navigator;
    }

    public static void pop(String name) {
        Navigator navigator = getNavigator(name);
        navigator.pop();
    }
}
