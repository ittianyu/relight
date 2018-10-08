package com.ittianyu.relight.c_lcee

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class LceeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LceeLayout(this, lifecycle).render())
    }
}
