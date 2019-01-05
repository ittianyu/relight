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

public abstract class Widget<V extends View>
        implements ActivityDelegation, WidgetUpdater, AndroidLifecycle {
    protected final Context context;
    protected final Lifecycle lifecycle;
    protected boolean hasRegisterActivityResultDelegation;

    public Widget(Context context, Lifecycle lifecycle) {
        this.context = context;
        this.lifecycle = lifecycle;
    }

    public abstract V render();

    protected void startActivity(Intent intent) {
        startActivity(intent, null);
    }

    protected void startActivity(Intent intent, Bundle options) {
        context.startActivity(intent, options);
    }

    protected void startActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode, null);
    }

    protected void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        startActivityForResult(intent, requestCode, options, this);
    }

    protected void startActivityForResult(Intent intent, int requestCode, Bundle options, ActivityDelegation delegation) {
        hasRegisterActivityResultDelegation = true;
        Activity activity = ContextUtils.getActivity(context);
        if (null == activity) {
            throw new IllegalStateException("can't call startActivityForResult, the widget context is not Activity context!");
        }
        ActivityDelegationManager.register(activity, delegation);
        activity.startActivityForResult(intent, requestCode, options);
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
    public void initWithParams(String params) {
        WidgetInflateUtils.initWidgetWithParams(context, this, params);
    }

    public boolean isDestroyed() {
        if (lifecycle == null) {
            return false;
        }
        return lifecycle.getCurrentState() == State.DESTROYED;
    }
}
