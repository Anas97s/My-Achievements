package com.example.money_split_f1.Guest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.money_split_f1.MainActivity;
import com.example.money_split_f1.R;
import com.example.money_split_f1.signup.SignUpActivity;

public class SignInOrSignUpGuestActivity extends AppCompatActivity {
    Button logIn, signUp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_or_sign_up_guest);

        logIn = (Button) findViewById(R.id.signInGuestActivity);
        signUp = (Button) findViewById(R.id.signUPGuestActivity);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInOrSignUpGuestActivity.this, MainActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInOrSignUpGuestActivity.this, SignUpActivity.class));
            }
        });
    }
}