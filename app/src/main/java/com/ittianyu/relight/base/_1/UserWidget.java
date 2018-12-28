package com.ittianyu.relight.base._1;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ittianyu.relight.R;
import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.widget.native_.AndroidWidget;

public class UserWidget extends AndroidWidget<View> {
    private TextView tvId;
    private TextView tvName;
    private UserBean user;

    public UserWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public UserWidget(Context context, Lifecycle lifecycle, UserBean user) {
        super(context, lifecycle);
        this.user = user;
    }

    public void user(UserBean user) {
        this.user = user;
        update();
    }

    @Override
    public View createView(Context context) {
        return View.inflate(context, R.layout.activity_user, null);
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        tvId = view.findViewById(R.id.tv_id);
        tvName = view.findViewById(R.id.tv_name);
        update();
    }

    @Override
    public void update() {
        super.update();
        if (user == null) {
            tvName.setText("no data");
        } else {
            tvId.setText(user.getId() + "");
            tvName.setText(user.getName());
        }
    }
}
