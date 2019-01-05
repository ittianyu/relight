package com.ittianyu.relight.medium._7;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.ittianyu.relight.utils.WidgetUtils;
import com.ittianyu.relight.view.ActivityDelegationManager;

public class WidgetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = WidgetUtils.render(this, StartActivityWidget.class);
        setContentView(root);

        Intent intent = new Intent();
        intent.putExtra(StartActivityWidget.RESULT_MSG, "hello startActivityForResult");
        setResult(RESULT_OK, intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ActivityDelegationManager.onActivityResult(this, requestCode, resultCode, data);
    }
}
