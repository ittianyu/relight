package com.ittianyu.relight.widget.stateful.state.task;

import com.ittianyu.relight.widget.stateful.state.AsyncState;

public class UpdateTask implements Runnable {
    private AsyncState state;

    public UpdateTask(AsyncState state) {
        this.state = state;
    }

    @Override
    public void run() {
        state.update();
        state.didUpdate();
    }
}
