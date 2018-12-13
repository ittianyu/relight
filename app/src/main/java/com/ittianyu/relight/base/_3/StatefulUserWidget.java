package com.ittianyu.relight.base._3;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.datasource.UserDataSource;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateful.state.State;
import com.ittianyu.relight.widget.stateful.LifecycleStatefulWidget;

public class StatefulUserWidget extends LifecycleStatefulWidget<TextView, TextWidget> {
    private UserBean user = UserDataSource.getInstance().getUser();

    public StatefulUserWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<TextWidget> createState(Context context) {
        return StateUtils.create(new TextWidget(context, lifecycle));
    }

    @Override
    public void initWidget(TextWidget widget) {
        widget
                .text(user.getName())
                .gravity(Gravity.CENTER)
                .matchParent()
                .onClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setState(new Runnable() {
                            @Override
                            public void run() {
                                user = UserDataSource.getInstance().getUser();
                            }
                        });
                    }
                });
    }

    @Override
    public void updateWidget(TextWidget widget) {
        widget.text(user.getName());
    }
}
