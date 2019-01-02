package com.ittianyu.relight.widget.stateful.navigator;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
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
    private Action action;
    private boolean pop;
    private Integer pushAnim = android.R.anim.slide_in_left;
    private Integer popAnim = android.R.anim.slide_out_right;
    private Runnable pushTask = new Runnable() {
        @Override
        public void run() {
            action = Action.PUSH;
        }
    };
    private Runnable popTask = new Runnable() {
        @Override
        public void run() {
            action = Action.POP;
        }
    };

    public Navigator(Context context, Lifecycle lifecycle, String name, Route initRoute, Route... routes) {
        this(context, lifecycle, name, true, initRoute, routes);
    }

    public Navigator(Context context, Lifecycle lifecycle, String name, boolean finishWhenEmpty, Route initRoute, Route... routes) {
        super(context, lifecycle);
        map.put(name, this);
        this.finishWhenEmpty = finishWhenEmpty;
        bindRoute(initRoute, routes);
        stack.push(initRoute.build(context, lifecycle));
    }

    @Override
    public void initWidget(FrameWidget widget) {
        super.initWidget(widget);
        widget
            .matchParent()
            .addChild(stack.peek(), false);
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

    @Override
    public void update() {
        updateWidget();
        super.update();
    }

    private void updateWidget() {
        switch (action) {
            case PUSH: {
                Widget topWidget = stack.peek();
                widget.addChild(topWidget);
                View view = topWidget.render();
                if (pushAnim == null || pushAnim == 0) {
                    break;
                }
                Animation animation = AnimationUtils.loadAnimation(context, pushAnim);
                view.startAnimation(animation);
                break;
            }
            case POP: {
                if (pop || pushAnim == null || pushAnim == 0) {
                    Widget popWidget = stack.pop();
                    widget.removeChild(popWidget);
                    pop = false;
                    break;
                }

                pop = true;
                final Widget popWidget = stack.pop();
                final View view = popWidget.render();
                Animation animation = AnimationUtils.loadAnimation(context, popAnim);
                animation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (isDestroyed()) {
                            pop = false;
                            return;
                        }
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                widget.removeChild(popWidget);
                                pop = false;
                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                view.startAnimation(animation);
                break;
            }
        }
    }

    public void push(Widget widget) {
        push(widget, pushAnim);
    }

    public void push(Widget widget, Integer animRes) {
        this.pushAnim = animRes;
        stack.push(widget);
        setState(pushTask);
    }

    public void push(String path) {
        push(path, pushAnim);
    }

    public void push(String path, Integer animRes) {
        Route route = getRoute(path);
        push(route.build(context, lifecycle), animRes);
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
        pop(popAnim);
    }

    public void pop(Integer animRes) {
        if (stack.isEmpty()) {
            return;
        }
        this.popAnim = animRes;
        if (stack.size() == 1 && finishWhenEmpty) {
            finish();
        } else {
            setState(popTask);
        }
    }

    private void finish() {
        ContextUtils.getActivity(context).finish();
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

    private enum Action {
        PUSH,
        POP
    }
}
