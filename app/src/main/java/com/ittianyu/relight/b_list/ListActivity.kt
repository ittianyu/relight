package com.ittianyu.relight.b_list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ListLayout(this, lifecycle).render())
    }
}
