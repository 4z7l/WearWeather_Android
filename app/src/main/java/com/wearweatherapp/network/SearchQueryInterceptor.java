package com.wearweatherapp.network;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SearchQueryInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        HttpUrl originalHttpUrl = chain.request().url();
        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("service", "search")
                .addQueryParameter("request", "search")
                .addQueryParameter("type", "address")
                .addQueryParameter("category", "road")
                .addQueryParameter("size", "100")
                .addQueryParameter("key", "2566C643-E5EC-317E-BBAB-B6064E98ACC2")
                .build();

        Request.Builder requestBuilder = chain.request().newBuilder().url(url);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
