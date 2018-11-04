package com.ittianyu.relight.widget;

import android.view.View;

public interface StatefulContainerWidget<V extends View, T extends Widget<V>> extends ContainerWidget<V, T> {
    default void updateWidget(T widget){}
}
