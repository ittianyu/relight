package com.ittianyu.relight.medium._2;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.datasource.UserDataSource;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateful.state.State;
import com.ittianyu.relight.widget.stateful.StatefulWidget;

public class StatefulUserWidget extends StatefulWidget<LinearLayout, LinearWidget> {
    private UserBean user = UserDataSource.getInstance().getUser();
    private TextWidget twId;
    private TextWidget twName;
    private Runnable updateTask = () -> {
        user = UserDataSource.getInstance().getUserFromRemote();
    };

    public StatefulUserWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<LinearWidget> createState(Context context) {
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
                .onClickListener(v -> setStateAsync(updateTask));

    }

    @Override
    public void update() {
        super.update();
        twId.text(user.getId() + "");
        twName.text(user.getName());
    }
}
