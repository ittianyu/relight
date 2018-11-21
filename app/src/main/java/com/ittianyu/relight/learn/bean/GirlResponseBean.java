package com.ittianyu.relight.learn.bean;

import java.util.List;

/**
 * 数据实体
 * <p>
 * Created by liyujiang on 2018/11/15 18:11
 */
public class GirlResponseBean extends JavaBean {
    private boolean error;
    private List<GirlItemBean> results;
    private String errorReason;

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

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public String getErrorReason() {
        return errorReason;
    }

}
