package com.wearweatherapp.ui.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wearweatherapp.data.model.domain.DailyWeatherItem;
import com.wearweatherapp.data.model.domain.HourlyWeatherItem;
import com.wearweatherapp.data.model.response.Daily;
import com.wearweatherapp.databinding.ItemDailyWeatherBinding;
import com.wearweatherapp.databinding.ItemHourlyWeatherBinding;

import java.util.ArrayList;
import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {

    private ArrayList<DailyWeatherItem> data = new ArrayList<>();

    public void setData(ArrayList<DailyWeatherItem> list) {
        this.data = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
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

        void bind(DailyWeatherItem item) {
            binding.setData(item);
            Glide.with(binding.ivDaily.getContext())
                    .load("http://openweathermap.org/img/wn/"+item.getIcon()+"@2x.png")
                    .into(binding.ivDaily);
        }

    }

}
