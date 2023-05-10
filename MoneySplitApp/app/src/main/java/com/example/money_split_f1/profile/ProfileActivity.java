package com.example.money_split_f1.profile;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.money_split_f1.MainActivity;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.databinding.ActivityProfileBinding;
import com.example.money_split_f1.Repos.SharedPrefsRepo;
import com.example.money_split_f1.User.UserData;

import org.json.JSONObject;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    UserData userData = UserData.getInstance();
    ImageView imageView;
    ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getUsername().observe(this,profileNameObserver);
        UserData.getInstance().getProfilePitureLiveData().observe(this,profilePictureObserver);

        imageView = binding.imageView;

        if (UserData.getInstance().getProfilePicture() == null) {
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else {imageView.setImageBitmap(UserData.getInstance().getProfilePicture());}

        makeUserRequest();
        binding.textEmail.setText(UserData.getInstance().getEmail());
        binding.buttonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChangeUsernameActivity.class));
            }
        });

        binding.buttonPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
            }
        });

        binding.buttonPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Profilbild ändern
                startActivity(new Intent(getApplicationContext(), ChangeProfilePictureActivity.class));
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makeLogoutRequest();
            }
        });

        binding.buttonPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChangePaypalActivity.class));
            }
        });

        binding.buttonImpressum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ImpressumActivity.class));
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back button
    }

    public void openDeleteDialog(){
        AccountDeleteDialog deleteDialog = new AccountDeleteDialog();
        deleteDialog.show(getSupportFragmentManager(), "delete dialog");
    }

    Observer<String> profileNameObserver = new Observer<String>() {
        @Override
        public void onChanged(String s) {
            binding.textName.setText(s);
        }
    };
    Observer<Bitmap> profilePictureObserver = new Observer<Bitmap>() {
        @Override
        public void onChanged(Bitmap b) {
            if(b == null) return;
            binding.imageView.setImageBitmap(b);}
    };

    private void makeUserRequest(){
        RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
        String baseUrl = SharedPrefsRepo.getInstance().getServerIp();
        String url = SuperApplication.getContext().getResources().getString(R.string.api_get_user,baseUrl);

        //Request a JSON response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                binding.textEmail.setText(response.opt("email").toString());
                binding.textName.setText(response.opt("username").toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ProfileActivity.this, "Loading of Data failed", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public HashMap<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", userData.getCookie());
                return headers;
            }
        };

        queue.add(jsonRequest);
    }

    private void makeLogoutRequest(){
        //Instantiate a RequestQueue
        RequestQueue queue = Volley.newRequestQueue(ProfileActivity.this);
        String baseUrl = SharedPrefsRepo.getInstance().getServerIp();
        String url = SuperApplication.getContext().getResources().getString(R.string.api_logout,baseUrl);

        JSONObject object = null;

        //Request a JSON response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.opt("message").toString().equals("success")) {
                    Toast.makeText(ProfileActivity.this, SuperApplication.getContext().getResources().getString(R.string.logout_success), Toast.LENGTH_SHORT).show();
                    userData.logout();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else{
                    Toast.makeText(ProfileActivity.this, SuperApplication.getContext().getResources().getString(R.string.logout_failure), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ProfileActivity.this, SuperApplication.getContext().getResources().getString(R.string.logout_failure), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(jsonRequest);

    }


}

