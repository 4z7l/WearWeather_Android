package com.wearweatherapp.data.model.domain;

public class ExtraWeatherItem {
    String description;
    String value;

    public ExtraWeatherItem(String description, String value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
