package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.support.v7.widget.LinearLayoutCompat.DividerMode;
import com.ittianyu.relight.widget.RenderItem;
import com.ittianyu.relight.widget.Widget;

public class ListWidget extends FrameWidget {
    private LinearWidget linearWidget;
    private RenderItem renderItem;

    public ListWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    public boolean vertical() {
        return true;
    }

    @Override
    public FrameLayout createView(Context context) {
        Widget<? extends FrameLayout> root = null;
        linearWidget = new LinearWidget(context, lifecycle)
            .widthMatchAndHeightWrap()
            .orientation(vertical() ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL);
        if (vertical()) {
            root = new ScrollWidget(context, lifecycle, linearWidget);
        } else {
            root = new HorizontalScrollWidget(context, lifecycle, linearWidget);
        }
        return root.render();
    }

    public ListWidget scrollBarEnabled(boolean enabled) {
        if (vertical()) {
            verticalScrollBarEnabled(enabled);
        } else {
            horizontalScrollBarEnabled(enabled);
        }
        return this;
    }

    public ListWidget divider(Drawable divider) {
        linearWidget.dividerDrawable(divider);
        return this;
    }

    public ListWidget showDividers(@DividerMode int showDividers) {
        linearWidget.showDividers(showDividers);
        return this;
    }

    public ListWidget renderItem(RenderItem renderItem) {
        this.renderItem = renderItem;
        return this;
    }

    @Override
    public void initView(FrameLayout view) {
        super.initView(view);
        if (renderItem != null) {
            for (int i = 0; i < renderItem.size(); i++) {
                Widget item = renderItem.render(i);
                addChild(item);
            }
        }
    }

    @Override
    public FrameWidget addChild(Widget widget, boolean updateProps) {
        linearWidget.addChild(widget, updateProps);
        return this;
    }
}
