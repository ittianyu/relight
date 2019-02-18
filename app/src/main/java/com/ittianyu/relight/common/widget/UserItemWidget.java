package com.ittianyu.relight.common.widget;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.widget.LinearLayout;

import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

public class UserItemWidget extends StatelessWidget<LinearLayout, LinearWidget> {
    private TextWidget twId;
    private TextWidget twName;
    private UserBean user;

    public UserItemWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected LinearWidget build(Context context, Lifecycle lifecycle) {
        twId = new TextWidget(context, lifecycle);
        twName = new TextWidget(context, lifecycle);
        return new LinearWidget(context, lifecycle, twId, twName);
    }

    @Override
    public void initWidget(LinearWidget widget) {
        widget.orientation(LinearWidget.horizontal)
                .padding(16.0f);
        twId.marginEnd(16.0f);
    }

    @Override
    public void update() {
        super.update();
        if (user == null)
            return;
        twId.text(user.getId() + "");
        twName.text(user.getName());
    }

    public void setData(UserBean user) {
        this.user = user;
        update();
    }

}
