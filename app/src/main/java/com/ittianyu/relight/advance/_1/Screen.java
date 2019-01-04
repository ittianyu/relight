package com.ittianyu.relight.advance._1;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import com.ittianyu.relight.R;
import com.ittianyu.relight.advance._1.RouterConfig.Advance1;
import com.ittianyu.relight.widget.native_.ButtonWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.native_.ToolbarWidget;
import com.ittianyu.relight.widget.stateful.navigator.Navigator;

public class Screen extends LinearWidget implements OnClickListener {

    private static final int ID_BW_PUSH_FIRST = 1;
    private static final int ID_BW_PUSH_SECOND = 2;
    private static final int ID_BW_POP = 3;

    public Screen(Context context, Lifecycle lifecycle, String text) {
        super(context, lifecycle);
        this.addChildren(
            new ToolbarWidget(context, lifecycle).backgroundResource(R.color.colorPrimary).marginBottom(16.0f),
            new ButtonWidget(context, lifecycle).id(ID_BW_PUSH_FIRST).text("push first").onClickListener(this),
            new ButtonWidget(context, lifecycle).id(ID_BW_PUSH_SECOND).text("push second").onClickListener(this),
            new ButtonWidget(context, lifecycle).id(ID_BW_POP).text("pop").onClickListener(this),
            new TextWidget(context, lifecycle).text(text).marginTop(50.0f)
        );
    }

    @Override
    protected void initProps() {
        super.initProps();
        matchParent()
            .gravity(Gravity.CENTER_HORIZONTAL)
            .backgroundColor(Color.WHITE)
            .orientation(vertical);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case ID_BW_PUSH_FIRST:
                Navigator.push(Advance1.name, Advance1.firstScreen);
                break;
            case ID_BW_PUSH_SECOND:
                Navigator.push(Advance1.name, Advance1.secondScreen);
                break;
            case ID_BW_POP:
                Navigator.pop(Advance1.name);
                break;
        }
    }
}
