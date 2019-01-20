package com.ittianyu.relight.widget.stateful.navigator;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import com.ittianyu.relight.thread.Runnable1;
import com.ittianyu.relight.utils.ContextUtils;
import com.ittianyu.relight.utils.DensityUtils;
import com.ittianyu.relight.view.ActivityDelegationManager;
import com.ittianyu.relight.widget.ContainerWidget;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.BaseAndroidWidget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.stateful.navigator.route.Route;
import java.util.Stack;
import java.util.concurrent.Callable;

public class WidgetNavigator extends Navigator {
    private static final OnClickListener EMPTY_CLICK_LISTENER = new OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };
    private static final float ELEVATION = 4;

    private final boolean finishWhenEmpty;
    private boolean pop;
    protected Stack<Widget> stack = new Stack<>();
    protected Stack<Runnable1> callbacks = new Stack<>();

    public WidgetNavigator(Context context, Lifecycle lifecycle, String name, Route initRoute, Route... routes) {
        this(context, lifecycle, name, true, initRoute, routes);
    }

    public WidgetNavigator(Context context, Lifecycle lifecycle, String name, boolean finishWhenEmpty, Route initRoute, Route... routes) {
        super(context, lifecycle, name, initRoute, routes);
        this.finishWhenEmpty = finishWhenEmpty;
        ActivityDelegationManager.register(ContextUtils.getActivity(context), this);
        stack.push(initRoute.build(context, lifecycle));
        callbacks.push(null);
    }

    @Override
    public void initWidget(FrameWidget widget) {
        super.initWidget(widget);
        widget.addChild(stack.peek(), false);
    }

    @Override
    public void update() {
        updateWidget();
//        super.update();
    }

    private void updateWidget() {
        switch (action) {
            case PUSH: {
                Widget topWidget = stack.peek();
                widget.addChild(topWidget);
                View view = topWidget.render();
                BaseAndroidWidget topAndroidWidget = null;
                while (topWidget instanceof ContainerWidget) {
                    topWidget = ((ContainerWidget) topWidget).getInnerWidget();
                }
                if (topWidget instanceof BaseAndroidWidget) {
                    topAndroidWidget = (BaseAndroidWidget) topWidget;
                }
                if (topAndroidWidget != null && topAndroidWidget.onClick() == null) {
                    view.setOnClickListener(EMPTY_CLICK_LISTENER);
                }
                if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                    view.setElevation(DensityUtils.dip2px(context, ELEVATION * (size() - 1)));
                }
                if (pushAnim == null || pushAnim == 0) {
                    break;
                }
                Animation animation = AnimationUtils.loadAnimation(context, pushAnim);
                view.startAnimation(animation);
                break;
            }
            case POP: {
                if (stack.size() == 1 && finishWhenEmpty) {
                    finish(context);
                    return;
                }
                if (pop || popAnim == null || popAnim == 0) {
                    Widget popWidget = stack.pop();
                    Runnable1 callback = callbacks.pop();
                    widget.removeChild(popWidget);
                    if (callback != null) {
                        callback.run();
                    }
                    this.pop = false;
                    break;
                }

                pop = true;
                final Widget popWidget = stack.pop();
                final Runnable1 callback = callbacks.pop();
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
                                if (callback != null) {
                                    callback.run();
                                }
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
        push(widget, DEFAULT_PUSH_ANIM);
    }

    public void push(Widget widget, Integer animRes) {
        push(widget, animRes, null);
    }


    @Override
    public void push(Route route, Integer animRes) {
        push(route, animRes, null);
    }

    public <T> void push(Route route, Runnable1<T> callback) {
        push(route, DEFAULT_PUSH_ANIM, callback);
    }

    public <T> void push(Route route, Integer animRes, Runnable1<T> callback) {
        push(route.build(context, lifecycle), animRes, callback);
    }

    public <T> void push(String path, Runnable1<T> callback) {
        push(path, DEFAULT_PUSH_ANIM, callback);
    }

    public <T> void push(Widget widget, Integer animRes, Runnable1<T> callback) {
        this.pushAnim = animRes;
        callbacks.push(callback);
        stack.push(widget);
        setState(pushTask);
    }

    public <T> void push(String path, Integer animRes, Runnable1<T> callback) {
        push(getRoute(path), animRes, callback);
    }

    @Override
    public boolean pop(Integer animRes) {
        return pop(animRes, null);
    }

    public <T> boolean pop(T data) {
        return pop(DEFAULT_POP_ANIM, data);
    }

    public <T> boolean pop(Integer animRes, T data) {
        if (stack.isEmpty()) {
            return false;
        }
        Runnable1 callback = callbacks.peek();
        if (callback != null) {
            callback.setData(data);
        }
        return super.pop(animRes);
    }

    @Override
    public int size() {
        return stack.size();
    }

    public void replace(Widget widget) {
        Widget popWidget = stack.pop();
        callbacks.pop();
        this.widget.removeChild(popWidget);
        callbacks.push(null);
        push(widget, null);
    }

    public void replace(String path) {
        Route route = getRoute(path);
        replace(route.build(context, lifecycle));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return pop();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void finish(Context context) {
        ContextUtils.getActivity(context).finish();
    }

    public static void push(String name, Widget widget) {
        getNavigator(name).push(widget);
    }

    public static <T> void push(String name, Widget widget, Integer animRes, Runnable1<T> callback) {
        getNavigator(name).push(widget, animRes, callback);
    }

    public static <T> void push(String name, String path, Runnable1<T> callback) {
        getNavigator(name).push(path, callback);
    }

    public static <T> void push(String name, String path, Integer animRes, Runnable1<T> callback) {
        getNavigator(name).push(path, animRes, callback);
    }

    public static <T> void push(String name, Route route, Runnable1<T> callback) {
        getNavigator(name).push(route, callback);
    }

    public static <T> void push(String name, Route route, Integer animRes, Runnable1<T> callback) {
        getNavigator(name).push(route, animRes, callback);
    }

    public static <T> boolean pop(String name, Integer animRes, T data) {
        return getNavigator(name).pop(animRes, data);
    }

    public static <T> boolean pop(String name, T data) {
        return getNavigator(name).pop(data);
    }

    public static void replace(String name, Widget widget) {
        getNavigator(name).replace(widget);
    }

    public static void replace(String name, String path) {
        getNavigator(name).replace(path);
    }

    public static Widget getTopWidget(String name) {
        return getNavigator(name).stack.peek();
    }

    protected static WidgetNavigator getNavigator(String name) {
        return (WidgetNavigator) Navigator.getNavigator(name);
    }
}
