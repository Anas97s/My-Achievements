package com.example.money_split_f1.Event.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private String Title;
    private List<Participant> Participants;
    private double Cost;
    private String Image;
    private LocalDate Date;
    private Participant creator;
    private String PostID;
    private String Description;
    private String originEventName = "";


    public Post(String title, List<Participant> participants, double cost, LocalDate date, Participant creator) {
        Title = title;
        Participants = participants;
        Cost = cost;
        Date = date;
        this.creator = creator;
    }

    public Post(String title, List<Participant> participants, double cost, LocalDate date, Participant creator, String PostID) {
        Title = title;
        Participants = participants;
        Cost = cost;
        Date = date;
        this.creator = creator;
        this.PostID = PostID;
    }

    public Post(String title, List<Participant> participants, double cost, LocalDate date, Participant creator, String PostID, String description) {
        Title = title;
        Participants = participants;
        Cost = cost;
        Date = date;
        this.creator = creator;
        this.PostID = PostID;
        this.Description = description;
    }

    //Guest post constructor
    public Post(String title, double price, String details){
        Title = title;
        Cost = price;
        Description = details;
    }

    public void addParticipant(Participant participant){
        Participants.add(participant);
    }

    public Participant getCreator() {
        return creator;
    }

    public void setCreator(Participant creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public List<Participant> getParticipants() {
        return Participants;
    }

    public void setParticipants(List<Participant> participants) {
        Participants = participants;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        PostID = postID;
    }

    public String getOriginEventName() {
        return originEventName;
    }

    public void setOriginEventName(String originEventName) {
        this.originEventName = originEventName;
    }

    //get list of all the participant's email addresses
    public List<String> getParticipantEmails(){
        List<String> emails = new ArrayList<>();
        for (Participant participant:Participants) {
            emails.add(participant.getEmail());
        }
        return emails;
    }
}
