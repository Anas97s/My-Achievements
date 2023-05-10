package com.example.money_split_f1.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.money_split_f1.User.UserData;

public class ProfileViewModel extends ViewModel {
    UserData userData = UserData.getInstance();

    MutableLiveData<String> username = userData.getLiveUsername();

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public void setUsername(String username) {
        userData.setUsername(username);
    }
}
