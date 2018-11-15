package com.ittianyu.relight.widget.stateful;

import android.os.Handler;
import android.os.Looper;

import com.ittianyu.relight.thread.ThreadPool;
import com.ittianyu.relight.widget.Widget;

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
 */
public abstract class AsyncState<T extends Widget> extends State<T> {
    private Handler handler = new Handler(Looper.getMainLooper());
    private List<Future> results = new LinkedList<>();
    private OnUpdateListener onUpdateListener;

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

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

    @Override
    public void update() {
        if (onUpdateListener != null)
            onUpdateListener.update();
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

    static interface OnUpdateListener {
        void update();
    }
}
