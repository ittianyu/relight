package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import com.ittianyu.relight.widget.Widget;

public class HorizontalListWidget extends ListWidget {

    public HorizontalListWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    @Override
    public boolean vertical() {
        return false;
    }
}
