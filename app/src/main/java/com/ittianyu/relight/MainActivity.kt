package com.ittianyu.relight

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ittianyu.relight.a_hello.UserActivity
import com.ittianyu.relight.b_list.ListActivity
import com.ittianyu.relight.c_lcee.LceeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.a_hello -> {
                startActivity(Intent(this, UserActivity::class.java))
            }
            R.id.b_list -> {
                startActivity(Intent(this, ListActivity::class.java))
            }
            R.id.c_lcee -> {
                startActivity(Intent(this, LceeActivity::class.java))
            }
        }
    }
}
