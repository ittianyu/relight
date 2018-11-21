package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public class ImageWidget extends BaseAndroidWidget<ImageView> {
    protected Drawable drawable;
    protected ImageView.ScaleType scaleType;

    public ImageWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public ImageView createView(Context context) {
        return new ImageView(context);
    }

    public ImageWidget imageBitmap(Bitmap bitmap) {
        return imageDrawable(new BitmapDrawable(context.getResources(), bitmap));
    }

    public ImageWidget imageDrawable(Drawable drawable) {
        this.drawable = drawable;
        if (this.drawable != null)
            view.setImageDrawable(this.drawable);
        return this;
    }

    public ImageWidget imageResource(@DrawableRes int res) {
        return imageDrawable(drawable(res));
    }

    protected void initProps() {
        view.setImageResource(android.R.drawable.ic_menu_report_image);
    }

    public ImageWidget scaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        if (this.scaleType != null)
            view.setScaleType(this.scaleType);
        return this;
    }

    public void updateProps(ImageView view) {
        super.updateProps(view);
        scaleType(scaleType);
        imageDrawable(drawable);
    }

}