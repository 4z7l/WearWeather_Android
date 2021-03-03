package com.wearweatherapp.data.remote;

import com.wearweatherapp.data.model.response.ResCurrentWeather;
import com.wearweatherapp.data.model.response.ResFutureWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("/data/2.5/weather")
    Call<ResCurrentWeather> getCurrentWeather(
            @Query("lat") Double lat,
            @Query("lon") Double lon
    );

    @GET("/data/2.5/onecall")
    Call<ResFutureWeather> getFutureWeather(
            @Query("lat") Double lat,
            @Query("lon") Double lon
    );
}
