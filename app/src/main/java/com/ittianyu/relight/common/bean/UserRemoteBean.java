package com.ittianyu.relight.common.bean;

/**
 * 类描述
 * <p>
 * Created by liyujiang on 2018/11/15 18:11
 */
public class UserRemoteBean {
    private int code;
    private String message;
    private UserBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserBean getData() {
        return data;
    }

    public void setData(UserBean data) {
        this.data = data;
    }

}
