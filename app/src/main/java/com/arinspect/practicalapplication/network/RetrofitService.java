package com.arinspect.practicalapplication.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//initialize Retrofit for call webservice
public class RetrofitService {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://dl.dropboxusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static <S> S cteateService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
