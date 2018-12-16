package com.ittianyu.relight.view;

import android.content.Intent;
import android.support.annotation.Nullable;

public interface ActivityResultDelegation {
    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

}
