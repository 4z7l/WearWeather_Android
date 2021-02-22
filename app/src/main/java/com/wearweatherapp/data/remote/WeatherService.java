package com.wearweatherapp.data.remote;

import com.wearweatherapp.data.model.response.CurrentWeather;
import com.wearweatherapp.data.model.response.FutureWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/data/2.5/weather")
    Call<CurrentWeather> getCurrentWeather(
            @Query("lat") Double lat,
            @Query("lon") Double lon
    );

    @GET("/data/2.5/onecall")
    Call<FutureWeather> getFutureWeather(
            @Query("lat") Double lat,
            @Query("lon") Double lon
    );
}
