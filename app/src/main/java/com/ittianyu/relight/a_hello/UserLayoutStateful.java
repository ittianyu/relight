package com.ittianyu.relight.a_hello;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ittianyu.relight.R;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.atomic.AndroidWidget;
import com.ittianyu.relight.widget.stateful.AsyncState;
import com.ittianyu.relight.widget.stateful.LifecycleStatefulWidget;


public class UserLayoutStateful extends LifecycleStatefulWidget<View> {
    private User user = UserModel.getInstance().getUser();

    public UserLayoutStateful(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected AsyncState<View> createState(Context context) {
        return StateUtils.create(new UserLayout(context, lifecycle, user));
    }

    class UserLayout extends AndroidWidget<View> {
        private User user;

        private TextView tvId;
        private TextView tvName;

        public UserLayout(Context context, Lifecycle lifecycle, User user) {
            super(context, lifecycle);
            this.user = user;
        }

        @Override
        public View createView(Context context) {
            return View.inflate(context, R.layout.a_activity_user, null);
        }

        @Override
        public void initView(View view) {
            tvId = view.findViewById(R.id.tv_id);
            tvName = view.findViewById(R.id.tv_name);
        }

        @Override
        public void bindEvent(View view) {
            view.setOnClickListener(v -> setState(() -> {
                user = UserModel.getInstance().getUser();
            }));
        }

        @Override
        public void initData() {
        }

        @Override
        public void updateView(View view) {
            tvId.setText(user.getId() + "");
            tvName.setText(user.getName());
        }
    }

}
