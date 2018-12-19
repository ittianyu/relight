package com.ittianyu.relight.widget.stateless;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ittianyu.relight.view.ActivityResultDelegation;
import com.ittianyu.relight.view.ActivityResultDelegationManager;
import com.ittianyu.relight.view.AndroidLifecycle;
import com.ittianyu.relight.widget.Widget;

public abstract class LifecycleStatelessWidget<V extends View, T extends Widget<V>> extends StatelessWidget<V, T> implements AndroidLifecycle {
    protected final Lifecycle lifecycle;
    protected boolean hasRegisterActivityResultDelegation;

    public LifecycleStatelessWidget(Context context, Lifecycle lifecycle) {
        super(context);
        this.lifecycle = lifecycle;
        lifecycle.addObserver(this);
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
            ActivityResultDelegationManager.unregister((Activity) context, this);
        }
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


}
