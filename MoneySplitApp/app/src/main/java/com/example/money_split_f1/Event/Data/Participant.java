package com.example.money_split_f1.Event.Data;

public class Participant {
    private String Name;
    private String PayPal;
    private String Image;
    private Boolean isOwner;
    private String email;

    public Participant(String name, String email, String image) {
        Name = name;
        this.email = email;
        Image = image;
        isOwner = false;
    }

    public Participant(String name, String payPal, String image, Boolean isOwner, String email) {
        Name = name;
        PayPal = payPal;
        Image = image;
        this.isOwner = isOwner;
        this.email = email;
    }
    public Participant(String Name, String email){
        this.Name = Name;
        this.email = email;
    }

    public Participant(String name, String payPal, String image,String email) {
        Name = name;
        PayPal = payPal;
        Image = image;
        isOwner = false;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPayPal() {
        return PayPal;
    }

    public void setPayPal(String payPal) {
        PayPal = payPal;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
