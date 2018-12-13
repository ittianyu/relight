package com.ittianyu.relight.medium._2;

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
import com.ittianyu.relight.widget.stateful.state.State;

public class StatefulUserWidget extends LifecycleStatefulWidget<LinearLayout, LinearWidget> {
    private UserBean user = UserDataSource.getInstance().getUser();
    private TextWidget twId;
    private TextWidget twName;
    private Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            user = UserDataSource.getInstance().getUserFromRemote();
        }
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
                .onClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setStateAsync(updateTask);
                    }
                });

        updateWidget(widget);
    }

    @Override
    public void updateWidget(LinearWidget widget) {
        twId.text(user.getId() + "");
        twName.text(user.getName());
    }
}
