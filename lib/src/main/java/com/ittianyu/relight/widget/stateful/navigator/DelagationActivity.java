package com.ittianyu.relight.widget.stateful.navigator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.ittianyu.relight.widget.stateful.navigator.route.Route;

public class DelagationActivity extends AppCompatActivity {
    public static final String EXTRA_ROUTE = "route";
    public static final String EXTRA_NAME = "name";
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Route route = (Route) getIntent().getSerializableExtra(EXTRA_ROUTE);
        name = getIntent().getStringExtra(EXTRA_NAME);

        ActivityNavigator.pushActivity(name, this);

        setContentView(route.build(this, getLifecycle()).render());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityNavigator.popActivity(name);
    }
}
