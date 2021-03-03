package com.wearweatherapp.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wearweatherapp.data.model.domain.DailyWeather;
import com.wearweatherapp.databinding.ItemDailyWeatherBinding;

import java.util.ArrayList;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {

    private ArrayList<DailyWeather> data = new ArrayList<>();

    public void setData(ArrayList<DailyWeather> list) {
        this.data = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDailyWeatherBinding binding = ItemDailyWeatherBinding.inflate(inflater, parent, false);

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
        ItemDailyWeatherBinding binding;

        public ViewHolder(ItemDailyWeatherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(DailyWeather item) {
            binding.setData(item);
            Glide.with(binding.ivDaily.getContext())
                    .load("http://openweathermap.org/img/wn/" + item.getIcon() + "@2x.png")
                    .into(binding.ivDaily);
        }

    }

}
