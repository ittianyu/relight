package com.ittianyu.relight.advance._2;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.datasource.UserDataSource;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.native_.BaseLinearWidget;
import com.ittianyu.relight.widget.native_.ButtonWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.ScrollWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateful.state.State;

public class SetStateAsyncWidget extends StatefulWidget<LinearLayout, LinearWidget> {

    private TextWidget twLog;

    public SetStateAsyncWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    private Runnable[] tasks = new Runnable[100];
    private UserBean[] users = new UserBean[100];

    @Override
    protected State<LinearWidget> createState(Context context, Lifecycle lifecycle) {
        twLog = new TextWidget(context, lifecycle)
            .widthMatchAndHeightWrap();
        LinearWidget root = new LinearWidget(context, lifecycle,
            new ButtonWidget(context, lifecycle)
                .widthMatchAndHeightWrap()
                .marginBottom(16.f)
                .allCaps(false)
                .text("request 100 users from network")
                .onClick(v -> {
                    setStateAsync(tasks);
                    Toast.makeText(context, "Running now, you can see the info in logcat", Toast.LENGTH_SHORT).show();
                }),
            new ScrollWidget(context, lifecycle,
                twLog
            ).matchParent()
                .verticalScrollBarEnabled(false)

        ).orientation(BaseLinearWidget.vertical)
            .matchParent()
            .padding(16.f);
        return StateUtils.create(root);
    }

    @Override
    public void initWidget(LinearWidget widget) {
        for (int i = 0; i < tasks.length; i++) {
            int finalI = i;
            tasks[i] = () -> {
                System.out.println("running task " + finalI + " in thread " + Thread.currentThread().getName());
                users[finalI] = UserDataSource.getInstance().getUserFromRemote();
                System.out.println("task " + finalI + " finish, result: " + users[finalI]);
            };
        }
    }

    @Override
    public void update() {
        super.update();
        System.out.println("all tasks are finished...");
        twLog.text(TextUtils.join("\n", users));
    }

}
