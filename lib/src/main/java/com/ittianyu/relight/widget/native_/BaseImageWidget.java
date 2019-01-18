package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION_CODES;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.ImageViewCompat;
import android.view.View;
import android.widget.ImageView;

public class BaseImageWidget<V extends ImageView, T extends BaseImageWidget> extends BaseAndroidWidget<V, T> {

    public BaseImageWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected void initProps() {
    }

    public T imageBitmap(Bitmap bitmap) {
        return imageDrawable(new BitmapDrawable(context.getResources(), bitmap));
    }

    public T imageDrawable(Drawable drawable) {
        view.setImageDrawable(drawable);
        return self();
    }

    public T imageResource(@DrawableRes int res) {
        return imageDrawable(drawable(res));
    }

    public T scaleType(ImageView.ScaleType scaleType) {
        view.setScaleType(scaleType);
        return self();
    }

    public T imageTintList(ColorStateList tint) {
        ImageViewCompat.setImageTintList(view, tint);
        return self();
    }
}