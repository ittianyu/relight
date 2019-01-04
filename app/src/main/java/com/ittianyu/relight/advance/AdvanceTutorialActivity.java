package com.ittianyu.relight.advance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.ittianyu.relight.R;
import com.ittianyu.relight.advance._1.activity.WidgetActivity;

public class AdvanceTutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_tutorial);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._1_Navigator:
                start(com.ittianyu.relight.advance._1.WidgetActivity.class);
                break;

        }
    }

    private void start(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }
}
