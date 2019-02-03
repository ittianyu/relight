package com.ittianyu.relight.widget.stateful.state.task;

import java.util.Arrays;
import java.util.Objects;

public class TaskSet implements Runnable {
    private Runnable[] tasks;

    public TaskSet(Runnable[] tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskSet taskSet = (TaskSet) o;
        return Arrays.equals(tasks, taskSet.tasks);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tasks);
    }
}
