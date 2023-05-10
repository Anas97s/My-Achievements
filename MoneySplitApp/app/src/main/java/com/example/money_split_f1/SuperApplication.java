package com.example.money_split_f1;

import android.app.Application;
import android.content.Context;

public class SuperApplication extends Application {
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
