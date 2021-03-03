package com.wearweatherapp.ui.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.wearweatherapp.R;
import com.wearweatherapp.databinding.ActivityOpenSourceBinding;

public class OpenSourceActivity extends AppCompatActivity {

    private ActivityOpenSourceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_open_source);

        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }
}