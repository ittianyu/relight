package com.ittianyu.relight.widget;

import android.view.View;

public interface Widget<T extends View> {
    T render();
}
