package com.ittianyu.relight.advance._1.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.ittianyu.relight.advance._1.RouterConfig.Advance1;
import com.ittianyu.relight.advance._1.Screen;
import com.ittianyu.relight.widget.stateful.navigator.Navigator;
import com.ittianyu.relight.widget.stateful.navigator.WidgetNavigator;
import com.ittianyu.relight.widget.stateful.navigator.route.WidgetRoute;

public class WidgetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Navigator navigator = new WidgetNavigator(this, getLifecycle(), Advance1.name,
            new WidgetRoute<>(Advance1.firstScreen, Screen.class, "first screen"),
            new WidgetRoute<>(Advance1.secondScreen, Screen.class, "second screen")
        );
        setContentView(navigator.render());
    }
}
