package com.ittianyu.relight.widget.stateful.state.task;

import com.ittianyu.relight.widget.stateful.state.State;

public class UpdateTask implements Runnable {
    private State state;

    public UpdateTask(State state) {
        this.state = state;
    }

    @Override
    public void run() {
        state.update();
        state.didUpdate();
    }
}
