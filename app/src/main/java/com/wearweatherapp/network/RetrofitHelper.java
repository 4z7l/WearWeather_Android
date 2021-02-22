package com.wearweatherapp.network;

import com.wearweatherapp.data.model.response.CurrentWeather;
import com.wearweatherapp.data.model.response.FutureWeather;
import com.wearweatherapp.data.remote.WeatherService;

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

    private Retrofit retrofit;
    private final String BASE_URL = "http://api.openweathermap.org/";
    private static final RetrofitHelper INSTANCE = new RetrofitHelper();

    public static RetrofitHelper getInstance() {
        return INSTANCE;
    }

    private RetrofitHelper() {
        buildRetrofit();
    }

    public void buildRetrofit() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpQueryInterceptor())
                .addInterceptor(new HttpLoggingInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getCurrentWeather(Double lat, Double lon, Callback<CurrentWeather> callback) {
        WeatherService service = retrofit.create(WeatherService.class);
        service.getCurrentWeather(lat, lon)
                .enqueue(callback);
    }

    public void getFutureWeather(Double lat, Double lon, Callback<FutureWeather> callback) {
        WeatherService service = retrofit.create(WeatherService.class);
        service.getFutureWeather(lat, lon)
                .enqueue(callback);
    }
}
