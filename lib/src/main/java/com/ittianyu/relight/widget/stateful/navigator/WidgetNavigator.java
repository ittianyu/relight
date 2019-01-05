package com.ittianyu.relight.widget.stateful.navigator;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import com.ittianyu.relight.utils.ContextUtils;
import com.ittianyu.relight.view.ActivityDelegationManager;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.stateful.navigator.route.Route;
import java.util.Stack;

public class WidgetNavigator extends Navigator {
    private final boolean finishWhenEmpty;
    private boolean pop;
    protected Stack<Widget> stack = new Stack<>();


    public WidgetNavigator(Context context, Lifecycle lifecycle, String name, Route initRoute, Route... routes) {
        this(context, lifecycle, name, true, initRoute, routes);
    }

    public WidgetNavigator(Context context, Lifecycle lifecycle, String name, boolean finishWhenEmpty, Route initRoute, Route... routes) {
        super(context, lifecycle, name, initRoute, routes);
        this.finishWhenEmpty = finishWhenEmpty;
        ActivityDelegationManager.register(ContextUtils.getActivity(context), this);
        stack.push(initRoute.build(context, lifecycle));
    }

    @Override
    public void initWidget(FrameWidget widget) {
        super.initWidget(widget);
        widget.addChild(stack.peek(), false);
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
                if (stack.size() == 1 && finishWhenEmpty) {
                    finish(context);
                    return;
                }
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

    @Override
    public boolean pop(Integer animRes) {
        if (stack.isEmpty()) {
            return false;
        }
        return super.pop(animRes);
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

    public static Widget getTopWidget(String name) {
        return getNavigator(name).stack.peek();
    }

    protected static WidgetNavigator getNavigator(String name) {
        return (WidgetNavigator) Navigator.getNavigator(name);
    }
}
