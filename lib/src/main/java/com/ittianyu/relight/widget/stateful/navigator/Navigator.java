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
import javax.security.auth.callback.Callback;

public abstract class Navigator extends StatefulWidget<FrameLayout, FrameWidget> {
    protected static final Map<String, Navigator> map = new WeakHashMap<>();
    protected static final int DEFAULT_PUSH_ANIM = android.R.anim.slide_in_left;
    protected static final int DEFAULT_POP_ANIM = android.R.anim.slide_out_right;

    protected Map<String, Route> routeMap = new HashMap<>();
    protected String name;
    protected Action action;
    protected Integer pushAnim = DEFAULT_PUSH_ANIM;
    protected Integer popAnim = DEFAULT_POP_ANIM;
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

    protected void addRoute(Route route) {
        routeMap.put(route.path(), route);
    }

    @Override
    protected State<FrameWidget> createState(Context context) {
        return StateUtils.create(new FrameWidget(context, lifecycle));
    }

    public void push(String path) {
        push(path, DEFAULT_PUSH_ANIM);
    }

    public void push(String path, Integer animRes) {
        Route route = getRoute(path);
        push(route, animRes);
    }

    public void push(Route route) {
        push(route, DEFAULT_PUSH_ANIM);
    }

    public abstract void push(Route route, Integer animRes);

    @NonNull
    protected Route getRoute(String path) {
        Route route = routeMap.get(path);
        if (null == route) {
            throw new IllegalStateException("can't find the route which path is :" + path);
        }
        return route;
    }

    public boolean pop() {
        return pop(DEFAULT_POP_ANIM);
    }

    public boolean pop(Integer animRes) {
        this.popAnim = animRes;
        setState(popTask);
        return true;
    }

    public abstract int size();

    public static Navigator get(String name) {
        return map.get(name);
    }

    public static void push(String name, String path) {
        getNavigator(name).push(path);
    }

    public static void push(String name, String path, Integer animRes) {
        getNavigator(name).push(path, animRes);
    }

    public static void push(String name, Route route, Integer animRes) {
        getNavigator(name).push(route, animRes);
    }

    public static void push(String name, Route route) {
        getNavigator(name).push(route);
    }

    @NonNull
    protected static Navigator getNavigator(String name) {
        Navigator navigator = get(name);
        if (navigator == null) {
            throw new IllegalStateException("can't find the navigator named " + name);
        }
        return navigator;
    }

    public static boolean pop(String name) {
        return getNavigator(name).pop();
    }

    public static boolean pop(String name, Integer animRes) {
        return getNavigator(name).pop(animRes);
    }

    public static int size(String name) {
        return getNavigator(name).size();
    }

    protected enum Action {
        PUSH,
        POP
    }
}
