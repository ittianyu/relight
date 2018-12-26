package com.ittianyu.relight.convertor;

public abstract class JsonConvertor {
    private static JsonConvertor convertor;

    public static void setInstance(JsonConvertor convertor) {
        JsonConvertor.convertor = convertor;
    }

    public static JsonConvertor getInstance() {
        if (null == convertor) {
            try {
                convertor = new GsonConvertor();
            } catch (Throwable e) {
                throw new UninitException("please init before use. You can choose to add gson lib or set a custom JsonConvertor by call 'JsonConvertor.setInstance(instance);'", e);
            }
        }
        return convertor;
    }

    public abstract String toJson(Object obj);

    public abstract <T> T fromJson(String json, Class<T> beanClass);

    public static class UninitException extends RuntimeException {

        public UninitException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
