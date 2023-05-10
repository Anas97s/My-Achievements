package com.example.money_split_f1.Event.Data;

import java.time.LocalDate;
import java.util.List;

//TODO: complete EventInformations
public class EventData {
    private Integer EventID;
    private String name;
    private LocalDate dueDate;
    private List<Participant> participantList;
    private List<Post> postList;
    private String image;
    private Boolean done;
    private Participant owner;

    private boolean showSwipeMenu = false;

    public EventData(Integer eventID, String name, LocalDate dueDate, List<Participant> participantList, List<Post> postList, String image, Boolean done, Participant owner) {
        EventID = eventID;
        this.name = name;
        this.dueDate = dueDate;
        this.participantList = participantList;
        this.postList = postList;
        this.image = image;
        this.done = done;
        this.owner = owner;
    }

    public EventData(Integer eventID, String name, LocalDate dueDate, Boolean done, List<Participant> participantList, List<Post> postList) {
        this.name = name;
        this.dueDate = dueDate;
        this.participantList = participantList;
        this.postList = postList;
        this.EventID = eventID;
        this.done = done;
    }

    public EventData(Integer eventID,String name, LocalDate dueDate) {
        this.EventID = eventID;
        this.name = name;
        this.dueDate = dueDate;
        this.done = false;
    }


    public String getName(){
        return  name;
    }
    public void setName(String name){
        this.name = name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public List<Participant> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<Participant> participantList) {
        this.participantList = participantList;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public Participant getOwner() {
        return owner;
    }

    public void setOwner(Participant owner) {
        this.owner = owner;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isShowSwipeMenu() {
        return showSwipeMenu;
    }

    public void setShowSwipeMenu(boolean showSwipeMenu) {
        this.showSwipeMenu = showSwipeMenu;
    }

    public Integer getEventID() {
        return EventID;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    /**
     *
     * @param post_id
     * @return the Post belonging to post_id or null if the Post could not be found
     */
    public Post getPostFromID(String post_id){
        for (Post post : postList) {
            if (post.getPostID().equals(post_id)){
                return post;
            }
        }
        return null;
    }
}
