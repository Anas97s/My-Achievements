package com.example.money_split_f1.Repos;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.money_split_f1.SuperApplication;

public class SharedPrefsRepo {
    //Constants for saving
    private static final String SHARED_PREFS_ARCHIVE = "shared_prefs_archive";
    private static final String SHARED_PREFS = "shared_prefs";
    private static final String KEEP_LOGIN = "keep_login";
    private static final String LOGIN_TOKEN = "login_token";
    private static final String USER_EMAIL = "user_email";
    private static final String SERVER_IP = "server_ip";
    private static final String INSTALLATION_DONE = "installation_done";
    private static final String GUEST_EVENT_ID = "guest_event_id";
    private static final String GUEST_POST_ID = "guest_post_id";
    boolean setup_done;


    private static final SharedPrefsRepo thisInstance = new SharedPrefsRepo();

    public static SharedPrefsRepo getInstance() {
        return thisInstance;
    }

    //storage of EventID[key],boolean[value] pair to remember which events were archived
    //EventID is a String but the repo receives it as an Integer
    private SharedPreferences preferencesArchive;
    private SharedPreferences guestPreferencesArchive;

    //SharedPreferences Storage for Settings for example "Remember me" setting
    private SharedPreferences preferences;

    public SharedPrefsRepo(){
        preferences = SuperApplication.getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        preferencesArchive = SuperApplication.getContext().getSharedPreferences(SHARED_PREFS_ARCHIVE,Context.MODE_PRIVATE);
        setup_done = getSetupDone();
    }

    public void setKeepLogin(Boolean bool){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEEP_LOGIN,bool).apply();
    }

    public Boolean getKeepLogin(){
        return preferences.getBoolean(KEEP_LOGIN,false);
    }

    public void archive(Integer EventID){
        SharedPreferences.Editor editor = preferencesArchive.edit();
        editor.putBoolean(EventID.toString(),true).apply();
    }

    //returns whether the event with the given EventID is supposed to be archived
    public boolean isArchived(Integer EventID){
        return preferencesArchive.getBoolean(EventID.toString(),false);
    }

    public void removeArchived(Integer EventID){
        SharedPreferences.Editor editor = preferencesArchive.edit();
        editor.putBoolean(EventID.toString(),false);
        editor.apply();
    }

    public String getLoginToken(){
        return preferences.getString(LOGIN_TOKEN,"no token");
    }

    public void setLoginToken(String Token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOGIN_TOKEN,Token).apply();
    }

    public void setEmail(String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_EMAIL, email).apply();
    }

    public String getUserEmail(){
        return preferences.getString(USER_EMAIL,"no email");
    }

    public void setServerIP(String serverIP){
        preferences.edit().putString(SERVER_IP,"http://"+serverIP+":8000").apply();
    }

    public String getServerIp(){
        return preferences.getString(SERVER_IP,"0.0.0.0");
    }

    private boolean getSetupDone(){
        return preferences.getBoolean(INSTALLATION_DONE,false);
    }

    /**
     * If the serverIP has never been set this function sets it
     * @return returns a boolean. True if the ServerIp has never been set and false if the server ip has been set before.
     */
    public boolean setup(){
        if (!setup_done){
            preferences.edit().putBoolean(INSTALLATION_DONE,true).apply();
            return true;
        }
        return false;
    }

    public void setGuestEventID(int id){
        preferences.edit().putInt(GUEST_EVENT_ID,id).apply();
    }

    public int getGuestEventID(){
        return preferences.getInt(GUEST_EVENT_ID,-1);
    }

    public void setGuestPostID(int id){
        preferences.edit().putInt(GUEST_POST_ID,id).apply();
    }


    public int getGuestPostID(){
        return preferences.getInt(GUEST_POST_ID,-1);
    }

}
