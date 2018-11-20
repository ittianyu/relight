package com.ittianyu.relight.base._1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.utils.WidgetUtils;

public class WidgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserBean user = new UserBean(1, "ittianyu");
        View root = WidgetUtils.render(this, UserWidget.class, user);
//        View root = new UserWidget(this, getLifecycle(), user).render(); // this is ok too
        setContentView(root);
    }
}
