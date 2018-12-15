package com.ittianyu.relight.widget.stateful.state.task;

import android.os.Handler;
import com.ittianyu.relight.widget.stateful.state.strategy.CacheStrategy;

public class CacheAsyncTask implements Runnable {
    private Handler handler;
    private Runnable cacheTask;
    private Runnable asyncTask;
    private Runnable mainThreadTask;
    private CacheStrategy cacheStrategy;
    private boolean readyForNextTask = true;
    private Runnable notifyReadyForNextTask = () -> {
        synchronized (this) {
            readyForNextTask = true;
            notify();
        }
    };

    public CacheAsyncTask(Handler handler, Runnable cacheTask, Runnable asyncTask, Runnable mainThreadTask, CacheStrategy cacheStrategy) {
        this.handler = handler;
        this.cacheTask = cacheTask;
        this.asyncTask = asyncTask;
        this.mainThreadTask = mainThreadTask;
        this.cacheStrategy = cacheStrategy;
    }

    @Override
    public void run() {
        if (cacheStrategy.runCacheFirst()) {
            runCache();
            runTask();
        } else {
            runTask();
            runCache();
        }
        mainThreadTask = null;
        handler = null;
    }

    private void runTask() {
        if (cacheStrategy.shouldRunTask()) {
            waitForMainThreadTask();
            Throwable t = null;
            try {
                asyncTask.run();
            } catch (Throwable throwable) {
                t = throwable;
            }
            cacheStrategy.setTaskError(t);
            if (cacheStrategy.shouldUpdateViewAfterTask()) {
                readyForNextTask = false;
                handler.post(mainThreadTask);
                handler.post(notifyReadyForNextTask);
            }
        }
    }

    private void runCache() {
        if (cacheStrategy.shouldRunCacheTask()) {
            waitForMainThreadTask();
            Throwable t = null;
            try {
                cacheTask.run();
            } catch (Throwable throwable) {
                t = throwable;
            }
            cacheStrategy.setCacheTaskError(t);
            if (cacheStrategy.shouldUpdateViewAfterCacheTask()) {
                readyForNextTask = false;
                handler.post(mainThreadTask);
                handler.post(notifyReadyForNextTask);
            }
        }
    }

    private void waitForMainThreadTask() {
        if (readyForNextTask) {
            return;
        }
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
