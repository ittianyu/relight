package com.ittianyu.relight.widget.stateful.state;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.ittianyu.relight.thread.ThreadPool;
import com.ittianyu.relight.widget.Widget;

import com.ittianyu.relight.widget.stateful.state.listener.OnUpdateListener;
import com.ittianyu.relight.widget.stateful.state.strategy.CacheStrategy;
import com.ittianyu.relight.widget.stateful.state.strategy.CacheThenTaskStrategy;
import com.ittianyu.relight.widget.stateful.state.strategy.FilterStrategy;
import com.ittianyu.relight.widget.stateful.state.strategy.NotRepeatFilterStrategy;
import com.ittianyu.relight.widget.stateful.state.task.AsyncTask;
import com.ittianyu.relight.widget.stateful.state.task.CacheAsyncTask;
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
public abstract class State<T extends Widget> implements SetState {
    public static final FilterStrategy DEFAULT_UPDATE_STATE_STRATEGY = new NotRepeatFilterStrategy();

    private Handler handler = new Handler(Looper.getMainLooper());
    private OnUpdateListener onUpdateListener;
    private FilterStrategy filterStrategy;
    private UpdateTask updateTask = new UpdateTask(this);
    private Map<Object, Future> updateStateMap = new HashMap<>();

    public State() {
        this(DEFAULT_UPDATE_STATE_STRATEGY);
    }

    /**
     *
     * @param filterStrategy if null, it don't filter any func
     */
    public State(FilterStrategy filterStrategy) {
        this.filterStrategy = filterStrategy;
    }

    public void setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
    }

    public void init() {}

    public abstract T build(Context context);

    public void willUpdate() {}

    public void update() {
        if (onUpdateListener != null)
            onUpdateListener.update();
    }

    public void didUpdate() {
        cleanFinishedTask();
    }

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
     * run func in main thread.
     * @param func
     */
    @Override
    public void setState(Runnable func) {
        willUpdate();
        if (null != func)
            func.run();
        update();
        didUpdate();
    }

    /**
     * run func in main thread.
     * @param retryCount if > 0, it will retry when func return false
     * @param func
     */
    @Override
    public void setState(int retryCount, Callable<Boolean> func) {
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

    /**
     * run func in other thread. And update in main thread.
     *
     * @param func
     */
    @Override
    public void setStateAsync(Runnable func) {
        if (shouldIgnored(func)) {
            return;
        }

        willUpdate();
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
    @Override
    public void setStateAsync(int retryCount, Callable<Boolean> func) {
        if (shouldIgnored(func)) {
            return;
        }

        willUpdate();
        RetryableAsyncTask task = new RetryableAsyncTask(handler, func, updateTask, retryCount);
        Future<?> result = ThreadPool.get().submit(task);
        updateStateMap.put(func, result);
    }

    @Override
    public void setStateAsyncWithCache(Runnable cacheFunc, Runnable func) {
        setStateAsyncWithCache(new CacheThenTaskStrategy(), cacheFunc, func);
    }

    @Override
    public void setStateAsyncWithCache(CacheStrategy cacheStrategy, Runnable cacheFunc,
        Runnable func) {
        // cacheFunc or func is running, ignore
        if (shouldIgnored(func)) {
            return;
        }

        willUpdate();
        // run by cache strategy
        CacheAsyncTask task = new CacheAsyncTask(handler, cacheFunc, func, updateTask, cacheStrategy);
        Future<?> result = ThreadPool.get().submit(task);
        updateStateMap.put(func, result);
    }

    private boolean shouldIgnored(Object func) {
        if (filterStrategy == null) {
            return false;
        }
        return !filterStrategy.filter(updateStateMap, func);
    }

    private void cleanFinishedTask() {
        Iterator<Map.Entry<Object, Future>> it = updateStateMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Future> entry = it.next();
            if (entry.getValue().isDone()) {
                it.remove();
            }
        }
    }
}
