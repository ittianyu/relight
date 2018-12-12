package com.ittianyu.relight.widget.stateful.state;

import android.content.Context;

import com.ittianyu.relight.widget.Widget;

/**
 * call in order:
 * 1. init
 * 2. build
 * 3. willUpdate
 * 4. update
 * 5. didUpdate
 *
 */
public interface State<T extends Widget> {

    void dispose();

    void init();

    void willUpdate();

    void didUpdate();

    T build(Context context);

    void update();
}
