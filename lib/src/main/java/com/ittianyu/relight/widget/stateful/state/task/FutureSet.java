package com.ittianyu.relight.widget.stateful.state.task;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureSet<V> implements Future<V> {
    private Set<Future<V>> futures = new HashSet<>();

    public void add(Future<V> future) {
        futures.add(future);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        boolean result = true;
        for (Future future : futures) {
            if (!future.cancel(mayInterruptIfRunning)) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean isCancelled() {
        for (Future future : futures) {
            if (!future.isCancelled()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isDone() {
        for (Future future : futures) {
            if (!future.isDone()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public V get() throws ExecutionException, InterruptedException {
        for (Future<V> future : futures) {
            future.get();
        }
        return null;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        for (Future<V> future : futures) {
            future.get(timeout, unit);
        }
        return null;
    }
}
