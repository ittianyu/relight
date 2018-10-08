package com.ittianyu.relight.common.ankoview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.recyclerView(context : Context) = recyclerView(context) {}
inline fun ViewManager.recyclerView(context : Context, init: RecyclerView.() -> Unit): RecyclerView {
    return ankoView({ RecyclerView(context, null) }, 0) { init() }
}
