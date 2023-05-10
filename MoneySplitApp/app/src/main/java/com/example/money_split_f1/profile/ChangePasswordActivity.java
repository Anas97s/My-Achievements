package com.example.money_split_f1.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.databinding.ActivityChangePasswordBinding;
import com.example.money_split_f1.Repos.SharedPrefsRepo;
import com.example.money_split_f1.User.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding binding;
    UserData userData = UserData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button changePassword  = binding.ButtonChangePassword;
        EditText currentPassword = binding.currentPassword;
        EditText password1 = binding.ChangePassword1;
        EditText password2 = binding.ChangePassword2;

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNewPassword();
            }

            private void checkNewPassword() {
                String currPass = currentPassword.getText().toString().trim();
                String pass1 = password1.getText().toString().trim();
                String pass2 = password2.getText().toString().trim();
                String resultPassWord = "";

                if (pass1.isEmpty()){
                    password1.setError(SuperApplication.getContext().getResources().getString(R.string.password_required));
                    password1.requestFocus();
                    return;
                }

                if (pass1.length() < 8){
                    password1.setError(SuperApplication.getContext().getResources().getString(R.string.invalid_password));
                    password1.requestFocus();
                    return;
                }

                if (pass2.isEmpty()){
                    password2.setError(SuperApplication.getContext().getResources().getString(R.string.password_required));
                    password2.requestFocus();
                    return;
                }

                if (!pass2.equals(pass1)){
                    password2.setError(SuperApplication.getContext().getResources().getString(R.string.identical_passwords));
                    password2.requestFocus();
                    return;
                }else{
                    resultPassWord = pass1;
                }


                makeChangePasswordRequest(currPass, resultPassWord);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back button
    }

    private void makeChangePasswordRequest(String currentPassword,String newPassword) {
        String baseUrl = SharedPrefsRepo.getInstance().getServerIp();
        String url = SuperApplication.getContext().getResources().getString(R.string.api_change_password,baseUrl);

        JSONObject object = new JSONObject();
        try {
            object.put("password", currentPassword);
            object.put("new_password", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.opt("message").toString().equals("Password changed successfully")) {
                    Toast.makeText(ChangePasswordActivity.this, SuperApplication.getContext().getResources().getString(R.string.chgPassword_success), Toast.LENGTH_SHORT).show();
                    finish();
                }else if (response.opt("message").toString().equals("wrong password!")){
                    Toast.makeText(ChangePasswordActivity.this, SuperApplication.getContext().getResources().getString(R.string.chgPassword_failure), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public HashMap<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", userData.getCookie());
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ChangePasswordActivity.this);
        queue.add(jsonRequest);
    }
}