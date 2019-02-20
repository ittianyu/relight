package com.ittianyu.relight.base._3;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.ittianyu.relight.utils.ViewUtils;

public class WidgetActivity extends AppCompatActivity {
    private static final int ID_FL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout fl = new FrameLayout(this);
        fl.setId(ID_FL);
        ViewUtils.setSize(fl, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        setContentView(fl);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(ID_FL, new WidgetFragment())
                .commit();
    }
}
