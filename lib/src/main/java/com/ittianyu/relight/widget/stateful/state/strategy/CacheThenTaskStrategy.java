package com.ittianyu.relight.widget.stateful.state.strategy;

/**
 * run cache task and update view
 * then run task and update view
 * <p>
 * if cache task error, don't update view
 * always update when task finish
 */
public class CacheThenTaskStrategy extends CacheStrategy {

    @Override
    public boolean shouldRunCacheTask() {
        return true;
    }

    @Override
    public boolean shouldRunTask() {
        return true;
    }

    @Override
    public boolean shouldUpdateViewAfterTask() {
        return true;
    }
}
