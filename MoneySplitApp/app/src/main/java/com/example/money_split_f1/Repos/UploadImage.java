package com.example.money_split_f1.Repos;

import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadImage implements Runnable{
    String urlString = SuperApplication.getContext().getString(R.string.api_set_profile_picture,SharedPrefsRepo.getInstance().getServerIp());
    HttpURLConnection connection = null;
    public UploadImage(){

    }

    @Override
    public void run() {
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("Post");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
