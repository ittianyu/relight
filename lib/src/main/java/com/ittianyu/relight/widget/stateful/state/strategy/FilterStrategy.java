package com.ittianyu.relight.widget.stateful.state.strategy;

import java.util.Map;
import java.util.concurrent.Future;

public interface FilterStrategy {

    /**
     * @param updateStateMap all running or finished func map
     * @param func           the task which need to filter
     * @return true to keep the func, false to ignore the func
     */
    boolean filter(Map<Runnable, Future> updateStateMap, Object func);
}
