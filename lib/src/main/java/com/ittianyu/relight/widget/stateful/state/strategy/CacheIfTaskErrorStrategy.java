package com.ittianyu.relight.widget.stateful.state.strategy;

/**
 * run task first, if success, don't run cache anymore.
 * if task error, run cache.
 *
 * Always update view when task and cache task finished.
 */
public class CacheIfTaskErrorStrategy extends CacheStrategy {

    @Override
    public boolean shouldRunCacheTask() {
        // run cache task if task error
        return getTaskError() != null;
    }

    @Override
    public boolean shouldRunTask() {
        return true;
    }

    @Override
    public boolean runCacheFirst() {
        return false;
    }

    @Override
    public boolean shouldUpdateViewAfterTask() {
        return true;
    }

    @Override
    public boolean shouldUpdateViewAfterCacheTask() {
        return true;
    }
}
