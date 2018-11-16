package com.ittianyu.relight.lcee.bean;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 数据实体
 * <p>
 * Created by liyujiang on 2018/11/16 15:32
 */
public class JavaBean implements Serializable {

    @NonNull
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
