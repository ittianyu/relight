package com.ittianyu.relight.medium._5;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.datasource.UserDataSource;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateful.LifecycleStatefulWidget;
import com.ittianyu.relight.widget.stateful.state.AsyncState;
import java.util.Date;
import java.util.concurrent.Callable;

public class StatefulUserWidget extends LifecycleStatefulWidget<LinearLayout, LinearWidget> {
    private static final int RETRY_COUNT = 2;

    private UserBean user;
    private TextWidget twId;
    private TextWidget twName;
    private Callable<Boolean> updateTask = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            System.out.println("Time:" + new Date() + ", get data...");
            try {
                user = UserDataSource.getInstance().getUserFromRemoteWithRandomError();
                return true;
            } catch (Exception e) {
                user = null;
                return false;
            }
        }
    };
    View.OnClickListener loadData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setStateAsync(updateTask, RETRY_COUNT);
        }
    };

    public StatefulUserWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected AsyncState<LinearWidget> createState(Context context) {
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

        updateWidget(widget);

        loadData.onClick(null);
    }

    @Override
    public void updateWidget(LinearWidget widget) {
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
