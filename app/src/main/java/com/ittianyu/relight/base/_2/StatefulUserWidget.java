package com.ittianyu.relight.base._2;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;

import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.datasource.UserDataSource;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.stateful.StatefulWidget;
import com.ittianyu.relight.widget.stateful.state.State;

public class StatefulUserWidget extends StatefulWidget<View, UserWidget> {
    private UserBean user = UserDataSource.getInstance().getUser();

    public StatefulUserWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<UserWidget> createState(Context context) {
        return StateUtils.create(new UserWidget(context, lifecycle, user));
    }

    @Override
    public void initWidget(UserWidget widget) {
        widget.setOnClickListener(v -> setState(() -> {
            user = UserDataSource.getInstance().getUser();
        }));
        update();
    }

    @Override
    public void update() {
        widget.setUser(user);
        super.update();
    }
}
