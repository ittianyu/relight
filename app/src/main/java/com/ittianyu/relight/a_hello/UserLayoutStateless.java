package com.ittianyu.relight.a_hello;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ittianyu.relight.R;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.AndroidWidget;
import com.ittianyu.relight.widget.stateless.LifecycleStatelessWidget;

public class UserLayoutStateless extends LifecycleStatelessWidget<View> {
    private User user;

    public UserLayoutStateless(Context context, Lifecycle lifecycle, User user) {
        super(context, lifecycle);
        this.user = user;
    }

    @Override
    protected Widget<View> build(Context context) {
        return new UserLayout(context, lifecycle, user);
    }

    static class UserLayout extends AndroidWidget<View> {
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
