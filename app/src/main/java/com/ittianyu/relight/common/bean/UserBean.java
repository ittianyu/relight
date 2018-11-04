package com.ittianyu.relight.common.bean;

import java.io.Serializable;

public class UserBean implements Serializable {
    private Integer id;
    private String name;

    public UserBean() {
    }

    public UserBean(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void update(UserBean bean) {
        setId(bean.id);
        setName(bean.name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
