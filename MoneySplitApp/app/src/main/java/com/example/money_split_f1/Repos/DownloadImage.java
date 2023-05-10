package com.example.money_split_f1.Repos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.money_split_f1.User.UserData;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImage implements Runnable {

    private URL url;

    public DownloadImage(String url){
        try {
            this.url = new URL(url);
            //this.url = new URL("https://rickandmortyapi.com/api/character/avatar/1.jpeg");
        } catch (MalformedURLException e){
            e.printStackTrace();
            this.url = null;
        }

    }

    @Override
    public void run() {
        Bitmap bm = null;
        if (this.url == null) return;
        try {
            bm =    BitmapFactory.decodeStream(url.openConnection().getInputStream());
            UserData.getInstance().setProfilePicture(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
