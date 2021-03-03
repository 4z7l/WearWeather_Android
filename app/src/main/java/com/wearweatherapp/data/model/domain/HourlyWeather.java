package com.wearweatherapp.data.model.domain;

public class HourlyWeather {
    private String dt;
    private String temp;
    private String icon;

    public HourlyWeather(String dt, String temp, String icon) {
        this.dt = dt;
        this.temp = temp;
        this.icon = icon;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
