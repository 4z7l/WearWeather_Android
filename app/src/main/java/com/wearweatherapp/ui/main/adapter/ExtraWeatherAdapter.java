package com.wearweatherapp.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wearweatherapp.data.model.domain.ExtraWeather;
import com.wearweatherapp.databinding.ItemExtraWeatherBinding;

import java.util.ArrayList;

public class ExtraWeatherAdapter extends RecyclerView.Adapter<ExtraWeatherAdapter.ViewHolder> {

    private ArrayList<ExtraWeather> data = new ArrayList<>();

    public void setData(ArrayList<ExtraWeather> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemExtraWeatherBinding binding = ItemExtraWeatherBinding.inflate(inflater, parent, false);

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
        ItemExtraWeatherBinding binding;

        public ViewHolder(ItemExtraWeatherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ExtraWeather item) {
            binding.setData(item);
        }
    }
}
