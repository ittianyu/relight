package com.ittianyu.relight.widget.stateful.state.strategy;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * the same task only can be executed once at the same time
 */
public class NotRepeatFilterStrategy implements FilterStrategy {

    @Override
    public boolean filter(Map<Object, Future> updateStateMap, Object func) {
        if (null == func) {
            throw new IllegalArgumentException("func can't be null, please call setState(null) if you want to update without task running !");
        }
        return !isTaskRunning(updateStateMap, func);
    }

    private boolean isTaskRunning(Map<Object, Future> updateStateMap, Object func) {
        Future future = updateStateMap.get(func);
        // the same task is Running, ignore new task
        if (future != null && !future.isDone()) {
            return true;
        }
        return false;
    }
}
