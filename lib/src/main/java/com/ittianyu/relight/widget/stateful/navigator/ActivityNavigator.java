package com.ittianyu.relight.widget.stateful.navigator;

import android.app.Activity;
import android.app.Instrumentation;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

import com.ittianyu.relight.thread.ThreadPool;
import com.ittianyu.relight.utils.ContextUtils;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.stateful.navigator.route.Route;

import java.lang.ref.WeakReference;
import java.util.Stack;

public class ActivityNavigator extends Navigator {
    protected Stack<Route> stack = new Stack<>();
    protected Stack<WeakReference<Activity>> activities = new Stack<>();
    private Runnable backTask = new Runnable() {
        @Override
        public void run() {
            Instrumentation inst = new Instrumentation();
            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
        }
    };

    public ActivityNavigator(Context context, Lifecycle lifecycle, String name, Route initRoute, Route... routes) {
        super(context, lifecycle, name, initRoute, routes);
        stack.push(initRoute);
        activities.push(new WeakReference<>(ContextUtils.getActivity(context)));
    }

    @Override
    public void initWidget(FrameWidget widget) {
        super.initWidget(widget);
        widget.addChild(stack.peek().build(context, lifecycle), false);
    }

    @Override
    public void push(String path) {
        push(getRoute(path));
    }

    @Override
    public void push(String path, Integer animRes) {
        push(getRoute(path), animRes);
    }

    public void push(Route route) {
        push(route, pushAnim);
    }

    public void push(Route route, Integer animRes) {
        this.pushAnim = animRes;
        stack.push(route);
        setState(pushTask);
    }

    @Override
    public boolean pop(Integer animRes) {
        if (stack.isEmpty()) {
            return false;
        }
        return super.pop(animRes);
    }

    @Override
    public void update() {
        updateWidget();
        super.update();
    }

    private void updateWidget() {
        switch (action) {
            case PUSH: {
                if (pushAnim != null && pushAnim != 0) {
                    ContextUtils.getActivity(context).overridePendingTransition(pushAnim, popAnim);
                }
                Intent intent = new Intent(context, DelagationActivity.class);
                intent.putExtra(DelagationActivity.EXTRA_ROUTE, stack.peek());
                intent.putExtra(DelagationActivity.EXTRA_NAME, name);
                startActivity(intent);
                break;
            }
            case POP: {
                Activity activity = activities.peek().get();
                if (popAnim != null && popAnim != 0 && activity != null) {
                    activity.overridePendingTransition(pushAnim, popAnim);
                }
                stack.pop();
                pressBack();
                break;
            }
        }
    }

    private void pressBack() {
        ThreadPool.get().submit(backTask);
    }

    private void pushActivity(Activity activity) {
        activities.push(new WeakReference<>(activity));
    }

    private void popActivity() {
        activities.pop();
    }

    static void pushActivity(String name, Activity activity) {
        getNavigator(name).pushActivity(activity);
    }

    static void popActivity(String name) {
        getNavigator(name).popActivity();
    }

    protected static ActivityNavigator getNavigator(String name) {
        return (ActivityNavigator) Navigator.getNavigator(name);
    }
}
