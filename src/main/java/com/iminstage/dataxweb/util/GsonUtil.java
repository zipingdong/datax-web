package com.iminstage.dataxweb.util;

import com.google.gson.Gson;

public class GsonUtil {

    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> T fromJson(Class<T> classname, String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, classname);
    }
}
