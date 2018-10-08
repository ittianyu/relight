package com.ittianyu.relight.b_list

import com.ittianyu.relight.a_hello.UserModel
import com.ittianyu.relight.common.ProjectItem
import java.util.*

object ListModel {
    fun getData() : List<ProjectItem> {
        val count = Random().nextInt(100)
        val list = ArrayList<ProjectItem>(count)

        for (item in 0..count) {
            val id = UserModel.getInstance().randomId()
            val name = UserModel.getInstance().randomName()
            list.add(ProjectItem(name, id))
        }
        return list;
    }
}
