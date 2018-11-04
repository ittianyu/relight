package com.ittianyu.relight.widget.stateless;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;

import com.ittianyu.relight.view.AndroidLifecycle;
import com.ittianyu.relight.widget.Widget;

public abstract class LifecycleStatelessWidget<V extends View, T extends Widget<V>> extends StatelessWidget<V, T> implements AndroidLifecycle {
    protected final Lifecycle lifecycle;

    public LifecycleStatelessWidget(Context context, Lifecycle lifecycle) {
        super(context);
        this.lifecycle = lifecycle;
        lifecycle.addObserver(this);
    }

}
