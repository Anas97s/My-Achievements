package com.example.money_split_f1.User;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.Repos.DownloadImage;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.Repos.SharedPrefsRepo;

import org.json.JSONObject;

import java.util.HashMap;


//TODO: check if Token is outdated

public class UserData {
    private MutableLiveData<String> username = new MutableLiveData<>();
    private String email, token, paypal;
    private static final UserData instance = new UserData();
    private SharedPrefsRepo sharedPrefsRepo = SharedPrefsRepo.getInstance();
    private Integer userID;
    private MutableLiveData<Bitmap> profilePicture = new MutableLiveData<>();
    private String baseUrl = sharedPrefsRepo.getServerIp();

    public UserData(){
        if(sharedPrefsRepo.getKeepLogin()){
            token = sharedPrefsRepo.getLoginToken();
            email = sharedPrefsRepo.getUserEmail();
            makeGetProfilePictureRequest();
        }
    }



    public static UserData getInstance(){
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public String getPaypal(){return paypal;}


    public String getUsername() {
        return username.getValue();
    }

    public MutableLiveData<String> getLiveUsername(){
        return username;
    }

    public String getToken() {
        return token;
    }

    public void setEmail(String email) {
        this.email = email;
        sharedPrefsRepo.setEmail(email);
    }

    public void setPaypal(String paypal){this.paypal = paypal; }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public void setToken(String token) {
        this.token = token;
        sharedPrefsRepo.setLoginToken(token);
    }

    public void setKeepLogin(boolean bool){
        sharedPrefsRepo.setKeepLogin(bool);
    }


    public String getCookie(){
        return "jwt=" + getToken();
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public MutableLiveData<Bitmap> getProfilePitureLiveData(){
        return profilePicture;
    }

    public Bitmap getProfilePicture(){
        return profilePicture.getValue();
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture.postValue(profilePicture);
    }

    public void logout(){
        setKeepLogin(false);
        profilePicture.setValue(null);
        EventRepository.getInstance().reset();
    }

    public void makeGetProfilePictureRequest(){
        String debugTag = "NetworkRequestAllPayments";

        RequestQueue queue = Volley.newRequestQueue(SuperApplication.getContext());
        String url = SuperApplication.getContext().getResources().getString(R.string.api_get_profile_picture,baseUrl);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String urlMediaPart = response.optString("image","null");
                if (urlMediaPart.equals("null")) return;
                DownloadImage downloadImage = new DownloadImage(baseUrl+urlMediaPart);
                Thread getProfilePicture = new Thread(downloadImage);
                getProfilePicture.start();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(debugTag,"Error: Could not get ProfilePicture");
            }
        }){
            @Override
            public HashMap<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", getCookie());
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}
