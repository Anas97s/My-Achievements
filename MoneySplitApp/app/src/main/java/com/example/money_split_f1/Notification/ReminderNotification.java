package com.example.money_split_f1.Notification;

public class ReminderNotification extends NotificationData{

    public ReminderNotification(String amount, String toUser,String id,int eventId){
        super("Erinnerung", "Zu bezahlen: " + amount + " an " + toUser + " .", false,id,eventId);
    }
}
