package com.ittianyu.relight.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.ittianyu.relight.view.ActivityDelegationManager;

public abstract class WidgetActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityDelegationManager.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (ActivityDelegationManager.onKeyDown(this, keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
