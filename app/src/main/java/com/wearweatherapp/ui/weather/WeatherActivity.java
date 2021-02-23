package com.wearweatherapp.ui.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wearweatherapp.R;
import com.wearweatherapp.data.model.mapper.DailyWeatherItemMapper;
import com.wearweatherapp.data.model.mapper.HourlyWeatherItemMapper;
import com.wearweatherapp.data.model.response.CurrentWeather;
import com.wearweatherapp.data.model.response.FutureWeather;
import com.wearweatherapp.data.model.response.Hourly;
import com.wearweatherapp.databinding.ActivityWeatherBinding;
import com.wearweatherapp.network.RetrofitHelper;
import com.wearweatherapp.ui.dust.DustActivity;
import com.wearweatherapp.ui.news.NewsXMLActivity;
import com.wearweatherapp.ui.settings.SettingsActivity;
import com.wearweatherapp.util.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {
    //current weather
    //http://api.openweathermap.org/data/2.5/weather?appid=944b4ec7c3a10a1bbb4a432d14e6f979&units=metric&id=1835848&lang=kr

    //future
    //http://api.openweathermap.org/data/2.5/onecall?appid=944b4ec7c3a10a1bbb4a432d14e6f979&units=metric&id=1835848&lang=kr

    ActivityWeatherBinding binding;
    DailyWeatherAdapter dailyWeatherAdapter;
    HourlyWeatherAdapter hourlyWeatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather);

        initView();
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
            getCurrentWeather();
            getFutureWeather();

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

        binding.rvDaily.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDaily.setHasFixedSize(true);
        binding.rvDaily.setAdapter(dailyWeatherAdapter);

        binding.rvHourly.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        binding.rvHourly.setHasFixedSize(true);
        binding.rvHourly.setAdapter(hourlyWeatherAdapter);
    }

    private void getCurrentWeather() {
        RetrofitHelper.getInstance().getCurrentWeather(37.5172, 127.0423,
                new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            String str;
                            binding.txtWeatherDescription.setText(response.body().getWeather().get(0).getDescription());
                            str = Math.round(response.body().getMain().getTemp())+getString(R.string.temperature_unit);
                            binding.txtTempNow.setText(str);
                            str = "최고온도 "+Math.round(response.body().getMain().getTempMax())+getString(R.string.temperature_unit);
                            binding.txtTempMax.setText(str);
                            str = "최고온도 "+Math.round(response.body().getMain().getTempMin())+getString(R.string.temperature_unit);
                            binding.txtTempMin.setText(str);

                            str = response.body().getWeather().get(0).getIcon();
                            Glide.with(getBaseContext())
                                    .load("http://openweathermap.org/img/wn/"+str+"@2x.png")
                                    .into(binding.ivWeather);
                        }
                    }
                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {
                        Log.e("SEULGI", "Failed",t);
                    }
                });

    }

    private void getFutureWeather() {
        RetrofitHelper.getInstance().getFutureWeather(37.5172, 127.0423,
                new Callback<FutureWeather>() {
                    @Override
                    public void onResponse(Call<FutureWeather> call, Response<FutureWeather> response) {
                        if(response.isSuccessful() && response.body()!=null){
                            hourlyWeatherAdapter.setData(HourlyWeatherItemMapper.transform(response.body().getHourly()));
                            hourlyWeatherAdapter.notifyDataSetChanged();

                            dailyWeatherAdapter.setData(DailyWeatherItemMapper.transform(response.body().getDaily()));
                            dailyWeatherAdapter.notifyDataSetChanged();

                        }
                    }
                    @Override
                    public void onFailure(Call<FutureWeather> call, Throwable t) {
                        Log.e("SEULGI", "Failed",t);
                    }
                });

    }
}