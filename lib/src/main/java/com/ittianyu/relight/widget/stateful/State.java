package com.ittianyu.relight.widget.stateful;

import android.content.Context;

import com.ittianyu.relight.widget.Widget;

/**
 * call in order:
 * 1. init
 * 2. build
 * 3. willUpdate
 * 4. update
 * 5. didUpdate
 *
 */
public interface State<T extends Widget> {

    default void dispose() {
    }

    default void init() {
    }

    default void willUpdate() {
    }

    default void didUpdate() {
    }

    /**
     * run func in main thread.
     * @param func
     */
    default void setState(Runnable func) {
        willUpdate();
        if (null != func)
            func.run();
        update();
        didUpdate();
    }

    default T build(Context context) {
        return null;
    }

    default void update() {}
}
