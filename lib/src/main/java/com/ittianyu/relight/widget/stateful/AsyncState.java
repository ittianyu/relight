package com.ittianyu.relight.widget.stateful;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.ittianyu.relight.thread.ThreadPool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * call in order:
 * 1. init
 * 2. build
 * 3. willUpdate
 * 4. update
 * 5. didUpdate
 * <p>
 * You can call dispose to stop the state operations and release resources.
 *
 * @param <V>
 */
public abstract class AsyncState<V extends View> implements State<V> {
    private Handler handler = new Handler(Looper.getMainLooper());
    private List<Future> results = new LinkedList<>();

    @Override
    public void dispose() {
        for (Future future : results) {
            if (future.isDone())
                continue;
            future.cancel(true);
        }
        results.clear();
        handler = null;
    }

    /**
     * run func in other thread. And update in main thread.
     *
     * @param func
     */
    public void setStateAsync(Runnable func) {
        willUpdate();
        // run func in thread pool and update in main thread
        if (null == func) {
            return;
        }
        AsyncTask task = new AsyncTask(handler, func, new UpdateTask(this));
        Future<?> result = ThreadPool.get().submit(task);
        results.add(result);
    }

    private static class AsyncTask implements Runnable {
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
            handler = null;
        }
    }

    private static class UpdateTask implements Runnable {
        private AsyncState state;

        public UpdateTask(AsyncState state) {
            this.state = state;
        }

        @Override
        public void run() {
            state.update();
            state.didUpdate();
            state = null;
        }
    }

}
