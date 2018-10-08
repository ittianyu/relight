package com.ittianyu.relight.a_hello;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ittianyu.relight.utils.WidgetUtils;


/**
 * Created by 86839 on 2017/10/4.
 */
public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(WidgetUtils.render(this, UserLayoutStateless.class, UserModel.getInstance().getUser()));
        setContentView(WidgetUtils.render(this, UserLayoutStateful.class));
    }

}
