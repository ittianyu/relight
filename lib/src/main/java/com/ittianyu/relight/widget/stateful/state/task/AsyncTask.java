package com.ittianyu.relight.widget.stateful.state.task;

import android.os.Handler;

public class AsyncTask implements Runnable {
    private Handler handler;
    private Runnable asyncTask;
    private Runnable mainThreadTask;

    public AsyncTask(Handler handler, Runnable asyncTask, Runnable mainThreadTask) {
        this.handler = handler;
        this.asyncTask = asyncTask;
        this.mainThreadTask = mainThreadTask;
    }

    @Override
    public void run() {
        asyncTask.run();
        handler.post(mainThreadTask);
        mainThreadTask = null;
        handler = null;
    }
}
