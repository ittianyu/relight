package com.ittianyu.relight.widget;

import android.view.View;

public interface ContainerWidget<V extends View, T extends Widget<V>> {
    default void initWidget(T widget){}
}