package com.ittianyu.relight.base._6;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;

import com.ittianyu.relight.R;
import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.datasource.UserDataSource;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.native_.RelativeWidget;
import com.ittianyu.relight.widget.native_.RelativeWidget.Prop;
import com.ittianyu.relight.widget.native_.RelativeWidget.WidgetAndProps;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateful.state.State;
import com.ittianyu.relight.widget.stateful.StatefulWidget;


public class StatefulUserWidget extends StatefulWidget<RelativeLayout, RelativeWidget> {
    private UserBean user = UserDataSource.getInstance().getUser();
    private TextWidget twId;
    private TextWidget twName;

    public StatefulUserWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<RelativeWidget> createState(Context context) {
        twId = new TextWidget(context, lifecycle) {
            @Override
            protected void initProps() {
                super.initProps();
                id = R.id.tw_id;
                textSize = dp(16);
                textColor = Color.BLACK;
            }
        };
        twName = new TextWidget(context, lifecycle);
        twName.id(R.id.tw_name);
//        RelativeWidget root = new RelativeWidget(context, lifecycle,
//                new WidgetAndProps(twId, new Prop(RelativeLayout.CENTER_IN_PARENT, Prop.TRUE)),
//                new WidgetAndProps(twName, new Prop(RelativeLayout.BELOW, R.id.tw_id),
//                        new Prop(RelativeLayout.CENTER_HORIZONTAL, Prop.TRUE))
//        );
        RelativeWidget root = new RelativeWidget(context, lifecycle,
            twId.addRule(RelativeLayout.CENTER_IN_PARENT, Prop.TRUE),
            twName.addRule(RelativeLayout.BELOW, R.id.tw_id)
                .addRule(RelativeLayout.CENTER_HORIZONTAL, Prop.TRUE)
        );
        return StateUtils.create(root);
    }

    @Override
    public void initWidget(RelativeWidget widget) {
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
