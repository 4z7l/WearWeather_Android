package com.wearweatherapp.data.model.mapper;

import com.wearweatherapp.data.model.domain.HourlyWeatherItem;
import com.wearweatherapp.data.model.response.Hourly;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HourlyWeatherItemMapper {

    public static ArrayList<HourlyWeatherItem> transform(List<Hourly> input) {
        ArrayList<HourlyWeatherItem> out = new ArrayList<HourlyWeatherItem>();
        for (Hourly item : input) {
            Date date = new java.util.Date(item.getDt() * 1000L);
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("a h" + "시", Locale.KOREA);

            String dt = sdf.format(date);
            String temp = Math.round(item.getTemp()) + "°C";
            String icon = item.getWeather().get(0).getIcon();

            out.add(new HourlyWeatherItem(dt, temp, icon));
        }

        return out;
    }
}
