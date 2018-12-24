package com.ittianyu.relight.medium._8;

import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import com.ittianyu.relight.R;
import com.ittianyu.relight.utils.WidgetInflaterFactory;

public class WidgetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater().cloneInContext(this);
        LayoutInflaterCompat.setFactory2(inflater, new WidgetInflaterFactory());
        View root = inflater.inflate(R.layout.activity_medium_8, null);
        setContentView(root);
        View view = findViewById(R.id.w_1);
        System.out.println(view);
    }
}
