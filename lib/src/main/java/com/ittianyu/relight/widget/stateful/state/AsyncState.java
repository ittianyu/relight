package com.ittianyu.relight.widget.stateful.state;

import android.os.Handler;
import android.os.Looper;

import com.ittianyu.relight.thread.ThreadPool;
import com.ittianyu.relight.widget.Widget;

import com.ittianyu.relight.widget.stateful.state.listener.OnUpdateListener;
import com.ittianyu.relight.widget.stateful.state.task.AsyncTask;
import com.ittianyu.relight.widget.stateful.state.task.RetryableAsyncTask;
import com.ittianyu.relight.widget.stateful.state.task.UpdateTask;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
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
    private Map<Object, Future> updateStateMap = new HashMap<>();

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
        if (filterTaskByStrategy(func)) {
            return;
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

    /**
     * run func in other thread. And update in main thread.
     *
     * @param func
     * @param retryCount if > 0, it will retry when func return false
     */
    public void setStateAsync(Callable<Boolean> func, int retryCount) {
        // don't update if the func is ignored
        if (filterTaskByStrategy(func)) {
            return;
        }

        willUpdate();
        // run func in thread pool and update in main thread
        if (null == func) {
            updateTask.run();
            return;
        }
        RetryableAsyncTask task = new RetryableAsyncTask(handler, func, updateTask, retryCount);
        Future<?> result = ThreadPool.get().submit(task);
        updateStateMap.put(func, result);
    }

    private boolean filterTaskByStrategy(Object func) {
        if (updateStateStrategy == UpdateStateStrategy.Limited) {
            // if the same task is running, return
            if (isTaskRunning(func)) {
                return true;
            }
        } else if (updateStateStrategy == UpdateStateStrategy.Single) {
            // if exist one task is running, don't allow new task
            if (hasTaskRunning()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasTaskRunning() {
        for (Future future : updateStateMap.values()) {
            if (future.isDone())
                continue;
            return true;
        }
        return false;
    }

    private boolean isTaskRunning(Object func) {
        Future future = updateStateMap.get(func);
        // the same task is Running, ignore new task
        if (future != null && !future.isDone()) {
            return true;
        }
        return false;
    }

    private void clearFinishedTask() {
        Iterator<Map.Entry<Object, Future>> it = updateStateMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Object, Future> entry = it.next();
            if(entry.getValue().isDone())
                it.remove();
        }
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
