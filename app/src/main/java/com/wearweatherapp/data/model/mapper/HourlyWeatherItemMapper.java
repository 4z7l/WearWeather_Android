package com.wearweatherapp.data.model.mapper;

import com.wearweatherapp.data.model.domain.HourlyWeather;
import com.wearweatherapp.data.model.response.weather.Hourly;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HourlyWeatherItemMapper {

    public static ArrayList<HourlyWeather> transform(List<Hourly> input) {
        ArrayList<HourlyWeather> out = new ArrayList<HourlyWeather>();
        for (Hourly item : input) {
            Date date = new java.util.Date(item.getDt() * 1000L);
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("a h" + "시", Locale.KOREA);

            String dt = sdf.format(date);
            String temp = Math.round(item.getTemp()) + "°C";
            String icon = item.getWeather().get(0).getIcon();

            out.add(new HourlyWeather(dt, temp, icon));
        }

        return out;
    }
}
