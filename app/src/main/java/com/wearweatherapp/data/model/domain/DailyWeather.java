package com.wearweatherapp.data.model.domain;

public class DailyWeather {
    private String dt;
    private String max_temp;
    private String min_temp;
    private String icon;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public DailyWeather(String dt, String max_temp, String min_temp, String icon) {
        this.dt = dt;
        this.max_temp = max_temp;
        this.min_temp = min_temp;
        this.icon = icon;
    }
}
