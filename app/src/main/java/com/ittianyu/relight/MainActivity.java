package com.ittianyu.relight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._1_AndroidWidget:
                start(com.ittianyu.relight._1.WidgetActivity.class);
                break;
            case R.id._2_LifecycleStatefulWidget:
                start(com.ittianyu.relight._2.WidgetActivity.class);
                break;
            case R.id._3_TextWidget:
                start(com.ittianyu.relight._3.WidgetActivity.class);
                break;
            case R.id._4_LinearWidget:
                start(com.ittianyu.relight._4.WidgetActivity.class);
                break;
            case R.id._5_FrameWidget:
                start(com.ittianyu.relight._5.WidgetActivity.class);
                break;

        }
    }

    private void start(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }
}
