package com.example.money_split_f1.Notification;

public class InvitationNotification extends NotificationData{

    public InvitationNotification(String user, String event, String id, int event_id){
        super("Einladung", user + " hat dich zur Veranstaltung \"" + event + "\" hinzugefügt.", false,id,event_id);
    }
}
