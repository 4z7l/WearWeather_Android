package com.wearweatherapp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.wearweatherapp.R;
import com.wearweatherapp.ui.weather.WeatherActivity;
import com.wearweatherapp.util.AddressUtil;
import com.wearweatherapp.util.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    private final int LOCATION_REQUEST_CODE = 116;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkPermissions();
    }

    private void checkPermissions() {
        if (checkLocationPermission() && checkNetwork()) {
            startWeatherActivity();
        }
    }

    private boolean checkLocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private boolean checkNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!(cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected())) {
            new AlertDialog.Builder(this)
                    .setTitle("인터넷을 연결해주세요!")
                    .setPositiveButton("확인", (dialogInterface, i) -> finish())
                    .create()
                    .show();
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startWeatherActivity();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("위치 권한을 허용해주세요!")
                        .setPositiveButton("확인", (dialogInterface, i) -> finish())
                        .create()
                        .show();
            }
        }
    }

    private void startWeatherActivity() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission")
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        float newLat = 37.5172f, newLon = 127.0473f;
        float prefLat = PreferenceManager.getFloat(this, "LATITUDE");
        float prefLon = PreferenceManager.getFloat(this, "LONGITUDE");

        Log.e("SEULGI1", "" + prefLat + " " + prefLon + PreferenceManager.getString(this, "CITY"));

        if (prefLat == -1.0 && prefLon == -1.0) {
            if (location != null) {
                newLat = (float) location.getLatitude();
                newLon = (float) location.getLongitude();
            }
            PreferenceManager.setFloat(this, "LATITUDE", newLat);
            PreferenceManager.setFloat(this, "LONGITUDE", newLon);

            String address = AddressUtil.getCurrentAddress(this, newLat, newLon);
            if (address != null) {
                address = AddressUtil.getSigunguFromFullAddress(address);
                PreferenceManager.setString(this, "CITY", address);
            }
        }
        Log.e("SEULGI2", "" + prefLat + " " + prefLon + PreferenceManager.getString(this, "CITY"));

        startActivity(new Intent(this, WeatherActivity.class));
        finish();
    }

}