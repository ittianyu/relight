package com.ittianyu.relight.widget;

import android.view.View;

public interface ContainerWidget<V extends View, T extends Widget<V>> {
    void initWidget(T widget);
    T getInnerWidget();
}