package com.ittianyu.relight.widget.native_;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ittianyu.relight.widget.ContainerWidget;
import com.ittianyu.relight.widget.Widget;

public class RelativeWidget extends ViewGroupWidget<RelativeLayout, RelativeWidget> {
    protected WidgetAndProps[] childrenAndProps;

    public RelativeWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    public RelativeWidget(Context context, Lifecycle lifecycle, Widget... children) {
        super(context, lifecycle, children);
    }

    public RelativeWidget(Context context, Lifecycle lifecycle, WidgetAndProps... childrenAndProps) {
        super(context, lifecycle, getChildren(childrenAndProps));
        this.childrenAndProps = childrenAndProps;
    }

    @Override
    public RelativeLayout createView(Context context) {
        return new RelativeLayout(context);
    }

    @Override
    public void updateProps(RelativeLayout view) {
        super.updateProps(view);

        if (null != childrenAndProps) {
            for (WidgetAndProps widgetAndProps: childrenAndProps) {
                Widget widget = widgetAndProps.widget;
                Prop[] props = widgetAndProps.props;
                if (null == props || props.length == 0)
                    continue;

                View child = widget.render();
                ViewGroup.LayoutParams lp = child.getLayoutParams();
                if (!(lp instanceof RelativeLayout.LayoutParams))
                    continue;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) lp;
                for (Prop prop : props) {
                    params.addRule(prop.type, prop.id);
                }
            }
            view.requestLayout();
            return;
        }

        for (Widget widget : children) {
            while (widget instanceof ContainerWidget) {
                widget = ((ContainerWidget) widget).getInnerWidget();
            }
            if (widget instanceof BaseAndroidWidget) {
                ((BaseAndroidWidget) widget).mergeLayoutParams();
            }
        }
    }

    protected static Widget[] getChildren(WidgetAndProps[] childrenAndProps) {
        Widget[] widgets = new Widget[childrenAndProps.length];
        for (int i = 0; i < widgets.length; i++){
            WidgetAndProps widgetAndProps = childrenAndProps[i];
            widgets[i] = widgetAndProps.widget;
        }
        return widgets;
    }

    /**
     * type : RelativeLayout.ALIGN_PARENT_LEFT ...
     * id : view id or Prop.TRUE or Prop.FALSE
     */
    public static class Prop {
        public static final int TRUE = RelativeLayout.TRUE;
        public static final int FALSE = 0;

        public int type;
        public int id;

        public Prop(int type, int id) {
            this.type = type;
            this.id = id;
        }
    }

    public static class WidgetAndProps {
        public Widget widget;
        public Prop[] props;

        public WidgetAndProps(Widget widget, Prop... props) {
            this.widget = widget;
            this.props = props;
        }
    }

}
