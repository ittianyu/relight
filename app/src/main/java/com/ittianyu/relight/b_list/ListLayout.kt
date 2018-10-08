package com.ittianyu.relight.b_list

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.graphics.Color
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.ittianyu.relight.R
import com.ittianyu.relight.common.ProjectItem
import com.ittianyu.relight.common.ProjectsAdapter
import com.ittianyu.relight.utils.StateUtils
import com.ittianyu.relight.widget.Widget
import com.ittianyu.relight.widget.native_.*
import com.ittianyu.relight.widget.native_.RelativeWidget.Prop
import com.ittianyu.relight.widget.native_.RelativeWidget.WidgetAndProps
import com.ittianyu.relight.widget.stateful.AsyncState
import com.ittianyu.relight.widget.stateful.LifecycleStatefulWidget

class ListLayout(context: Context, lifecycle: Lifecycle) : LifecycleStatefulWidget<FrameLayout>(context, lifecycle) {
    var data: List<ProjectItem> = ListModel.getData();

    private val tvLeft = 1001
    private val tvCenter = 1002
    private val tvRight = 1003

    override fun createState(context: Context): AsyncState<FrameLayout> {
        val widget = buildFrameWidget(
                buildRecyclerWidget(),
                buildLinearWidget(
                        buildFab(),
                        buildText()
                ),
                buildRelativeWidget(
                        WidgetAndProps(TextWidget(context, lifecycle).text("left").id(tvLeft)),
                        WidgetAndProps(TextWidget(context, lifecycle).text("center").id(tvCenter).marginStart(16.0f).marginEnd(16.0f),
                                Prop(RelativeLayout.END_OF, tvLeft)),
                        WidgetAndProps(TextWidget(context, lifecycle).text("right").id(tvRight),
                                Prop(RelativeLayout.END_OF, tvCenter))
                ).marginStart(10.0f).marginTop(10.0f)
        )
        return StateUtils.create(widget)
    }

    private fun buildFrameWidget(vararg children: Widget<*>): FrameWidget {
        return object : FrameWidget(context, lifecycle,
                *children) {
            override fun initProps() {
                width = matchParent
                height = matchParent
            }
        }
    }

    private fun buildLinearWidget(vararg children: Widget<*>): LinearWidget {
        return object : LinearWidget(context, lifecycle,
                *children) {
            override fun initProps() {
                width = wrapContent
                height = wrapContent
                orientation = vertical
                gravity = Gravity.CENTER_HORIZONTAL
                layoutGravity = Gravity.BOTTOM or Gravity.END
                margin = dp(16)
            }
        }
    }


    private fun buildRelativeWidget(vararg childrenAndProps: RelativeWidget.WidgetAndProps): RelativeWidget {
        return RelativeWidget(context, lifecycle, *childrenAndProps);
    }

    private fun buildRecyclerWidget(): RecyclerWidget {
        return object : RecyclerWidget(context, lifecycle) {
            override fun initProps() {
                width = matchParent;
                height = matchParent;
                margin = dp(16)
                padding = dp(30.5)
                layoutManager = LinearLayoutManager(context)
                adapter = ProjectsAdapter(data)
            }

            override fun bindEvent(view: RecyclerView) {
                (view.adapter as ProjectsAdapter).setOnItemClickListener { v, position ->
                    setState {
                        data = ListModel.getData()
                    }
                }
            }

            override fun updateView(view: RecyclerView) {
                (view.adapter as ProjectsAdapter).data = data
            }
        }
    }

    private fun buildFab(): BaseAndroidWidget<FloatingActionButton> {
        return object : BaseAndroidWidget<FloatingActionButton>(context, lifecycle) {
            override fun initProps() {
                width = wrapContent
                height = wrapContent
            }
        }
    }

    private fun buildText(): TextWidget {
        return object : TextWidget(context, lifecycle) {
            override fun initProps() {
                width = wrapContent
                height = wrapContent
                text = string(R.string.app_name)
                textColor = Color.BLACK
            }
        }
    }

}
