package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import com.ittianyu.relight.lib.R;
import com.ittianyu.relight.widget.Widget;

public class ToolbarWidget extends ViewGroupWidget<Toolbar, ToolbarWidget> {

    public ToolbarWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    @Override
    protected void initProps() {
        super.initProps();
        width = matchParent;
    }
}
