package com.ittianyu.relight.medium._8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ittianyu.relight.widget.native_.ButtonWidget;

public class WidgetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButtonWidget widget = new ButtonWidget(this, null);
        setContentView(widget.render());
    }
}
