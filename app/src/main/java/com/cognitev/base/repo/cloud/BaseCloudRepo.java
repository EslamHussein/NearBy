package com.cognitev.base.repo.cloud;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
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

    protected <T> T execute(Call<T> call) throws Throwable {
        Response<T> response = call.execute();

        if (!response.isSuccessful()) {

//            Logger.e("Cloud exp : ", JsonUtil.objectToString(response));

//            throw new LaCaseException();  // TODO: HTTP failure exception

        }
//        Logger.e("Cloud response : ", response.body());

        return response.body();
    }

    private Retrofit retrofit() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(CloudConfig.BASE_URL).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
