package com.ittianyu.relight.thread;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MainHandler extends Handler {

    public MainHandler() {
    }

    public MainHandler(Callback callback) {
        super(callback);
    }

    public MainHandler(Looper looper) {
        super(looper);
    }

    public MainHandler(Looper looper, Callback callback) {
        super(looper, callback);
    }

    @Override
    public void dispatchMessage(Message msg) {
        if (msg.what == 0) {
            msg.what = 1;
        }
        super.dispatchMessage(msg);
    }
}
