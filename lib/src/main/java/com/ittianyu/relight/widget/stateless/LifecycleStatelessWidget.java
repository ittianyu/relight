package com.ittianyu.relight.widget.stateless;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;

import com.ittianyu.relight.view.AndroidLifecycle;

public abstract class LifecycleStatelessWidget<T extends View> extends StatelessWidget<T> implements AndroidLifecycle {
    protected final Lifecycle lifecycle;

    public LifecycleStatelessWidget(Context context, Lifecycle lifecycle) {
        super(context);
        this.lifecycle = lifecycle;
        lifecycle.addObserver(this);
    }

}
