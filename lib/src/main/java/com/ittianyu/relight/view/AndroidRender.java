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

    void initView(T view);

    void initEvent(T view);

    void initData();

}
