package com.ittianyu.relight.convertor;

import com.google.gson.Gson;

public class GsonConvertor extends JsonConvertor {
    private static final Gson gson = new Gson();

    @Override
    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <T> T fromJson(String json, Class<T> beanClass) {
        return gson.fromJson(json, beanClass);
    }
}
