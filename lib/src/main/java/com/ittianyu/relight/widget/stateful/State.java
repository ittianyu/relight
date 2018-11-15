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
 */
public abstract class State<T extends Widget> {

    protected void dispose() {
    }

    protected void init() {
    }

    protected void willUpdate() {
    }

    protected void didUpdate() {
    }

    /**
     * run func in main thread.
     *
     * @param func
     */
    public void setState(Runnable func) {
        willUpdate();
        if (null != func) {
            func.run();
        }
        update();
        didUpdate();
    }

    protected T build(Context context) {
        return null;
    }

    protected void update() {
    }

}
