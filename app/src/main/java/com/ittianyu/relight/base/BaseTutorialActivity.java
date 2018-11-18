package com.ittianyu.relight.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ittianyu.relight.R;

public class BaseTutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_tutorial);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._1_AndroidWidget:
                start(com.ittianyu.relight.base._1.WidgetActivity.class);
                break;
            case R.id._2_LifecycleStatefulWidget:
                start(com.ittianyu.relight.base._2.WidgetActivity.class);
                break;
            case R.id._3_TextWidget:
                start(com.ittianyu.relight.base._3.WidgetActivity.class);
                break;
            case R.id._4_LinearWidget:
                start(com.ittianyu.relight.base._4.WidgetActivity.class);
                break;
            case R.id._5_FrameWidget:
                start(com.ittianyu.relight.base._5.WidgetActivity.class);
                break;
            case R.id._6_RelativeWidget:
                start(com.ittianyu.relight.base._6.WidgetActivity.class);
                break;
            case R.id._7_setStateAsync:
                start(com.ittianyu.relight.base._7.WidgetActivity.class);
                break;

        }
    }

    private void start(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }
}
