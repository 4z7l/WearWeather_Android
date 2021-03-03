package com.wearweatherapp.ui.weather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.wearweatherapp.R;
import com.wearweatherapp.data.model.mapper.DailyWeatherItemMapper;
import com.wearweatherapp.data.model.mapper.ExtraWeatherItemMapper;
import com.wearweatherapp.data.model.mapper.HourlyWeatherItemMapper;
import com.wearweatherapp.data.model.response.ResCurrentWeather;
import com.wearweatherapp.data.model.response.ResFutureWeather;
import com.wearweatherapp.databinding.ActivityWeatherBinding;
import com.wearweatherapp.network.RetrofitHelper;
import com.wearweatherapp.ui.dust.DustActivity;
import com.wearweatherapp.ui.news.NewsXMLActivity;
import com.wearweatherapp.ui.settings.SettingsActivity;
import com.wearweatherapp.util.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    private ActivityWeatherBinding binding;
    private DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter();
    private HourlyWeatherAdapter hourlyWeatherAdapter = new HourlyWeatherAdapter();
    private ExtraWeatherAdapter extraWeatherAdapter = new ExtraWeatherAdapter();

    private double latitude = 37.5172, longitude = 127.0423;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather);

        initView();
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        latitude = PreferenceManager.getFloat(this, "LATITUDE");
        longitude = PreferenceManager.getFloat(this, "LONGITUDE");
        city = PreferenceManager.getString(this, "CITY");

        binding.txtRegion.setText(city);
        getCurrentWeather();
        getFutureWeather();
    }

    private void initView() {
        setBackgroundByTime();
        initSwipeLayout();
        initDrawerLayout();
        initRecyclerView();
    }

    private void setBackgroundByTime() {
        Date date = new Date(System.currentTimeMillis());
        String hourText = new SimpleDateFormat("HH", Locale.KOREA).format(date);
        int time = Integer.parseInt(hourText);
        if (time >= 0 && time < 6) {
            binding.slWeather.setBackgroundResource(R.drawable.sunny_night_background);
        } else if (time >= 6 && time < 15) {
            binding.slWeather.setBackgroundResource(R.drawable.sunny_afternoon_background);
        } else if (time >= 15 && time < 20) {
            binding.slWeather.setBackgroundResource(R.drawable.sunny_sunset_background);
        } else if (time >= 20 && time < 24) {
            binding.slWeather.setBackgroundResource(R.drawable.sunny_night_background);
        }
    }

    private void initSwipeLayout() {
        binding.slWeather.setOnRefreshListener(() -> {
            /* 새로고침 시 수행될 코드 */
            setBackgroundByTime();
            getData();

            /* 새로고침 완료 */
            binding.slWeather.setRefreshing(false);
        });
    }

    private void initDrawerLayout() {
        binding.btnDrawer.setOnClickListener(v -> {
                    if (!binding.dlWeather.isDrawerOpen(Gravity.RIGHT)) {
                        binding.dlWeather.openDrawer(Gravity.RIGHT);
                    } else {
                        binding.dlWeather.closeDrawer(Gravity.RIGHT);
                    }
                }
        );
        binding.nvDrawer.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_dust) {
                startActivity(new Intent(getBaseContext(), DustActivity.class)
                        .putExtra("city", PreferenceManager.getString(getBaseContext(), "CITY")));
            } else if (itemId == R.id.nav_clothing) {
                Toast.makeText(this, "서비스 준비중입니다", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_news) {
                startActivity(new Intent(getBaseContext(), NewsXMLActivity.class));
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(getBaseContext(), SettingsActivity.class));
            }
            binding.dlWeather.closeDrawer(Gravity.RIGHT);
            return false;
        });

    }

    private void initRecyclerView() {
        dailyWeatherAdapter = new DailyWeatherAdapter();
        hourlyWeatherAdapter = new HourlyWeatherAdapter();
        extraWeatherAdapter = new ExtraWeatherAdapter();

        binding.rvDaily.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDaily.setHasFixedSize(true);
        binding.rvDaily.setAdapter(dailyWeatherAdapter);

        binding.rvHourly.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvHourly.setHasFixedSize(true);
        binding.rvHourly.setAdapter(hourlyWeatherAdapter);

        binding.rvExtra.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvExtra.setHasFixedSize(true);
        binding.rvExtra.setAdapter(extraWeatherAdapter);
    }

    private void getCurrentWeather() {
        RetrofitHelper.getInstance().getCurrentWeather(latitude, longitude,
                new Callback<ResCurrentWeather>() {
                    @Override
                    public void onResponse(Call<ResCurrentWeather> call, Response<ResCurrentWeather> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String str;
                            binding.txtWeatherDescription.setText(response.body().getWeather().get(0).getDescription());
                            str = Math.round(response.body().getMain().getTemp()) + getString(R.string.temperature_unit);
                            binding.txtTempNow.setText(str);
                            str = "최고온도 " + Math.round(response.body().getMain().getTempMax()) + getString(R.string.temperature_unit);
                            binding.txtTempMax.setText(str);
                            str = "최고온도 " + Math.round(response.body().getMain().getTempMin()) + getString(R.string.temperature_unit);
                            binding.txtTempMin.setText(str);

                            str = response.body().getWeather().get(0).getIcon();
                            Glide.with(getBaseContext())
                                    .load("http://openweathermap.org/img/wn/" + str + "@2x.png")
                                    .into(binding.ivWeather);

                            extraWeatherAdapter.setData(ExtraWeatherItemMapper.transform(response.body()));
                            extraWeatherAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResCurrentWeather> call, Throwable t) {
                        Log.e(WeatherActivity.class.getSimpleName(), "getCurrentWeather", t);
                    }
                });

    }

    private void getFutureWeather() {
        RetrofitHelper.getInstance().getFutureWeather(latitude, longitude,
                new Callback<ResFutureWeather>() {
                    @Override
                    public void onResponse(Call<ResFutureWeather> call, Response<ResFutureWeather> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            hourlyWeatherAdapter.setData(HourlyWeatherItemMapper.transform(response.body().getHourly()));
                            hourlyWeatherAdapter.notifyDataSetChanged();

                            dailyWeatherAdapter.setData(DailyWeatherItemMapper.transform(response.body().getDaily()));
                            dailyWeatherAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResFutureWeather> call, Throwable t) {
                        Log.e(WeatherActivity.class.getSimpleName(), "getFutureWeather", t);
                    }
                });

    }
}