package com.example.money_split_f1.Notification;

public class NotificationData {

    private String header;
    private String text;
    private String button;
    private String id;
    private int v_id;

    public NotificationData(String h, String t, boolean b, String id, int v_id){
        header = h; text = t;
        if(b){
            button = "Nachricht Löschen";
        }else{
            button = " ";
        }
        this.v_id = v_id;
        this.id = id;
    }

    public String getHeader(){
        return header;
    }
    public String getText(){ return text;}

    public String getButton(){
        return button;
    }

    public void setText(String t){text = t;}

    public int getV_id(){
        return v_id;
    }

    public String getId(){
        return id;
    }

}
