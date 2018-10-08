package com.ittianyu.relight.view;

import android.content.Context;
import android.view.View;

/**
 * call in order when first render:
 *      1. #createView
 *      2. #initView
 *      3. bindEvent
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

    default void bindEvent(T view) {
    }

    default void initData() {
    }

    default void updateView(T view) {
    }

}
