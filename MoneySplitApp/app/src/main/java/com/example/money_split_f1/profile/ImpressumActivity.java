package com.example.money_split_f1.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.money_split_f1.databinding.ActivityImpressumBinding;
import com.example.money_split_f1.databinding.ActivityProfileBinding;

public class ImpressumActivity extends AppCompatActivity {

    ActivityImpressumBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityImpressumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}