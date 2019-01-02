package com.ittianyu.relight.advance._1;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import com.ittianyu.relight.widget.stateful.navigator.Navigator;
import com.ittianyu.relight.widget.stateful.navigator.route.Route;

public class NavigatorWidget extends Navigator {

    public NavigatorWidget(Context context, Lifecycle lifecycle, String name, Route initRoute, Route... routes) {
        super(context, lifecycle, name, initRoute, routes);
    }

}
