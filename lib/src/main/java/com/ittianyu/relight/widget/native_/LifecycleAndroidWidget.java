package com.ittianyu.relight.widget.native_;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ittianyu.relight.view.ActivityResultDelegation;
import com.ittianyu.relight.view.ActivityResultDelegationManager;
import com.ittianyu.relight.view.AndroidLifecycle;
import com.ittianyu.relight.view.AndroidRender;

public abstract class LifecycleAndroidWidget<V extends View> extends AndroidWidget<V>
        implements AndroidRender<V>, AndroidLifecycle {
    protected final Lifecycle lifecycle;
    private boolean addObserver;
    protected boolean hasRegisterActivityResultDelegation;

    public LifecycleAndroidWidget(Context context, Lifecycle lifecycle) {
        super(context);
        this.lifecycle = lifecycle;
    }

    @Override
    public V render() {
        V result = super.render();
        if (!addObserver) {
            addObserver = true;
            if (null != lifecycle) {
                lifecycle.addObserver(this);
            }
        }
        return result;
    }

    @Override
    protected void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
        hasRegisterActivityResultDelegation = true;
    }

    @Override
    protected void startActivityForResult(Intent intent, int requestCode, Bundle options,
                                          ActivityResultDelegation delegation) {
        super.startActivityForResult(intent, requestCode, options, delegation);
        hasRegisterActivityResultDelegation = true;
    }

    @Override
    public void onDestroy() {
        if (hasRegisterActivityResultDelegation) {
            ActivityResultDelegationManager.unregister((Activity) context, this);
        }
    }
}
