package com.ittianyu.relight.medium._5;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.datasource.UserDataSource;
import com.ittianyu.relight.utils.RetryUtils;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateful.state.State;

import java.util.Date;

public class StatefulUserWidget extends StatefulWidget<LinearLayout, LinearWidget> {
    private static final int RETRY_COUNT = 2;// This task is executed up to 3 times at most

    private UserBean user;
    private TextWidget twId;
    private TextWidget twName;
    private Runnable updateTask = RetryUtils.create(RETRY_COUNT, () -> {
        System.out.println("Time:" + new Date() + ", get data...");
        try {
            user = UserDataSource.getInstance().getUserFromRemoteWithRandomError();
            return true;
        } catch (Exception e) {
            user = null;
            return false;
        }
    });
    View.OnClickListener loadData = v -> setStateAsync(updateTask);

    public StatefulUserWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<LinearWidget> createState(Context context, Lifecycle lifecycle) {
        twId = new TextWidget(context, lifecycle);
        twName = new TextWidget(context, lifecycle);
        LinearWidget root = new LinearWidget(context, lifecycle, twId, twName);
        return StateUtils.create(root);
    }

    @Override
    public void initWidget(LinearWidget widget) {
        widget
                .orientation(LinearWidget.vertical)
                .gravity(Gravity.CENTER)
                .matchParent()
                .onClickListener(loadData);

        loadData.onClick(null);
    }

    @Override
    public void update() {
        super.update();
        String id;
        String name;
        if (user == null) {
            id = "";
            name = "not found user";
        } else {
            id = user.getId() + "";
            name = user.getName();
        }
        twId.text(id);
        twName.text(name);
    }
}
