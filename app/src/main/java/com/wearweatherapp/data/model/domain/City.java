package com.wearweatherapp.data.model.domain;

public class City {
    private double latitude;
    private double longitude;
    private String fullAddress;
    private String sigungu;

    public City(double latitude, double longitude, String fullAddress, String sigungu) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.fullAddress = fullAddress;
        this.sigungu = sigungu;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getSigungu() {
        return sigungu;
    }

    public void setSigungu(String sigungu) {
        this.sigungu = sigungu;
    }
}
