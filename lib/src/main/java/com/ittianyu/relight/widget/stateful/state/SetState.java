package com.ittianyu.relight.widget.stateful.state;

import com.ittianyu.relight.widget.stateful.state.strategy.CacheStrategy;

public interface SetState {
    void setState(Runnable func);
    void setStateAsync(Runnable func);
    void setStateAsyncWithCache(Runnable cacheFunc, Runnable func);
    void setStateAsyncWithCache(CacheStrategy cacheStrategy, Runnable cacheFunc, Runnable func);

}
