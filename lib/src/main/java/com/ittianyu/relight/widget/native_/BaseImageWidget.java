package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

public class BaseImageWidget<V extends ImageView, T extends BaseImageWidget> extends BaseAndroidWidget<V, T> {
    protected Drawable drawable;
    protected ImageView.ScaleType scaleType;

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
        this.drawable = drawable;
        view.setImageDrawable(drawable);
        return self();
    }

    public T imageResource(@DrawableRes int res) {
        return imageDrawable(drawable(res));
    }

    public T scaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        view.setScaleType(this.scaleType);
        return self();
    }

    @Override
    public void updateProps(V view) {
        super.updateProps(view);
        if (this.scaleType != null)
            scaleType(scaleType);
        if (this.drawable != null)
            imageDrawable(drawable);
    }

}