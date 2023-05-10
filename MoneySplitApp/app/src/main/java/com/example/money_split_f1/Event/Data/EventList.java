package com.example.money_split_f1.Event.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventList {
    String category;
    List<EventData> events;
    private boolean archive = false;

    public int getListSize(){
        return events.size();
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public EventList(String category, List<EventData> events) {
        this.category = category;
        this.events = events;
    }

    public EventList(String category, List<EventData> events,boolean archive) {
        this.category = category;
        this.events = events;
        this.archive = archive;
    }
    public EventList(EventList eventList){
        this.category = eventList.getCategory();
        this.events = new ArrayList<>(eventList.getEvents());
        this.archive = eventList.isArchive();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<EventData> getEvents() {
        return events;
    }

    public EventData getEventById(int id){
        EventData e;
        Iterator<EventData> i = events.iterator();
        while(i.hasNext()){
            e = (EventData) i.next();
            if(e.getEventID() == id){
                return e;
            }
        }
        return null;
    }

    public void setEvents(List<EventData> events) {
        this.events = new ArrayList<>(events);
    }

    public boolean closeSwipeMenu(){
        boolean action = false;
        for (int i = 0; i < getListSize(); i++) {
            if (events.get(i).isShowSwipeMenu()){
                events.get(i).setShowSwipeMenu(false);
                action = true;
            }
        }
        return action;
    }
}
