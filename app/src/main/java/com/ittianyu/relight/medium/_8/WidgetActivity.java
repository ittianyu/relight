package com.ittianyu.relight.medium._8;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.ittianyu.relight.R;
import com.ittianyu.relight.utils.WidgetInflaterFactory;
import com.ittianyu.relight.view.ActivityDelegationManager;
import com.ittianyu.relight.widget.Widget;

public class WidgetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = WidgetInflaterFactory.getLayoutInflater(this);
        View root = inflater.inflate(R.layout.activity_medium_8, null);
        setContentView(root);
        View view = findViewById(R.id.w_1);
        System.out.println(view);
        Widget widget = (Widget) view.getTag();
        System.out.println(widget);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityDelegationManager.onActivityResult(this, requestCode, resultCode, data);
    }
    
}
