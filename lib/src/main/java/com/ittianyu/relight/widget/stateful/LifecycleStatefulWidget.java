package com.ittianyu.relight.widget.stateful;

import android.content.Context;
import android.view.View;

import com.ittianyu.relight.view.AndroidLifecycle;

import android.arch.lifecycle.Lifecycle;

public abstract class LifecycleStatefulWidget<T extends View> extends StatefulWidget<T> implements AndroidLifecycle {
    protected final Lifecycle lifecycle;

    public LifecycleStatefulWidget(Context context, Lifecycle lifecycle) {
        super(context);
        this.lifecycle = lifecycle;
        lifecycle.addObserver(this);
    }

    @Override
    public void onDestroy() {
        state.dispose();
    }
}
