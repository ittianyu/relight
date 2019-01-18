package com.ittianyu.relight.utils;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ViewUtils {
    public static void setMargin(View v, Integer l, Integer t, Integer r, Integer b) {
        MarginLayoutParams p = getMarginInfo(v);
        if (null == p) {
            return;
        }
        if (l == null)
            l = p.leftMargin;
        if (t == null)
            t = p.topMargin;
        if (r == null)
            r = p.rightMargin;
        if (b == null)
            b = p.bottomMargin;
        p.setMargins(l, t, r, b);
        v.requestLayout();
    }

    public static ViewGroup.MarginLayoutParams getMarginInfo(View v) {
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            return (ViewGroup.MarginLayoutParams) lp;
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

    public static void setLayoutGravity(Toolbar.LayoutParams lp, View view, int layoutGravity) {
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
