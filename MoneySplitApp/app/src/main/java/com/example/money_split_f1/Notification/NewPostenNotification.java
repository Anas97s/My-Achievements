package com.example.money_split_f1.Notification;

import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;

public class NewPostenNotification extends NotificationData{

    public NewPostenNotification(String event, String user, Double amount, String id, int event_id){
        super( "Neuer Posten", event + ": " + user + " hat " +  String.format("%3.2f",amount).replace(".", ",")
                + SuperApplication.getContext().getText(R.string.Currency)
                + " angefordert.", true, id,event_id);
    }

}
