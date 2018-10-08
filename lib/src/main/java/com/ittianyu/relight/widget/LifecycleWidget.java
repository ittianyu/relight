package com.ittianyu.relight.widget;

import android.view.View;

import com.ittianyu.relight.view.AndroidLifecycle;

public interface LifecycleWidget<T extends View> extends Widget<T>, AndroidLifecycle {
}
