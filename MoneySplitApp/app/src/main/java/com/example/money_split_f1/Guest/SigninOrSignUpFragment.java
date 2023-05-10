package com.example.money_split_f1.Guest;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.money_split_f1.MainActivity;
import com.example.money_split_f1.R;
import com.example.money_split_f1.signup.SignUpActivity;


public class SigninOrSignUpFragment extends Fragment {
    private Button signIn, signUp;

    public SigninOrSignUpFragment(){
        //Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin_or_signup, container, false);

        signIn = view.findViewById(R.id.signInGuest);
        signUp = view.findViewById(R.id.signUPGuest);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignUpActivity.class));
            }
        });

        return view;
    }


}