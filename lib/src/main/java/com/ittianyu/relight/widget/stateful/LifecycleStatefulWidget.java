package com.ittianyu.relight.widget.stateful;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;

import com.ittianyu.relight.view.AndroidLifecycle;
import com.ittianyu.relight.widget.Widget;

public abstract class LifecycleStatefulWidget<V extends View, T extends Widget<V>>
        extends StatefulWidget<V, T> implements AndroidLifecycle {
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
