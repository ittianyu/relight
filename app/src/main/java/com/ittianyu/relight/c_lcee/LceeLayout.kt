package com.ittianyu.relight.c_lcee

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import com.ittianyu.relight.common.ProjectItem
import com.ittianyu.relight.common.ProjectsAdapter
import com.ittianyu.relight.widget.Widget
import com.ittianyu.relight.widget.atomic.BaseAndroidWidget
import com.ittianyu.relight.widget.atomic.FrameWidget
import com.ittianyu.relight.widget.atomic.RecyclerWidget
import com.ittianyu.relight.widget.stateful.lcee.CommonEmptyWidget
import com.ittianyu.relight.widget.stateful.lcee.CommonLoadingWidget
import com.ittianyu.relight.widget.stateful.lcee.LceeWidget
import com.ittianyu.relight.widget.stateful.lcee.Status
import java.util.*

class LceeLayout(context: Context, lifecycle: Lifecycle) : LceeWidget(context, lifecycle) {
    var data: List<ProjectItem> = Collections.emptyList();

    val reload: View.OnClickListener = View.OnClickListener {
        reload()
    }

    override fun renderLoading(): Widget<*> {
        return CommonLoadingWidget(context, lifecycle)
    }

    override fun renderContent(): Widget<*> {
        return FrameWidget(context, lifecycle,
                buildRecyclerWidget(),
                buildFab()
        ).matchParent()
    }

    override fun renderEmpty(): Widget<*> {
        return CommonEmptyWidget(context, lifecycle, "No data. Click to reload", reload)
    }

    override fun renderError(): Widget<*> {
        return CommonEmptyWidget(context, lifecycle, "Network error. Click to reload", reload)
    }

    private fun buildRecyclerWidget(): RecyclerWidget {
        return object : RecyclerWidget(context, lifecycle) {
            override fun initProps() {
                width = matchParent;
                height = matchParent;
                padding = dp(16)
                layoutManager = LinearLayoutManager(context)
                adapter = ProjectsAdapter(data)
            }

            override fun updateView(view: RecyclerView) {
                (adapter as ProjectsAdapter).data = data
            }
        }
    }

    private fun buildFab(): BaseAndroidWidget<FloatingActionButton> {
        return object : BaseAndroidWidget<FloatingActionButton>(context, lifecycle) {
            override fun initProps() {
                layoutGravity = Gravity.END or Gravity.BOTTOM
                margin = dp(16)
                wrapContent()
            }
        }.setOnClickListener(reload)
    }

    override fun onLoadData(): Status {
        data = LceeModel.getData()
        if (data.isEmpty())
            return Status.Empty;
        return Status.Content;
    }
}
