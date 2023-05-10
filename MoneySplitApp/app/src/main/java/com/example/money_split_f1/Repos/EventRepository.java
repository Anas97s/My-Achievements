package com.example.money_split_f1.Repos;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.EventList;
import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.Event.Data.PostUserList;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.User.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


/**
 * This Class contains the network logic to retrieve Events and the corresponding posts from the server
 *
 * @author Tim Oliver Wolter
 */
public class EventRepository {
    private MutableLiveData<List<EventList>> eventList = new MutableLiveData<List<EventList>>();
    private MutableLiveData<List<Post>> payments = new MutableLiveData<>();
    //TODO: Payments/ get Payments
    private List<EventList> list = new ArrayList<>();


    private SharedPrefsRepo sharedPrefsRepo = new SharedPrefsRepo();
    private UserData userData = UserData.getInstance();

    //initializes this instance of the event repository
    private static final EventRepository thisInstance = new EventRepository();

    //checks whether the data has been initialized
    private Boolean initDone = false;

    static final int CATEGORY_OPEN = 0;
    static final int CATEGORY_DUE = 1;
    static final int CATEGORY_DONE = 2;
    static final int CATEGORY_ARCHIVED = 3;
    //might not be needed, could be used to make network calls smarter
    private EventCache eventCache = EventCache.getInstance();

    private String baseUrl = sharedPrefsRepo.getServerIp();



    public static EventRepository getInstance(){
        return thisInstance;
    }

    public EventRepository(){
        //creates the initial List with the 4 categories
        reset();

    }

    public void setEventList(MutableLiveData<List<EventList>> eventList) {
        this.eventList = eventList;
    }

    //makes a network request to fetch data the first time it is called
    public MutableLiveData<List<EventList>> getEventList(){
        if (!initDone){
            String baseUrl = sharedPrefsRepo.getServerIp();
            updateEventList();
            initDone = true;
        }
        return eventList;
    }

    /**
     * searches and returns an event inside all categories with the given EventID
     * @param EventID
     * @return an Object of Type EventData if an Object with EventID could be found
     * @return null object if no EventData with the EventID could be found
     */
    public EventData getEventbyID(int EventID){
        List<EventList> events = this.eventList.getValue();
        for (EventList eventList:events){
            if (eventList != null && eventList.getEventById(EventID) != null)
                return eventList.getEventById(EventID);
        }
        return null;
    }

    public Post getPaymentByID(String paymentID){
        List<Post> payList = this.payments.getValue();
        for (Post payment:payList){
            if (payment.getPostID().equals(paymentID)){
                return payment;
            }
        }
        return null;
    }

    public MutableLiveData<List<Post>> getAllPayments(){
        return payments;
    }


    /**
     * fetches Data from the server and parses it
     * categorizes the data and places it in eventList
     */
    public void updateEventList(){
        makeGetAllEventsNetworkRequest();
    }

