package com.example.money_split_f1.ui.startScreen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.money_split_f1.Event.Data.EventList;
import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.Repos.EventRepository;

import java.util.List;

public class EventViewModel extends ViewModel {
    private EventRepository eventRepo = EventRepository.getInstance();
    private MutableLiveData<List<EventList>> eventList;

    public EventViewModel(){
        eventList = eventRepo.getEventList();
    }

    public MutableLiveData<List<EventList>> getMutableLiveDataEventList(){
        return eventList;
    }

    public MutableLiveData<List<Post>> getLivePayments() {
        return eventRepo.getAllPayments();
    }

    public  List<EventList> getEventList() {
        return eventRepo.getEventList().getValue();
    }
    public void update(){
        eventRepo.updateEventList();
    }
}
