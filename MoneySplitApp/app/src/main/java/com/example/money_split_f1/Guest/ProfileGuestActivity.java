package com.example.money_split_f1.Guest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.money_split_f1.MainActivity;
import com.example.money_split_f1.databinding.ActivityProfileGuestBinding;
import com.example.money_split_f1.profile.ImpressumActivity;
import com.example.money_split_f1.signup.SignUpActivity;

public class ProfileGuestActivity extends AppCompatActivity {

    ActivityProfileGuestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileGuestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonImpressum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ImpressumActivity.class));
            }
        });

        binding.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        binding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

    }
}