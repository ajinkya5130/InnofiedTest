/*
 * Copyright (c) 13/10/20 12:25 PM Ajinkya K. Android Dev.
 */

package com.ajinkya.assignmentapp.api_call;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private APIInterface myApi;

    private RetrofitClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.readTimeout(2, TimeUnit.MINUTES);
        httpClient.connectTimeout(2, TimeUnit.MINUTES);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        myApi = retrofit.create(APIInterface.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public APIInterface getMyApi() {
        return myApi;
    }
}
