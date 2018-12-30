package com.ittianyu.relight.widget.stateful.navigator.route;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import com.ittianyu.relight.widget.Widget;

public interface Route {
    Widget build(Context context, Lifecycle lifecycle);
    String path();
}
