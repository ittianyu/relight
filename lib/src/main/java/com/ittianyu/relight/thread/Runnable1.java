package com.ittianyu.relight.thread;

public abstract class Runnable1<T> implements Runnable {
    protected T data;

    public Runnable1() {
    }

    public Runnable1(T data) {
        this.data = data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public final void run() {
        run(data);
    }

    public abstract void run(T data);
}
