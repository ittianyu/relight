package com.ittianyu.relight.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.ittianyu.relight.view.ActivityResultDelegation;
import com.ittianyu.relight.view.ActivityResultDelegationManager;

public abstract class Widget<V extends View>
        implements ActivityResultDelegation, WidgetUpdater {
    protected final Context context;

    public Widget(Context context) {
        this.context = context;
    }

    public abstract V render();

    protected void startActivity(Intent intent) {
        startActivity(intent, null);
    }

    protected void startActivity(Intent intent, Bundle options) {
        context.startActivity(intent, options);
    }

    protected void startActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode, null);
    }

    protected void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        startActivityForResult(intent, requestCode, options, this);
    }

    protected void startActivityForResult(Intent intent, int requestCode, Bundle options, ActivityResultDelegation delegation) {
        Activity activity = (Activity) context;
        ActivityResultDelegationManager.register(activity, delegation);
        activity.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    }
}
