package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public class ImageWidget extends BaseImageWidget<ImageView, ImageWidget> {
    public ImageWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

}