package com.example.money_split_f1.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
import com.example.money_split_f1.databinding.ActivityChangeUsernameBinding;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.Repos.SharedPrefsRepo;
import com.example.money_split_f1.User.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ChangeUsernameActivity extends AppCompatActivity {

    ActivityChangeUsernameBinding binding;
    UserData userData = UserData.getInstance();
    EventRepository eventRepository = EventRepository.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChangeUsernameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button changeUserName = binding.changeName;
        EditText name = binding.usernameChange;

        changeUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserName();
            }

            private void checkUserName() {
                String newUserName = name.getText().toString().trim();

                if (newUserName.isEmpty()){
                    name.setError(SuperApplication.getContext().getResources().getString(R.string.username_required));
                    name.requestFocus();
                    return;
                }

                if (newUserName.length() < 3){
                    name.setError(SuperApplication.getContext().getResources().getString(R.string.username_length));
                    name.requestFocus();
                    return;
                }

                makeChangeUserNameRequest(newUserName);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back button
    }

    private void makeChangeUserNameRequest(String userName) {
        String baseUrl = SharedPrefsRepo.getInstance().getServerIp();
        String url = SuperApplication.getContext().getResources().getString(R.string.api_change_username,baseUrl);

        JSONObject object = new JSONObject();
        try {
            object.put("new_username", userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(ChangeUsernameActivity.this, SuperApplication.getContext().getResources().getString(R.string.chgUsername_success), Toast.LENGTH_SHORT).show();
                try {
                    userData.setUsername(response.getString("username"));
                    eventRepository.updateEventList();

                } catch (JSONException e) {
                    Log.d("NetworkUser","Error while parsing new Username");
                    e.printStackTrace();
                }
                finish();
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

        RequestQueue queue = Volley.newRequestQueue(ChangeUsernameActivity.this);
        queue.add(jsonRequest);

    }

}