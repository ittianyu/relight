package com.ittianyu.relight.medium._7;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.widget.LinearLayout;
import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.datasource.UserDataSource;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.native_.ButtonWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateful.LifecycleStatefulWidget;
import com.ittianyu.relight.widget.stateful.state.State;
import com.ittianyu.relight.widget.stateless.LifecycleStatelessWidget;

public class StartActivityWidget extends LifecycleStatelessWidget<LinearLayout, LinearWidget> {
    private static final int REQ = 1;
    public static final String RESULT_MSG = "msg";

    private TextWidget twNote;
    private ButtonWidget btnStartActivity;
    private ButtonWidget btnStartActivityForResult;

    public StartActivityWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected LinearWidget build(Context context) {
        btnStartActivity = new ButtonWidget(context, lifecycle, "startActivity").allCaps(false);
        btnStartActivityForResult = new ButtonWidget(context, lifecycle, "startActivityForResult").allCaps(false);
        twNote = new TextWidget(context, lifecycle).textColor(Color.BLACK);
        return new LinearWidget(context, lifecycle,
            btnStartActivity,
            btnStartActivityForResult,
            twNote.marginTop(16.0f)
        );
    }

    @Override
    public void initWidget(LinearWidget widget) {
        widget
            .orientation(LinearWidget.vertical)
            .gravity(Gravity.CENTER)
            .matchParent();

        btnStartActivity.onClickListener(v -> {
            startActivity(new Intent(context, WidgetActivity.class));
        });

        btnStartActivityForResult.onClickListener(v -> {
            startActivityForResult(new Intent(context, WidgetActivity.class), REQ);
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("onActivityResult, requestCode:" + requestCode + ", resultCode:" + resultCode + ", data:" + data);
        if (requestCode == REQ && resultCode == Activity.RESULT_OK && data != null) {
            twNote.text("onActivityResult:" + data.getStringExtra(RESULT_MSG));
        }
    }

}
