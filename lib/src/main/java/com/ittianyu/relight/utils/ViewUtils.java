package com.ittianyu.relight.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class ViewUtils {
    public static void setMargin(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static ViewGroup.MarginLayoutParams getMarginInfo(View v) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            return p;
        }
        return null;
    }

    public static void setSize(View view, int width, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            return;
        }
        lp.width = width;
        lp.height = height;
        view.requestLayout();
    }

    public static void setLayoutGravity(FrameLayout.LayoutParams lp, View view, int layoutGravity) {
        lp.gravity = layoutGravity;
        view.requestLayout();
    }

    public static void setLayoutGravity(LinearLayout.LayoutParams lp, View view, int layoutGravity) {
        lp.gravity = layoutGravity;
        view.requestLayout();
    }

    public static void setWeight(LinearLayout.LayoutParams lp, View view, int weight) {
        lp.weight = weight;
        view.requestLayout();
    }

}
