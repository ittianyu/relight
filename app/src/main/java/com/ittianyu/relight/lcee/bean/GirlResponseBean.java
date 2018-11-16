package com.ittianyu.relight.lcee.bean;

import java.util.List;

/**
 * 网络数据实体
 * <p>
 * Created by liyujiang on 2018/11/15 18:11
 */
public class GirlResponseBean extends JavaBean {
    private int displayNum;
    private List<GirlItemBean> data;

    public int getDisplayNum() {
        return displayNum;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public List<GirlItemBean> getData() {
        return data;
    }

    public void setData(List<GirlItemBean> data) {
        this.data = data;
    }

}
