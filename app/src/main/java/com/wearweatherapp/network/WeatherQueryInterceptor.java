package com.wearweatherapp.network;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherQueryInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        HttpUrl originalHttpUrl = chain.request().url();
        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("appid", "944b4ec7c3a10a1bbb4a432d14e6f979")
                .addQueryParameter("units", "metric")
                .addQueryParameter("lang", "kr")
                .build();

        Request.Builder requestBuilder = chain.request().newBuilder().url(url);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
