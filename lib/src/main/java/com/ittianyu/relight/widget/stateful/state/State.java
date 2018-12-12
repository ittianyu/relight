package com.ittianyu.relight.widget.stateful.state;

import android.content.Context;

import com.ittianyu.relight.widget.Widget;
import java.util.concurrent.Callable;

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

    /**
     * run func in main thread.
     * @param func
     * @param retryCount if > 0, it will retry when func return false
     */
    default void setState(Callable<Boolean> func, int retryCount) {
        willUpdate();
        if (null != func) {
            try {
                boolean result;
                do {
                    result = func.call();
                    retryCount--;
                } while (!result && retryCount >= 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        update();
        didUpdate();
    }

    default T build(Context context) {
        return null;
    }

    default void update() {}
}
