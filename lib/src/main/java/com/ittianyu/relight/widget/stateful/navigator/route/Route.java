package com.ittianyu.relight.widget.stateful.navigator.route;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import com.ittianyu.relight.widget.Widget;

public interface Route<V extends View> {
    Widget<V> build(Context context, Lifecycle lifecycle);
    String path();
}
