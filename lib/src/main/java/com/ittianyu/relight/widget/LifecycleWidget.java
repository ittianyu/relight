package com.ittianyu.relight.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ittianyu.relight.view.ActivityResultDelegation;
import com.ittianyu.relight.view.ActivityResultDelegationManager;
import com.ittianyu.relight.view.AndroidLifecycle;

public abstract class LifecycleWidget<T extends View> extends Widget<T> implements AndroidLifecycle {
    protected boolean hasRegisterActivityResultDelegation;

    public LifecycleWidget(Context context) {
        super(context);
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
