package com.example.anupam.places.rest;

import com.example.anupam.places.constant.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by anupam on 05-11-2016.
 */
public class ApiClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
