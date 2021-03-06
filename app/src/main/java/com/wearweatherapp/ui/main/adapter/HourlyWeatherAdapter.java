package com.wearweatherapp.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wearweatherapp.data.model.domain.HourlyWeather;
import com.wearweatherapp.databinding.ItemHourlyWeatherBinding;

import java.util.ArrayList;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {

    private ArrayList<HourlyWeather> data = new ArrayList<>();

    public void setData(ArrayList<HourlyWeather> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemHourlyWeatherBinding binding = ItemHourlyWeatherBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemHourlyWeatherBinding binding;

        public ViewHolder(ItemHourlyWeatherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(HourlyWeather item) {
            binding.setData(item);
            Glide.with(binding.ivHourlyWeather.getContext())
                    .load("http://openweathermap.org/img/wn/" + item.getIcon() + "@2x.png")
                    .into(binding.ivHourlyWeather);
        }

    }
}
