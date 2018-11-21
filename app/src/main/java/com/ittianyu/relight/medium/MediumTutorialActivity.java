package com.ittianyu.relight.medium;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ittianyu.relight.R;

public class MediumTutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium_tutorial);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._1_LceeWidget:
                start(com.ittianyu.relight.medium._1.WidgetActivity.class);
                break;

        }
    }

    private void start(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }
}
