package com.ittianyu.relight.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    private static ExecutorService executorService;

    public static void set(ExecutorService executorService) {
        ThreadPool.executorService = executorService;
    }

    public static ExecutorService get() {
        if (executorService == null)
            executorService = Executors.newCachedThreadPool();
        return executorService;
    }

}
