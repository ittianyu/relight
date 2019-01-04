package com.ittianyu.relight.widget.stateful.navigator;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateful.navigator.route.Route;
import com.ittianyu.relight.widget.stateful.state.State;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class Navigator extends StatefulWidget<FrameLayout, FrameWidget> {
    protected static final Map<String, Navigator> map = new WeakHashMap<>();

    protected Map<String, Route> routeMap = new HashMap<>();
    protected String name;
    protected Action action;
    protected Integer pushAnim = android.R.anim.slide_in_left;
    protected Integer popAnim = android.R.anim.slide_out_right;
    protected Runnable pushTask = new Runnable() {
        @Override
        public void run() {
            action = Action.PUSH;
        }
    };
    protected Runnable popTask = new Runnable() {
        @Override
        public void run() {
            action = Action.POP;
        }
    };

    public Navigator(Context context, Lifecycle lifecycle, String name, Route initRoute, Route... routes) {
        super(context, lifecycle);
        this.name = name;
        map.put(name, this);
        bindRoute(initRoute, routes);
    }

    @Override
    public void initWidget(FrameWidget widget) {
        super.initWidget(widget);
        widget.matchParent();
    }

    private void bindRoute(Route initRoute, Route[] routes) {
        addRoute(initRoute);
        if (routes == null) {
            return;
        }
        for (Route route : routes) {
            addRoute(route);
        }
    }

    private void addRoute(Route route) {
        routeMap.put(route.path(), route);
    }

    @Override
    protected State<FrameWidget> createState(Context context) {
        return StateUtils.create(new FrameWidget(context, lifecycle));
    }

    public abstract void push(String path);

    public abstract void push(String path, Integer animRes);

    @NonNull
    protected Route getRoute(String path) {
        Route route = routeMap.get(path);
        if (null == route) {
            throw new IllegalStateException("can't find the route which path is :" + path);
        }
        return route;
    }

    public void pop() {
        pop(popAnim);
    }

    public void pop(Integer animRes) {
        this.popAnim = animRes;
        setState(popTask);
    }

    public static Navigator get(String name) {
        return map.get(name);
    }

    public static void push(String name, String path) {
        getNavigator(name).push(path);
    }

    public static void push(String name, String path, Integer animRes) {
        getNavigator(name).push(path, animRes);
    }

    @NonNull
    protected static Navigator getNavigator(String name) {
        Navigator navigator = get(name);
        if (navigator == null) {
            throw new IllegalStateException("can't find the navigator named " + name);
        }
        return navigator;
    }

    public static void pop(String name) {
        getNavigator(name).pop();
    }

    public static void pop(String name, Integer animRes) {
        getNavigator(name).pop(animRes);
    }

    protected enum Action {
        PUSH,
        POP
    }
}
