package com.ittianyu.relight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ittianyu.relight.base.BaseTutorialActivity;
import com.ittianyu.relight.medium.MediumTutorialActivity;
import com.ittianyu.relight.lcee.GirlListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BaseTutorial:
                start(BaseTutorialActivity.class);
                break;
            case R.id.MediumTutorial:
                start(MediumTutorialActivity.class);
                break;
            case R.id.LceeWidget:
                start(GirlListActivity.class);
                break;
        }
    }

    private void start(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }
}
