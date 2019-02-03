package com.ittianyu.relight.widget.stateful.state.task;

import android.os.Handler;
import java.util.concurrent.atomic.AtomicInteger;

public class CountAsyncTask implements Runnable {
    private Handler handler;
    private Runnable asyncTask;
    private Runnable mainThreadTask;
    private AtomicInteger count;

    public CountAsyncTask(Handler handler, Runnable asyncTask, Runnable mainThreadTask, AtomicInteger count) {
        this.handler = handler;
        this.asyncTask = asyncTask;
        this.mainThreadTask = mainThreadTask;
        this.count = count;
    }

    @Override
    public void run() {
        asyncTask.run();
        if (count.decrementAndGet() == 0) {
            handler.post(mainThreadTask);
        }
        mainThreadTask = null;
        handler = null;
    }
}
