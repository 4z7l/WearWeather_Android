package com.wearweatherapp.data.model.mapper;

import com.wearweatherapp.data.model.domain.ExtraWeather;
import com.wearweatherapp.data.model.response.ResCurrentWeather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ExtraWeatherItemMapper {
    public static ArrayList<ExtraWeather> transform(ResCurrentWeather input) {
        ArrayList<ExtraWeather> out = new ArrayList<>();
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("a hh:mm", Locale.KOREA);

        String feels_like = Math.round(input.getMain().getFeelsLike() * 10) + "°C";
        out.add(new ExtraWeather("체감온도", feels_like));

        Double rainValue;
        if (input.getRain() != null) {
            if (input.getRain().get_1h() != null) rainValue = input.getRain().get_1h();
            else if (input.getRain().get_3h() != null) rainValue = input.getRain().get_3h();
            else rainValue = 0.0;
        } else rainValue = 0.0;
        String rain = Math.round(rainValue * 10) + "mm";
        out.add(new ExtraWeather("강수량", rain));

        String pressure = input.getMain().getPressure() + "hPa";
        out.add(new ExtraWeather("기압", pressure));
        String humidity = input.getMain().getHumidity() + "%";
        out.add(new ExtraWeather("습도", humidity));
        String sunrise = sdf.format(new Date(input.getSys().getSunrise() * 1000L));
        out.add(new ExtraWeather("일출", sunrise));
        String sunset = sdf.format(new Date(input.getSys().getSunset() * 1000L));
        out.add(new ExtraWeather("일몰", sunset));

        return out;
    }
}
