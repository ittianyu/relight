package com.ittianyu.relight.lcee.bean;

import java.util.List;

/**
 * 网络数据实体
 * <p>
 * Created by liyujiang on 2018/11/15 18:11
 */
public class GirlResponseBean extends JavaBean {
    private boolean error;
    private List<GirlItemBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GirlItemBean> getResults() {
        return results;
    }

    public void setResults(List<GirlItemBean> results) {
        this.results = results;
    }

}
