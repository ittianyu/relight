package com.ittianyu.relight.net_data;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.ittianyu.relight.MainApplication;
import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.bean.UserRemoteBean;
import com.ittianyu.relight.common.datasource.UserRemoteDataSource;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.stateful.AsyncState;
import com.ittianyu.relight.widget.stateful.LifecycleStatefulWidget;

public class StatefulUserWidget extends LifecycleStatefulWidget<View, UserWidget> {
    private UserBean user = null;

    public StatefulUserWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected AsyncState<UserWidget> createState(Context context) {
        return StateUtils.create(new UserWidget(context, lifecycle));
    }

    @Override
    public void initWidget(UserWidget widget) {
        fetchUserInfoFromNet();
        widget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchUserInfoFromNet();
            }
        });
    }

    private void fetchUserInfoFromNet() {
        UserRemoteDataSource.getInstance().fetchUser(new UserRemoteDataSource.Callback() {
            @Override
            public void onStart() {
                Toast.makeText(MainApplication.getInstance(), "开始请求数据", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(final UserRemoteBean data) {
                Toast.makeText(MainApplication.getInstance(), "数据获取成功", Toast.LENGTH_SHORT).show();
                setState(new Runnable() {
                    @Override
                    public void run() {
                        if (user == null) {
                            user = data.getData();
                        } else {
                            user.update(data.getData());
                        }
                    }
                });
            }

            @Override
            public void onFailure() {
                Toast.makeText(MainApplication.getInstance(), "数据获取失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void updateWidget(UserWidget widget) {
        widget.setUser(user);
    }
}
