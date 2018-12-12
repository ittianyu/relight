package com.ittianyu.relight.widget.stateful.state.task;

import android.os.Handler;
import java.util.concurrent.Callable;

public class RetryableAsyncTask implements Runnable {
    private Handler handler;
    private Callable<Boolean> asyncTask;
    private Runnable mainThreadTask;
    private int retryCount;

    /**
     *
     * @param handler
     * @param asyncTask
     * @param mainThreadTask
     * @param retryCount the execute count when async task return false. If you don't want retry, please  use AsyncTask or set retryCount 0
     */
    public RetryableAsyncTask(Handler handler, Callable<Boolean> asyncTask, Runnable mainThreadTask, int retryCount) {
        this.handler = handler;
        this.asyncTask = asyncTask;
        this.mainThreadTask = mainThreadTask;
        this.retryCount = retryCount;
    }

    @Override
    public void run() {
        try {
            boolean result;
            do {
                result = asyncTask.call();
                retryCount--;
            } while (!result && retryCount >= 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.post(mainThreadTask);
        mainThreadTask = null;
        handler = null;
    }
}
