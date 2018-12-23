package com.ittianyu.relight.convertor;

public abstract class JsonConvertor {
    private static JsonConvertor convertor;

    public static void setInstance(JsonConvertor convertor) {
        JsonConvertor.convertor = convertor;
    }

    public static JsonConvertor getInstance() {
        if (null == convertor) {
            convertor = new GsonConvertor();
        }
        return convertor;
    }

    public abstract String toJson(Object obj);

    public abstract <T> T fromJson(String json, Class<T> beanClass);
}
