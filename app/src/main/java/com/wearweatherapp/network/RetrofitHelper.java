package com.wearweatherapp.network;

import com.wearweatherapp.data.model.response.CurrentWeather;
import com.wearweatherapp.data.model.response.FutureWeather;
import com.wearweatherapp.data.remote.WeatherService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpQueryInterceptor())
                .addInterceptor(new HttpLoggingInterceptor())
                .build();

        weatherRetrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(WEATHER_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OkHttpClient httpClient2 = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        HttpUrl originalHttpUrl = chain.request().url();
                        HttpUrl url = originalHttpUrl.newBuilder()
                                .addQueryParameter("service", "address")
                                .addQueryParameter("request", "getAddress")
                                .addQueryParameter("type", "road")
                                .addQueryParameter("key", "2566C643-E5EC-317E-BBAB-B6064E98ACC2")
                                .build();

                        //service=address&request=getAddress&point=126.978275264,37.566642192&type=road&key=2566C643-E5EC-317E-BBAB-B6064E98ACC2
                        Request.Builder requestBuilder = chain.request().newBuilder().url(url);
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(new HttpLoggingInterceptor())
                .build();


        vworldRetrofit = new Retrofit.Builder()
                .client(httpClient2)
                .baseUrl(VWORLD_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void getCurrentWeather(Double lat, Double lon, Callback<CurrentWeather> callback) {
        WeatherService service = weatherRetrofit.create(WeatherService.class);
        service.getCurrentWeather(lat, lon)
                .enqueue(callback);
    }

    public void getFutureWeather(Double lat, Double lon, Callback<FutureWeather> callback) {
        WeatherService service = weatherRetrofit.create(WeatherService.class);
        service.getFutureWeather(lat, lon)
                .enqueue(callback);
    }

}
