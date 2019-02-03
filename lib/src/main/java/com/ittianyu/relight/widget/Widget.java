package com.ittianyu.relight.widget;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Lifecycle.State;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import com.ittianyu.relight.utils.ContextUtils;
import com.ittianyu.relight.utils.WidgetInflateUtils;
import com.ittianyu.relight.view.ActivityDelegation;
import com.ittianyu.relight.view.ActivityDelegationManager;
import com.ittianyu.relight.view.AndroidLifecycle;

public abstract class Widget<V extends View> implements ActivityDelegation, WidgetUpdater, AndroidLifecycle, Render<V> {
    protected final Context context;
    protected final Lifecycle lifecycle;
    protected boolean hasRegisterActivityResultDelegation;
    private WidgetUpdater widgetUpdater;

    public Widget(Context context, Lifecycle lifecycle) {
        this.context = context;
        this.lifecycle = lifecycle;
    }

    protected Widget<V> startActivity(Intent intent) {
        return startActivity(intent, null);
    }

    protected Widget<V> startActivity(Intent intent, Bundle options) {
        context.startActivity(intent, options);
        return this;
    }

    protected Widget<V> startActivityForResult(Intent intent, int requestCode) {
        return startActivityForResult(intent, requestCode, null);
    }

    protected Widget<V> startActivityForResult(Intent intent, int requestCode, Bundle options) {
        return startActivityForResult(intent, requestCode, options, this);
    }

    protected Widget<V> startActivityForResult(Intent intent, int requestCode, Bundle options, ActivityDelegation delegation) {
        hasRegisterActivityResultDelegation = true;
        Activity activity = ContextUtils.getActivity(context);
        if (null == activity) {
            throw new IllegalStateException("can't call startActivityForResult, the widget context is not Activity context!");
        }
        ActivityDelegationManager.register(activity, delegation);
        activity.startActivityForResult(intent, requestCode, options);
        return this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
        if (hasRegisterActivityResultDelegation) {
            ActivityDelegationManager.unregister((Activity) context, this);
        }
    }

    /**
     * use json to call the widget method
     * @param params
     */
    public Widget<V> initWithParams(String params) {
        WidgetInflateUtils.initWidgetWithParams(context, this, params);
        return this;
    }

    public boolean isDestroyed() {
        if (lifecycle == null) {
            return false;
        }
        return lifecycle.getCurrentState() == State.DESTROYED;
    }

    /**
     * listen widget update event
     * @param widgetUpdater
     * @return
     */
    public Widget<V> onUpdate(WidgetUpdater widgetUpdater) {
        this.widgetUpdater = widgetUpdater;
        return this;
    }

    @Override
    public void update() {
        if (widgetUpdater != null) {
            widgetUpdater.update();
        }
    }
}
