package com.ittianyu.relight.widget.stateful.state.strategy;

public abstract class CacheStrategy {
    // throws from task, if task run success, it will be null
    protected Throwable cacheTaskError;
    protected Throwable taskError;

    public void setCacheTaskError(Throwable cacheTaskError) {
        this.cacheTaskError = cacheTaskError;
    }

    public void setTaskError(Throwable taskError) {
        this.taskError = taskError;
    }

    public Throwable getCacheTaskError() {
        return cacheTaskError;
    }

    public Throwable getTaskError() {
        return taskError;
    }

    /**
     *
     * @return true to run cache task
     */
    public abstract boolean shouldRunCacheTask();

    /**
     * @return true to update view when cache task finish
     */
    public boolean shouldUpdateViewAfterCacheTask() {
        if (runCacheFirst()) {
            // if run cache first, update the view when cache task success
            return cacheTaskError == null;
        } else {
            // if run task first, update the view when task failed
            return taskError != null;
        }
    }

    /**
     *
     * @return true to run task
     */
    public abstract boolean shouldRunTask();

    /**
     * @return true to update view when task finish
     */
    public boolean shouldUpdateViewAfterTask() {
        if (runCacheFirst()) {
            // if run cache first
            // update the view when task success
            if (taskError == null) {
                return true;
            }
            // if task error, update view when cache error too
            return cacheTaskError != null;
        } else {
            // if run task first, update the view when task success
            return taskError != null;
        }
    }

    /**
     *
     * @return true to run cache task and then run task
     */
    public boolean runCacheFirst() {
        return true;
    }
}
