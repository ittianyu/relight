package com.ittianyu.relight.widget;

import android.view.View;

public interface RenderItem<V extends View, T extends Widget<V>> {
    T render(int index);
    int size();
}
