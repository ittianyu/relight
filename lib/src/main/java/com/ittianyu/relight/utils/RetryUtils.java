package com.ittianyu.relight.utils;

import java.util.concurrent.Callable;

public class RetryUtils {

    /**
     * create a Runnable which can retry when func return false
     * This task is executed up to ${retryCount + 1} times at most
     * @param retryCount if > 0, it will retry when func return false
     * @param func
     * @return
     */
    public static Runnable create(final int retryCount, Callable<Boolean> func) {
        return () -> {
            int count = retryCount;
            try {
                boolean result;
                do {
                    result = func.call();
                    count--;
                } while (!result && count >= 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
