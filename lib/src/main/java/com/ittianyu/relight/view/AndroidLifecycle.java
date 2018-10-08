package com.ittianyu.relight.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

/**
 * Will call when Activity or fragment state change:
 *      onStart
 *      onResume
 *      onPause
 *      onStop
 *      onDestroy
 */
public interface AndroidLifecycle extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    default void onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    default void onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    default void onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    default void onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    default void onDestroy() {
    }
}
