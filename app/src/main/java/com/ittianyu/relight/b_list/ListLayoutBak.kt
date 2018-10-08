package com.ittianyu.relight.b_list

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.ittianyu.relight.common.ankoview.recyclerView
import com.ittianyu.relight.c_lcee.LceeModel
import com.ittianyu.relight.common.ProjectItem
import com.ittianyu.relight.common.ProjectsAdapter
import com.ittianyu.relight.utils.StateUtils
import com.ittianyu.relight.widget.native_.AndroidWidget
import com.ittianyu.relight.widget.stateful.AsyncState
import com.ittianyu.relight.widget.stateful.LifecycleStatefulWidget
import org.jetbrains.anko.UI
import org.jetbrains.anko.matchParent

class ListLayoutBak(context: Context, lifecycle: Lifecycle) : LifecycleStatefulWidget<RecyclerView>(context, lifecycle) {
    override fun createState(context: Context): AsyncState<RecyclerView> {
        return StateUtils.create(ListWidget(context, lifecycle))
    }

    inner class ListWidget(context: Context, lifecycle: Lifecycle) : AndroidWidget<RecyclerView>(context, lifecycle) {
        var data: List<ProjectItem> = LceeModel.getData();
        var recyclerView: RecyclerView? = null

        override fun createView(context: Context): RecyclerView {
            return with(context) {
                UI {
                    recyclerView = recyclerView(context) {
                        layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
                        layoutManager = LinearLayoutManager(context)
                        adapter = ProjectsAdapter(data)
                    }
                }
            }.view as RecyclerView
        }

        override fun bindEvent(view: RecyclerView) {
            (view.adapter as ProjectsAdapter).setOnItemClickListener { v, position ->
                setState {
                    data = LceeModel.getData()
                }
            }
        }

        override fun updateView(view: RecyclerView) {
            (recyclerView?.adapter as ProjectsAdapter).data = data
        }
    }
}
