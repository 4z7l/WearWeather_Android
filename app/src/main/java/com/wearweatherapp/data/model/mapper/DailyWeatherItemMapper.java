package com.wearweatherapp.data.model.mapper;

import com.wearweatherapp.data.model.domain.DailyWeatherItem;
import com.wearweatherapp.data.model.response.Daily;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyWeatherItemMapper {

    public static ArrayList<DailyWeatherItem> transform(List<Daily> input) {
        ArrayList<DailyWeatherItem> out = new ArrayList<>();
        for (Daily item : input) {
            Date date = new Date(item.getDt() * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.KOREA);

            String dt = sdf.format(date);
            String max_temp = Math.round(item.getTemp().getMax()) + "°C";
            String min_temp = Math.round(item.getTemp().getMin()) + "°C";
            String icon = item.getWeather().get(0).getIcon();

            out.add(new DailyWeatherItem(dt, max_temp, min_temp, icon));
        }

        return out;
    }
}