    /**
     * the network Request to fetch eventData from the server is handled here
     */
    public void makeGetAllEventsNetworkRequest(){
        String debugTag = "NetworkRequestEventList";
        //TODO: Reduce Network Requests
        Log.println(Log.DEBUG,debugTag, "Trying to fetch data");
        //init ArchivedEventsList
        List<EventData> archivedEvents = new ArrayList<>();
        List<EventData> openEvents = new ArrayList<>();
        List<EventData> doneEvents = new ArrayList<>();
        List<EventData> dueEvents = new ArrayList<>();


        RequestQueue queue = Volley.newRequestQueue(SuperApplication.getContext());
        String url = SuperApplication.getContext().getResources().getString(R.string.api_get_all,baseUrl);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //each event uses its ID as a key
                Iterator<String> keys = response.keys();
                //going through all the events
                while(keys.hasNext()) {
                    String key = keys.next();
                    try {
                        if (response.get(key) instanceof JSONObject) {
                            //parsing an Object corresponding to the current key/id
                            JSONObject eventObj = response.getJSONObject(key);
                            EventData currentEvent = Parser.ParseEventFromJSON(eventObj,key);

                            switch (getCategoryOfEvent(currentEvent)){
                                case CATEGORY_ARCHIVED:
                                    archivedEvents.add(currentEvent);
                                    break;
                                case CATEGORY_DUE:
                                    dueEvents.add(currentEvent);
                                    break;
                                case CATEGORY_DONE:
                                    doneEvents.add(currentEvent);
                                    break;
                                default:
                                    openEvents.add(currentEvent);
                            }

                        }

                    } catch (JSONException e) {
                        Toast.makeText(SuperApplication.getContext(),R.string.data_fetch_error,Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                eventList.getValue().get(CATEGORY_OPEN).setEvents(openEvents);
                eventList.getValue().get(CATEGORY_DUE).setEvents(dueEvents);
                eventList.getValue().get(CATEGORY_DONE).setEvents(doneEvents);
                eventList.getValue().get(CATEGORY_ARCHIVED).setEvents(archivedEvents);
                eventList.postValue(eventList.getValue());
                Log.d(debugTag, "Successfully fetched data");
                makeGetAllPaymentsNetworkRequest();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d(debugTag,"Couldn't reach server or parameters were wrong");
            }
        }){
            @Override
            public HashMap<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", userData.getCookie());
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }

    /**
     * the network Request to fetch payments from the server is handled here
     */
    public void makeGetAllPaymentsNetworkRequest(){
        String debugTag = "NetworkRequestAllPayments";

        RequestQueue queue = Volley.newRequestQueue(SuperApplication.getContext());
        String url = SuperApplication.getContext().getResources().getString(R.string.api_get_all_pay,baseUrl);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    payments.postValue(new ArrayList<>(Parser.ParsePostListFromJSON(response)));;
                    Log.d(debugTag,"Successfully fetched Payments");
                    eventList.setValue(eventList.getValue());
                }catch (JSONException e){
                    e.printStackTrace();
                    Log.d(debugTag,"JSON-OBJECT could not be parsed to posts");
                }
                NotificationRepository.getInstance().makeNewsRequest();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(debugTag,"Error: Could not fetch payments");
            }
        }){
            @Override
            public HashMap<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", userData.getCookie());
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }


    //returns list of all posts containing currently logged in user, including payments
    public PostUserList getAllPosts(){
        List<Post> result = new ArrayList<>();
        List<EventList> events = this.eventList.getValue();
        for (EventList eventList:events){
            if (eventList != null && eventList.getEvents() != null)
                for (EventData event:eventList.getEvents()){
                    if(event != null && event.getPostList() != null){
                        for (Post post:event.getPostList()){
                            if (post.getParticipantEmails().contains(userData.getEmail())){
                                post.setOriginEventName(event.getName());
                                result.add(post);
                            }
                        }
                    }
                }
        }
        for (Post post: payments.getValue()){
            if (post.getParticipantEmails().contains(userData.getEmail()))
                result.add(post);
        }
        return new PostUserList(result);
    }

    //returns list of posts from one list i=0 for open, 1 for overdue, 2 for done, 3 for archived
    public PostUserList getAllPostsFrom(int i){
        List<Post> result = new ArrayList<>();
        EventList eventList = this.eventList.getValue().get(i);
        if (eventList != null && eventList.getEvents() != null) {
            for (EventData event : eventList.getEvents()) {
                if (event != null && event.getPostList() != null) {
                    for (Post post : event.getPostList()) {
                        if (post.getParticipantEmails().contains(userData.getEmail())) {
                            result.add(post);
                        }
                    }
                }
            }
        }
        return new PostUserList(result);
    }


    /**
     *
     * @param name of the event
     * @param date the dueDate of the event
     * @param done
     */
    public void makeEventCreationRequest(String name, LocalDate date, Boolean done){
        RequestQueue queue = Volley.newRequestQueue(SuperApplication.getContext());
        String url = SuperApplication.getContext().getResources().getString(R.string.api_create_event,baseUrl);



        JSONObject object = null;
        try {
            object = new JSONObject()
                    .put("name", name)

                    .put("done", done);
                if (date == null){
                    object.put("due_date", JSONObject.NULL);
                }else {
                    object.put("due_date",date);
                }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(SuperApplication.getContext(), "JSONObject error!", Toast.LENGTH_SHORT);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String newID = response.getString("v_id");
                    Log.println(Log.DEBUG,"NetworkRequestEventCreation","Created new Event with ID = " + newID);
                    updateEventList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

                Log.println(Log.DEBUG,"NetworkRequestEventCreation","ERROR: Couldn't create Event");
            }
        }){
            @Override
            public HashMap<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", userData.getCookie());
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    /**
     * Determines the category of the given Event. The category can be used to access the specific List of Events from the List of EventLists
     * @param event of Type EventData
     * @returns CATEGORY_ARCHIVED or CATEGORY_DONE or CATEGORY_DUE or CATEGORY_OPEN
     */
    public int getCategoryOfEvent(EventData event){
        if (sharedPrefsRepo.isArchived(event.getEventID())){
            return CATEGORY_ARCHIVED;
        }
        if (event.getDone()){
            return CATEGORY_DONE;
        }
        Boolean isEventDue = false;
        if (event.getDueDate() != null){
            isEventDue = LocalDate.now().isAfter(event.getDueDate());
            if (isEventDue){
                return CATEGORY_DUE;
            }
        }

        return CATEGORY_OPEN;
    }


    /**
         *
         * @param EventID the event that has to be archived
         */
    public void archive(Integer EventID){
        Log.println(Log.DEBUG,"archived",EventID.toString());


        EventData event = getEventbyID(EventID);
        int category = getCategoryOfEvent(event);
        sharedPrefsRepo.archive(EventID);
        this.eventList.getValue().get(category).getEvents().remove(event);
        this.eventList.getValue().get(CATEGORY_ARCHIVED).getEvents().add(event);
        this.eventList.postValue(this.eventList.getValue());
    }


    /**
     *
     * @param EventID that has to be removed from the archive
     */
    public void removeArchived(Integer EventID){
        Log.println(Log.DEBUG,"archivedRemoved",EventID.toString());
        sharedPrefsRepo.removeArchived(EventID);

        EventData event = getEventbyID(EventID);
        int category = getCategoryOfEvent(event);

        this.eventList.getValue().get(CATEGORY_ARCHIVED).getEvents().remove(event);
        this.eventList.getValue().get(category).getEvents().add(event);
        this.eventList.postValue(this.eventList.getValue());

    }


    /**
     *
     * @param EventID of target event
     * @param email of user that should be added
     */
    public void makeAddUserToEventRequest(int EventID, String email){
        String url = SuperApplication.getContext().getResources().getString(R.string.api_add_user_to_event,baseUrl);

        JSONObject object = new JSONObject();
        try {
          object.put("v_id", EventID);
          object.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (Objects.requireNonNull(response.opt("message")).toString().equals("success")){
                    Toast.makeText(SuperApplication.getContext(), "Teilnehmer wurde hinzugefügt!", Toast.LENGTH_SHORT).show();
                    updateEventList();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public HashMap<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", userData.getCookie());
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(SuperApplication.getContext());
        queue.add(jsonRequest);
    }

    /**
     *
     * @param eventID of target event
     * @param name new name
     * @param date new dueDate
     * @param done sets if the event is done
     */
    public void makeChangeEventRequest(int eventID, String name, String date, Boolean done){
        String url = SuperApplication.getContext().getResources().getString(R.string.api_change_event,baseUrl);

        JSONObject object = new JSONObject();
        try {
            object.put("v_id", eventID);
            object.put("name", name);
            object.put("due_date", date);
            object.put("done", done);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(SuperApplication.getContext(), "Veranstaltung wurde geändert", Toast.LENGTH_SHORT).show();
                updateEventList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERORR IN DATE", error.toString());
            }
        }){
            @Override
            public HashMap<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", userData.getCookie());
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(SuperApplication.getContext());
        queue.add(objectRequest);
    }

    /**
     *
     * @param EventID target event
     * @param email of user that shall be removed
     */
    public void makeRemoveUserFromEventRequest(int EventID, String email){

        RequestQueue queue = Volley.newRequestQueue(SuperApplication.getContext());
        String url = SuperApplication.getContext().getResources().getString(R.string.api_remove_user_from_event,baseUrl);

        JSONObject object = new JSONObject();
        try {
            object.put("v_id", EventID);
            object.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (Objects.requireNonNull(response.opt("message")).toString().equals("success")){
                    updateEventList();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public HashMap<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", userData.getCookie());
                return headers;
            }
        };
        queue.add(jsonRequest);
    }

    /**
     *
     * @param name of Payment
     * @param amount of money
     * @param description
     * @param email of the receiver
     */
    public void makeCreatePaymentRequest(String name, double amount,String description,String email){
        RequestQueue queue = Volley.newRequestQueue(SuperApplication.getContext());
        String url = SuperApplication.getContext().getResources().getString(R.string.api_create_payment,baseUrl);

        JSONObject object = new JSONObject();
        try {
            object.put("name", name);
            object.put("amount", amount);
            object.put("description", description);
            object.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                makeGetAllPaymentsNetworkRequest();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public HashMap<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", userData.getCookie());
                return headers;
            }
        };
        queue.add(jsonRequest);
    }

    public void makeDeleteEventRequest(int v_id){
        RequestQueue queue = Volley.newRequestQueue(SuperApplication.getContext());
        String url = SuperApplication.getContext().getResources().getString(R.string.api_delete_event,baseUrl);

        JSONObject object = new JSONObject();
        try {
            object.put("v_id",v_id);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                updateEventList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public HashMap<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", userData.getCookie());
                return headers;
            }
        };
        queue.add(jsonRequest);
    }

    //setups the categories for the List of EventLists
    public void reset(){
        list = new ArrayList<>();
        list.add(new EventList("Offene Veranstaltungen", Collections.emptyList()));
        list.add(new EventList("Überfällige Veranstaltungen",Collections.emptyList()));
        list.add(new EventList("Erledigte Veranstaltungen",Collections.emptyList()));
        list.add(new EventList("Archivierte Veranstaltungen",Collections.emptyList(),true));
        eventList.setValue(list);
    }
}
