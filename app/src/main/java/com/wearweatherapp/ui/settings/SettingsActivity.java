package com.wearweatherapp.ui.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.wearweatherapp.R;
import com.wearweatherapp.databinding.ActivitySettingsBinding;
import com.wearweatherapp.util.AddressUtil;
import com.wearweatherapp.util.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        initView();
    }

    @SuppressLint("MissingPermission")
    private void initView() {
        binding.flSearch.setOnClickListener(view -> {
            startActivity(new Intent(getBaseContext(), SearchActivity.class));
        });

        binding.flRedirect.setOnClickListener(view -> {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    String address = AddressUtil.getCurrentAddress(getBaseContext(), location.getLatitude(), location.getLongitude());
                    if (address != null) {
                        address = AddressUtil.getSigunguFromFullAddress(address);
                        PreferenceManager.setString(getBaseContext(), "CITY", address);
                        PreferenceManager.setFloat(getBaseContext(), "LATITUDE", (float) location.getLatitude());
                        PreferenceManager.setFloat(getBaseContext(), "LONGITUDE", (float) location.getLongitude());
                        Toast.makeText(getBaseContext(), address + "로 설정되었습니다", Toast.LENGTH_SHORT).show();
                    }
                    lm.removeUpdates(this);
                }
                @Override
                public void onProviderEnabled(@NonNull String provider) {

                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }
            });
        });

        binding.toolbar.setNavigationOnClickListener( view -> finish());

        binding.flOpensource.setOnClickListener(view -> {
            startActivity(new Intent(getBaseContext(), OpenSourceActivity.class));
        });
    }

}