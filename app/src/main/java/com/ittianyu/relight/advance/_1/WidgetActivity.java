package com.ittianyu.relight.advance._1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import com.ittianyu.relight.advance._1.RouterConfig.Advance1;
import com.ittianyu.relight.widget.native_.ButtonWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.stateful.navigator.ActivityNavigator;
import com.ittianyu.relight.widget.stateful.navigator.Navigator;
import com.ittianyu.relight.widget.stateful.navigator.route.WidgetRoute;

public class WidgetActivity extends AppCompatActivity implements OnClickListener {

    private static final int ID_BTN_WIDGET_NAVIGATOR = 0;
    private static final int ID_BTN_ACTIVITY_NAVIGATOR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearWidget root = new LinearWidget(this, getLifecycle(),
            new ButtonWidget(this, getLifecycle()).text("widget navigator").allCaps(false)
                .id(ID_BTN_WIDGET_NAVIGATOR).onClick(this),
            new ButtonWidget(this, getLifecycle()).text("activity navigator").allCaps(false)
                .id(ID_BTN_ACTIVITY_NAVIGATOR).onClick(this)
        )
            .gravity(Gravity.CENTER)
            .orientation(LinearWidget.vertical)
            .matchParent();
        setContentView(root.render());
    }

    @Override
    public void onClick(View v) {
        Class<? extends Activity> activityClass = null;
        switch (v.getId()) {
            case ID_BTN_WIDGET_NAVIGATOR:
                activityClass = com.ittianyu.relight.advance._1.widget.WidgetActivity.class;
                break;
            case ID_BTN_ACTIVITY_NAVIGATOR:
                activityClass = com.ittianyu.relight.advance._1.activity.WidgetActivity.class;
                break;
        }
        startActivity(new Intent(this, activityClass));
    }

}
