package com.wearweatherapp.ui.settings;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wearweatherapp.R;
import com.wearweatherapp.data.model.domain.City;
import com.wearweatherapp.data.model.response.ResSearch;
import com.wearweatherapp.data.model.response.address.Item;
import com.wearweatherapp.databinding.ActivitySearchBinding;
import com.wearweatherapp.network.RetrofitHelper;
import com.wearweatherapp.ui.settings.adapter.SearchResultAdapter;
import com.wearweatherapp.util.AddressUtil;
import com.wearweatherapp.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private SearchResultAdapter searchResultAdapter = new SearchResultAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        initRecyclerView();
        initSearchView();
    }

    private void initRecyclerView() {
        searchResultAdapter.setOnItemClickListener(city -> {
            Log.e("클릭", "" + city.getLatitude() + " " + city.getLongitude());
            PreferenceManager.setFloat(getApplicationContext(), "LATITUDE", (float) city.getLatitude());
            PreferenceManager.setFloat(getApplicationContext(), "LONGITUDE", (float) city.getLongitude());
            PreferenceManager.setString(getApplicationContext(), "CITY", city.getSigungu());
            Toast.makeText(getApplicationContext(), "주소가 " + city.getSigungu() + "로 설정되었습니다", Toast.LENGTH_SHORT).show();
            finish();
        });
        binding.rvSearch.setLayoutManager(new LinearLayoutManager(this));
        binding.rvSearch.setAdapter(searchResultAdapter);
    }

    private void initSearchView() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAddress(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void searchAddress(String query) {
        RetrofitHelper.getInstance().searchAddress(query, new Callback<ResSearch>() {
            @Override
            public void onResponse(Call<ResSearch> call, retrofit2.Response<ResSearch> response) {
                Log.e("search", response.toString());
                if (response.isSuccessful()) {
                    if (response.body().getResponse().getStatus().equals("OK")) {
                        List<Item> items = response.body().getResponse().getResult().getItems();
                        ArrayList<City> cities = new ArrayList<>();

                        for (Item item : items) {
                            String address = item.getAddress().getRoad();
                            String sigungu = AddressUtil.getSigunguFromVWorldAddress(address);
                            double lat = Double.parseDouble(item.getPoint().getY());
                            double lon = Double.parseDouble(item.getPoint().getX());
                            cities.add(new City(lat, lon, address, sigungu));
                            Log.e("SEULGI", "" + lat + " " + lon);
                        }
                        searchResultAdapter.setData(cities);
                        binding.rvSearch.setAdapter(searchResultAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResSearch> call, Throwable t) {
                Log.e(SearchActivity.class.getSimpleName(), "onFailure", t);
            }
        });
    }

}