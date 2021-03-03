package com.wearweatherapp.network;

import com.wearweatherapp.data.model.response.ResCurrentWeather;
import com.wearweatherapp.data.model.response.ResFutureWeather;
import com.wearweatherapp.data.model.response.ResSearch;
import com.wearweatherapp.data.remote.SearchApi;
import com.wearweatherapp.data.remote.WeatherApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    //current weather
    //http://api.openweathermap.org/data/2.5/weather?appid=944b4ec7c3a10a1bbb4a432d14e6f979
    // &units=metric&id=1835848&lang=kr

    //future
    //http://api.openweathermap.org/data/2.5/onecall?appid=944b4ec7c3a10a1bbb4a432d14e6f979
    // &units=metric&id=1835848&lang=kr

    private Retrofit weatherRetrofit;
    private Retrofit vworldRetrofit;
    private final String WEATHER_API_BASE_URL = "http://api.openweathermap.org/";
    private final String VWORLD_API_BASE_URL = "http://api.vworld.kr/";
    private static final RetrofitHelper INSTANCE = new RetrofitHelper();

    public static RetrofitHelper getInstance() {
        return INSTANCE;
    }

    private RetrofitHelper() {
        buildRetrofit();
    }

    public void buildRetrofit() {
        OkHttpClient weatherHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new WeatherQueryInterceptor())
                .addInterceptor(new HttpLoggingInterceptor())
                .build();

        OkHttpClient vworldHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new SearchQueryInterceptor())
                .addInterceptor(new HttpLoggingInterceptor())
                .build();

        weatherRetrofit = new Retrofit.Builder()
                .client(weatherHttpClient)
                .baseUrl(WEATHER_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        vworldRetrofit = new Retrofit.Builder()
                .client(vworldHttpClient)
                .baseUrl(VWORLD_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getCurrentWeather(Double lat, Double lon, Callback<ResCurrentWeather> callback) {
        WeatherApi service = weatherRetrofit.create(WeatherApi.class);
        service.getCurrentWeather(lat, lon)
                .enqueue(callback);
    }

    public void getFutureWeather(Double lat, Double lon, Callback<ResFutureWeather> callback) {
        WeatherApi service = weatherRetrofit.create(WeatherApi.class);
        service.getFutureWeather(lat, lon)
                .enqueue(callback);
    }

    public void searchAddress(String query, Callback<ResSearch> callback) {
        SearchApi service = vworldRetrofit.create(SearchApi.class);
        service.searchAddress(query)
                .enqueue(callback);
    }
}
