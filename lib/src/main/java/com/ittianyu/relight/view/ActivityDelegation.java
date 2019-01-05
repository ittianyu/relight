package com.ittianyu.relight.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

public interface ActivityDelegation {
    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

    boolean onKeyDown(int keyCode, KeyEvent event);
}
