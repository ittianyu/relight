package com.ittianyu.relight.c_lcee

import com.ittianyu.relight.a_hello.UserModel
import com.ittianyu.relight.common.ProjectItem
import java.util.*

object LceeModel {
    fun getData() : List<ProjectItem> {
        Thread.sleep(1000)

        val num = Random().nextInt(4)
        if (num == 0)
            return getEmptyData();
        if (num == 1)
            throw IllegalStateException("network error")

        return getData(50)
    }

    private fun getEmptyData() : List<ProjectItem>{
        return Collections.emptyList();
    }

    private fun getData(count : Int) : List<ProjectItem>{
        val list = ArrayList<ProjectItem>(count)

        for (item in 0..count) {
            val id = UserModel.getInstance().randomId()
            val name = UserModel.getInstance().randomName()
            list.add(ProjectItem(name, id))
        }

        return list;
    }

}
