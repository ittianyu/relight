package com.ittianyu.relight.widget.stateful.state.strategy;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * if one update task is running, others will be ignore
 */
public class SingleTaskFilterStrategy implements FilterStrategy {

    @Override
    public boolean filter(Map<Object, Future> updateStateMap, Object func) {
        if (null == func) {
            throw new IllegalArgumentException("func can't be null, please call setState(null) if you want to update without task running !");
        }
        return !hasTaskRunning(updateStateMap);
    }

    private boolean hasTaskRunning(Map<Object, Future> updateStateMap) {
        for (Future future : updateStateMap.values()) {
            if (future.isDone())
                continue;
            return true;
        }
        return false;
    }
}
