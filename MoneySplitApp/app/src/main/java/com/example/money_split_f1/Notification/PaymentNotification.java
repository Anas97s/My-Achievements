package com.example.money_split_f1.Notification;

import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;

public class PaymentNotification extends NotificationData{

    public PaymentNotification(String user, double amount,String id, int eventId){
        super("Zahlung erhalten", user + " hat dir " + String.format("%3.2f",amount).replace(".", ",")
                + SuperApplication.getContext().getText(R.string.Currency) + " zurückgegeben.", true,id,eventId);
    }

}
