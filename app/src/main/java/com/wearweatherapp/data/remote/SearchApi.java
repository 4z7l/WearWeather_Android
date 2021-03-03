package com.wearweatherapp.data.remote;

import com.wearweatherapp.data.model.response.ResSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApi {
    @GET("/req/search")
    Call<ResSearch> searchAddress(
            @Query("query") String query
    );
}
