package com.ittianyu.relight.advance._2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.ittianyu.relight.utils.WidgetUtils;

public class WidgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(WidgetUtils.render(this, SetStateAsyncWidget.class));
    }
}
