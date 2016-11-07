package com.example.anupam.places.rest;

import com.example.anupam.places.constant.Constant;
import com.example.anupam.places.response.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by anupam on 05-11-2016.
 */
public interface ApiInterface {
    @GET(Constant.PLACES_URL)
    Call<Result> getPlaces(@Query("location") String location, @Query("radius") Integer radius,@Query("types") String types,@Query("sensor") boolean sensor,@Query("key") String key);
}
