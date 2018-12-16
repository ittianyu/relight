package com.ittianyu.relight.widget.stateful;

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

public abstract class LifecycleStatefulWidget<V extends View, T extends Widget<V>>
        extends StatefulWidget<V, T> implements AndroidLifecycle {
    protected boolean hasRegisterActivityResultDelegation;
    protected final Lifecycle lifecycle;

    public LifecycleStatefulWidget(Context context, Lifecycle lifecycle) {
        super(context);
        this.lifecycle = lifecycle;
        lifecycle.addObserver(this);
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
        state.dispose();
    }
}
