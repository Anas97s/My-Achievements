package com.example.money_split_f1.Repos;


import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.Post;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventCache {
    private static final EventCache thisInstance = new EventCache();

    public static EventCache getInstance(){
        return thisInstance;
    }

    public Map<String, List<EventData>> eventListMap = new HashMap<>();

    //VID + List of corresponding Items
    public Map<Integer, List<Post>> eventMap = new HashMap<>();


}
