package com.cognitev.base.repo.cloud;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eslam Hussein on 10/11/17.
 */

public class BaseCloudRepo {


    private Map<Class<?>, Object> servicesMap;

    public BaseCloudRepo() {
        servicesMap = new HashMap<>();
    }

    protected <T> T create(Class<T> clazz) {
        T service;
        if (servicesMap.containsKey(clazz)) {
            service = (T) servicesMap.get(clazz);
        } else {
            service = retrofit().create(clazz);
            servicesMap.put(clazz, service);
        }
        return service;
    }


    private Retrofit retrofit() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(CloudConfig.BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
