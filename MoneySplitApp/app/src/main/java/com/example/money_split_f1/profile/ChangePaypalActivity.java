package com.example.money_split_f1.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.databinding.ActivityChangePaypalBinding;
import com.example.money_split_f1.Repos.SharedPrefsRepo;
import com.example.money_split_f1.User.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ChangePaypalActivity extends AppCompatActivity {

    ActivityChangePaypalBinding binding;
    UserData userData = UserData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePaypalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String tv = "";
        if(userData.getPaypal() != null){
            tv = SuperApplication.getContext().getResources().getString(R.string.current_link) + userData.getPaypal();
        }

        binding.tvCurrentLink.setText(tv);

        binding.ButtonChangePaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paypal = binding.PaypalLink.getText().toString().trim();
                if(paypal.isEmpty()){
                    binding.PaypalLink.setError(SuperApplication.getContext().getResources().getString(R.string.chgPaypal_failure));
                    binding.PaypalLink.requestFocus();
                    return;
                }
                makePaypalSetRequest(paypal);
            }
        });
    }

    public void makePaypalSetRequest(String paypal){
        //Instantiate a RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String baseUrl = SharedPrefsRepo.getInstance().getServerIp();
        String url = SuperApplication.getContext().getResources().getString(R.string.api_change_paypal,baseUrl);

        JSONObject object = null;
        try {
            object = new JSONObject().put("paypalme", paypal);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ChangePaypalActivity.this, "JSONObject error!", Toast.LENGTH_SHORT).show();
        }

        //Request a JSON response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String paypalme = response.optString("username");
                userData.setPaypal(paypalme);
                Toast.makeText(ChangePaypalActivity.this, "Paypal-Link wurde zu " + paypalme + " geändert!", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERORR", error.toString());
                Toast.makeText(ChangePaypalActivity.this, "change paypalme failure", Toast.LENGTH_LONG).show();
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
}

