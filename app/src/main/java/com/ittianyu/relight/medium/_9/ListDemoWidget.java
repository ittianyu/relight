package com.ittianyu.relight.medium._9;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import com.ittianyu.relight.widget.RenderItem;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.HorizontalListWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.ListWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateless.StatelessWidget;

public class ListDemoWidget extends StatelessWidget<LinearLayout, LinearWidget> {

    public ListDemoWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected LinearWidget build(Context context) {
        return new LinearWidget(context, lifecycle,
            renderList(false),
            renderList(true)
        ).vertical();
    }

    private Widget renderList(boolean vertical) {
        ListWidget widget = null;
        if (vertical) {
            widget = new ListWidget(context, lifecycle);
        } else {
            widget = new HorizontalListWidget(context, lifecycle);
        }
        return widget
            .scrollBarEnabled(false)
            .renderItem(new RenderItem() {
                @Override
                public Widget render(int index) {
                    return new TextWidget(context, lifecycle)
                        .text(index + "")
                        .gravity(Gravity.CENTER)
                        .width(64.f)
                        .height(64.f)
                        .textSize(18.f)
                        .backgroundColor(getColor(index));
                }

                @Override
                public int size() {
                    return 15;
                }
            });
    }

    private Integer getColor(int index) {
        int position = index % 7;
        switch (position) {
            case 0:
                return Color.RED;
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.CYAN;
            case 5:
                return Color.LTGRAY;
            default:
                return Color.MAGENTA;
        }
    }

}
