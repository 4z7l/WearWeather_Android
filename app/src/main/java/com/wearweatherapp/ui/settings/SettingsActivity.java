package com.wearweatherapp.ui.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.wearweatherapp.databinding.ActivitySettingsBinding;
import com.wearweatherapp.util.AddressParsingUtil;
import com.wearweatherapp.util.GpsTracker;
import com.wearweatherapp.util.PreferenceManager;
import com.wearweatherapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;

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
                    Log.e("SEULGI", getCurrentAddress(location.getLatitude(), location.getLongitude()));
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

    public String getCurrentAddress( double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.KOREA);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude,longitude,7);
        } catch (IOException ioException) {
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }

}