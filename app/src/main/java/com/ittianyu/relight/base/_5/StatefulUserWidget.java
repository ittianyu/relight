package com.ittianyu.relight.base._5;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.datasource.UserDataSource;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateful.state.State;
import com.ittianyu.relight.widget.stateful.StatefulWidget;

public class StatefulUserWidget extends StatefulWidget<FrameLayout, FrameWidget> {
    private UserBean user = UserDataSource.getInstance().getUser();
    private TextWidget twId;
    private TextWidget twName;

    public StatefulUserWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<FrameWidget> createState(Context context) {
        twId = new TextWidget(context, lifecycle);
        twName = new TextWidget(context, lifecycle);
        FrameWidget root = new FrameWidget(context, lifecycle, twId, twName);
        return StateUtils.create(root);
    }

    @Override
    public void initWidget(FrameWidget widget) {
        twId.layoutGravity(Gravity.CENTER);
        twName.layoutGravity(Gravity.CENTER)
                .marginTop(20.f);

        widget
                .matchParent()
                .onClickListener(v -> setState(() -> {
                    user = UserDataSource.getInstance().getUser();
                }));

    }

    @Override
    public void update() {
        super.update();
        twId.text(user.getId() + "");
        twName.text(user.getName());
    }
}
