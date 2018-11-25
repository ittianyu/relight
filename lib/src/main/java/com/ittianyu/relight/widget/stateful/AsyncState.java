package com.ittianyu.relight.widget.stateful;

import android.os.Handler;
import android.os.Looper;

import com.ittianyu.relight.thread.ThreadPool;
import com.ittianyu.relight.widget.Widget;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
 */
public abstract class AsyncState<T extends Widget> implements State<T> {
    public static final AsyncState.UpdateStateStrategy DEFAULT_UPDATE_STATE_STRATEGY = AsyncState.UpdateStateStrategy.Limited;

    private Handler handler = new Handler(Looper.getMainLooper());
    private OnUpdateListener onUpdateListener;
    private UpdateStateStrategy updateStateStrategy;
    private UpdateTask updateTask = new UpdateTask(this);
    private Map<Runnable, Future> updateStateMap = new HashMap<>();

    public AsyncState() {
        this(DEFAULT_UPDATE_STATE_STRATEGY);
    }

    public AsyncState(UpdateStateStrategy updateStateStrategy) {
        this.updateStateStrategy = updateStateStrategy;
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    @Override
    public void update() {
        if (onUpdateListener != null)
            onUpdateListener.update();
    }

    @Override
    public void didUpdate() {
        clearFinishedTask();
    }

    @Override
    public void dispose() {
        for (Future future : updateStateMap.values()) {
            if (future.isDone())
                continue;
            future.cancel(true);
        }
        updateStateMap = null;
        handler = null;
    }

    /**
     * run func in other thread. And update in main thread.
     *
     * @param func
     */
    public void setStateAsync(Runnable func) {
        // don't update if the func is ignored
        if (updateStateStrategy == UpdateStateStrategy.Limited) {
            Future future = updateStateMap.get(func);
            if (future != null && !future.isDone()) {// the same task is Running, ignore new task
                return;
            }
        } else if (updateStateStrategy == UpdateStateStrategy.Single) {
            if (!updateStateMap.isEmpty()) {
                for (Future future : updateStateMap.values()) {
                    if (future.isDone())
                        continue;
                    return;// if exist one task is running, don't allow new task
                }
            }
        }

        willUpdate();
        // run func in thread pool and update in main thread
        if (null == func) {
            updateTask.run();
            return;
        }
        AsyncTask task = new AsyncTask(handler, func, updateTask);
        Future<?> result = ThreadPool.get().submit(task);
        updateStateMap.put(func, result);
    }

    private void clearFinishedTask() {
        Iterator<Map.Entry<Runnable, Future>> it = updateStateMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Runnable, Future> entry = it.next();
            if(entry.getValue().isDone())
                it.remove();
        }
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
            mainThreadTask = null;
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
        }
    }

    interface OnUpdateListener {
        void update();
    }

    /**
     * None: allow call setStateAsync limitless
     * Limited: the same task only can be executed once at the same time
     * Single: if one update task is running, others will be ignore
     */
    public enum UpdateStateStrategy {
        None,
        Limited,
        Single
    }
}
