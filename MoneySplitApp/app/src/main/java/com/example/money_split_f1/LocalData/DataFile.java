package com.example.money_split_f1.LocalData;

import static com.example.money_split_f1.SuperApplication.getContext;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.EventList;
import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.Repos.SharedPrefsRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class DataFile {
    private String filename;
    private Context context;
    private static final DataFile localEvents = new DataFile("events", getContext());
    private static final DataFile localPosts = new DataFile("posts", getContext());
    private int newEventID = SharedPrefsRepo.getInstance().getGuestEventID();
    MutableLiveData<List<EventList>> localEventsLiveData = new MutableLiveData<>();
    MutableLiveData<List<Post>> localPostsLiveData = new MutableLiveData<>();
    private int newPostID = SharedPrefsRepo.getInstance().getGuestPostID();


    public DataFile(String file, Context ctx){
        filename = file;
        context = ctx;
    }


    public static DataFile getEventsInstance(){
        return localEvents;
    }

    public static DataFile getPostsInstance(){return localPosts;}

    public void initialize(){
        ArrayList<EventList> empty = new ArrayList<>();                   //initialisieren der 4 EventLists
        empty.add(new EventList("Offene Veranstaltungen", Collections.emptyList()));
        empty.add(new EventList("Überfällige Veranstaltungen", Collections.emptyList()));
        empty.add(new EventList("Erledigte Veranstaltungen", Collections.emptyList()));
        empty.add(new EventList("Archivierte Veranstaltungen", Collections.emptyList(), true));

        setEvents(empty);   //erste (leere) Liste in File schreiben
    }

    /**
     * gibt noch nie verwendete EventID wieder
     * vorausgesetzt es wurde für alle Events diese Funktion verwendet
     */
    public int getNewEventID(){
        newEventID++;
        SharedPrefsRepo.getInstance().setGuestEventID(newEventID);
        return newEventID;
    }

    public MutableLiveData<List<EventList>> getLocalEventsLiveData(){
        return localEventsLiveData;
    }


    /**Creates id for each new post*/
    public int getNewPostID(){
        newPostID++;
        SharedPrefsRepo.getInstance().setGuestPostID(newPostID);
        return newPostID;
    }

    /**
     * Gibt komplette Liste der EventLists mit allen Events wieder, so wie sie im internen File stehen
     * Dabei wird der String wieder als Json interpretiert und dann zum Java Objekt umgewandelt, alles durch Gson
     */
    public List<EventList> getEvents(){

        ArrayList<String> events = new ArrayList<>();
        try {                                                       //generate strings from file
            Scanner scanner = new Scanner(context.openFileInput(filename)).useDelimiter("trennerXXX");  //one string for each eventList
            while(scanner.hasNext()){
                events.add(scanner.next());
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(events.size() < 4) return null;

        Log.d("getEvents (offene)", events.get(0));         //zum debuggen
        Log.d("getEvents (überfällige)", events.get(1));
        Log.d("getEvents (erledigte)", events.get(2));
        Log.d("getEvents (archivierte)", events.get(3));

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());  //deserializer für localdate hinzufügen
        Gson g = gsonBuilder.setPrettyPrinting().create();

        ArrayList<EventList> eventList = new ArrayList<>();
        for(int i = 0; i < events.size(); i++) {
            eventList.add(g.fromJson(events.get(i), EventList.class));
        }
        localEventsLiveData.setValue(eventList);

        return eventList;
    }

    public List<Post> getPosts(){
        ArrayList<String> posts = new ArrayList<>();
        try {                //generate strings from file
            Scanner scanner = new Scanner(context.openFileInput(filename)).useDelimiter("trennerXXX");  //one string for each eventList
            while(scanner.hasNext()){
                posts.add(scanner.next());
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson g = gsonBuilder.setPrettyPrinting().create();

        ArrayList<Post> postList = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++){
            postList.add(g.fromJson(posts.get(i), Post.class));
        }

        return  postList;
    }

    /**
     *  Schreibt List<EventList> in den internen Speicher
     *  Dabei wird Java zu Json zu String umgewandelt, mit Hilfe von Gson
     *  Alter Speicher wird komplett überschrieben
     * @param events    vollständige liste der 4 eventlists mit allen Events und allen Daten
     */
    public void setEvents(List<EventList> events){
        localEventsLiveData.setValue(events);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());    //serializer für localdate
        Gson g = gsonBuilder.setPrettyPrinting().create();

        String eventList;
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < events.size(); i++) {
            String json = g.toJson(events.get(i));    //convert EventList to json String
            stringBuilder.append(json);
            if(i + 1 < events.size()){
                stringBuilder.append("trennerXXX");     //zum scannen beim auslesen
            }
        }
        eventList = stringBuilder.toString();
        Log.d("setEvents", eventList);  //nur zum debuggen

        try {                                                              //String in file schreiben
            FileOutputStream fOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fOut.write(eventList.getBytes());
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPosts(List<Post> posts, int eventID){
        localPostsLiveData.postValue(posts);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson g = gsonBuilder.setPrettyPrinting().create();

        String postList;
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < posts.size(); i++) {
            String json = g.toJson(posts.get(i));    //convert postList to json String
            stringBuilder.append(json);
            if(i + 1 < posts.size()){
                stringBuilder.append("trennerXXX");     //zum scannen beim auslesen
            }
        }

        postList = stringBuilder.toString();
        Log.d("setPOSTS", postList);  //nur zum debuggen

        try {                                                              //String in file schreiben
            FileOutputStream fOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fOut.write(postList.getBytes());
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gibt Event mit der entsprechenden Id wieder
     */
    public static EventData getEventbyID(int EventID, List<EventList> events){
        for (EventList eventList:events){
            if (eventList != null && eventList.getEventById(EventID) != null)
                return eventList.getEventById(EventID);
        }
        return null;
    }

    /**
     * geht die gesamten offenen Events durch, und verschiebt sie ggf. zu überfälligen
     */
    public static List<EventList> checkOverdue(List<EventList> events){
        List<EventData> openEvents = events.get(0).getEvents();
        List<EventData> overdueEvents = events.get(1).getEvents();

        LocalDate today = LocalDate.now();

        for(int i = 0; i < openEvents.size(); i++){
            if((openEvents.get(i).getDueDate() != null) && (today.compareTo(openEvents.get(i).getDueDate()) >= 0)){   //heutige fälligkeitsdaten sind auch überfällig, für Testzwecke
                    overdueEvents.add(openEvents.get(i));
                    openEvents.remove(i);
            }
        }

        events.get(0).setEvents(openEvents);
        events.get(1).setEvents(overdueEvents);
        return events;
    }
    public void archive(EventData eventData){
        Log.d("TAG", "archive: "+eventData.toString());
        List<EventList> eventList= localEventsLiveData.getValue();
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getEvents().remove(eventData)){
                eventList.get(3).getEvents().add(eventData);
                setEvents(eventList);
                return;
            }
        }

    }



    public void removeArchived(EventData eventData){
        Log.d("TAG", "removeArchived: "+eventData.toString());
        List<EventList> eventList= localEventsLiveData.getValue();
            if (eventData.getDone()){
                eventList.get(2).getEvents().add(eventData);
            }else {
                eventList.get(0).getEvents().add(eventData);
                eventList = checkOverdue(eventList);
            }
            eventList.get(3).getEvents().remove(eventData);
            setEvents(eventList);


    }

}

/**
 *  Klasse zum serialisieren von LocalDate (dueDate in EventData), weil gson das nicht kann
 */
class LocalDateSerializer implements JsonSerializer<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");

    @Override
    public JsonElement serialize(LocalDate localDate, Type srcType, JsonSerializationContext context) {
        if (localDate == null){
            return new JsonPrimitive("null");
        }
        return new JsonPrimitive(formatter.format(localDate));
    }
}

/**
 * Klasse zum deserialisieren, damit aus dem json auch wieder ein passendes LocalDate wird
 */
class LocalDateDeserializer implements JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json.getAsString().equals("null")) return null;
        return LocalDate.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("d-MMM-yyyy").withLocale(Locale.ENGLISH));
    }
}