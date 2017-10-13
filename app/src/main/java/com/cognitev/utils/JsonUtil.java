package com.cognitev.utils;


import com.google.gson.Gson;

/**
 * Created by Eslam Hussein on a10/30/2016.
 */

public class JsonUtil {

    public static <T> T parseJson(String jsonString, Class<T> clazz) throws Exception {

        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, clazz);

        } catch (Exception e) {
            throw e;
        }

    }

    public static String objectToString(Object clazz) {
        Gson gson = new Gson();
        return gson.toJson(clazz);

    }
}

