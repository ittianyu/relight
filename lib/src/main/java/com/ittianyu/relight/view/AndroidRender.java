package com.ittianyu.relight.view;

import android.content.Context;
import android.view.View;

/**
 * call in order when first render:
 *      1. #createView
 *      2. #initView
 *      3. initEvent
 *      4. initData
 *      5. updateView
 * Then will call updateView if state changed
 *

 * @param <T>
 */
public interface AndroidRender<T extends View> {

    T createView(Context context);

    default void initView(T view) {
    }

    default void initEvent(T view) {
    }

    default void initData() {
    }

}
