package com.example.money_split_f1.Repos;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.Notification.InvitationNotification;
import com.example.money_split_f1.Notification.NewPostenNotification;
import com.example.money_split_f1.Notification.NotificationData;
import com.example.money_split_f1.Notification.PaymentNotification;
import com.example.money_split_f1.Notification.ReminderNotification;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.User.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

//hier würde der Zugriff auf die Datenbank stattfinden
public class NotificationRepository {
    MutableLiveData<List<NotificationData>> notifications = new MutableLiveData<List<NotificationData>>();
    UserData userData = UserData.getInstance();
    private static final int EVENT_ADDED_NOTIFICATION = 1;
    private static final int PAYMENT_NOTIFICATION = 2;
    private static final int POST_ADDED_NOTIFICATION = 3;
    private static final NotificationRepository thisInstance = new NotificationRepository();

    public static NotificationRepository getInstance() {
        return thisInstance;
    }

    public NotificationRepository() {
        //notifications.setValue(fillDummyData());
        makeNewsRequest();

    }

    public LiveData<List<NotificationData>> getNotification() {
        return notifications;
    }


    /**
     * parses a JSON Object to a Notification
     * @param object
     * @return a Notification of the given type and null if it could not be parsed because of missing data
     */
    NotificationData parseNotificationData(JSONObject object,String key) throws JSONException{
        int type = object.optInt("type",-1);
        int event_id = object.optInt("v_id",-1);
        String post_id = object.optString("p_id","null");

        EventData event;

        switch (type){
            case EVENT_ADDED_NOTIFICATION:

                event = EventRepository.getInstance().getEventbyID(event_id);
                if (event == null) return null;
                return new InvitationNotification(event.getOwner().getName(),event.getName(),key,event_id);
            case POST_ADDED_NOTIFICATION:
                if (post_id.equals("null")){
                    return new NotificationData("Error","Couldn't load this Message",false,key,-1);
                }
                int eventID = object.optInt("v_id_for_p_id",-1);
                event = EventRepository.getInstance().getEventbyID(eventID);
                if (event == null) return null;
                Post post = event.getPostFromID(post_id);
                return new NewPostenNotification(event.getName(),post.getCreator().getName(),post.getCost()/post.getParticipants().size(),key,eventID);
            case PAYMENT_NOTIFICATION:
                Post payment = EventRepository.getInstance().getPaymentByID(post_id);
                if (payment == null) return null;
                return new PaymentNotification(payment.getCreator().getName(),payment.getCost()/2,key,-1);
            default:
        }

        return new NotificationData("Error","Couldn't load this Message",false,key,-1);
    }


    /**
     * fetches news data from the Server and updates the current news
     */
    public void makeNewsRequest() {
        RequestQueue queue = Volley.newRequestQueue(SuperApplication.getContext());
        String baseUrl = SharedPrefsRepo.getInstance().getServerIp();
        String url = SuperApplication.getContext().getResources().getString(R.string.api_get_news, baseUrl);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("NetworkRequestNews","fetched news");
                List<NotificationData> newNotifications = new ArrayList<>();

                Iterator<String> keys = response.keys();
                //going through all the News
                while(keys.hasNext()) {
                    String key = keys.next();
                    try {
                        JSONObject notificationsObject = response.getJSONObject(key);
                        if (notificationsObject instanceof JSONObject){
                            if(parseNotificationData(notificationsObject,key) != null){
                                newNotifications.add(parseNotificationData(notificationsObject,key));
                            }

                        }

                    } catch (JSONException e) {
                        Log.d("NetworkRequestNews","unable to Parse Notification Data");
                        e.printStackTrace();
                    }
                }
                notifications.postValue(newNotifications);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public HashMap<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", userData.getCookie());
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }

    public void deleteNewsFromID(String n_id){
        RequestQueue queue = Volley.newRequestQueue(SuperApplication.getContext());
        String baseUrl = SharedPrefsRepo.getInstance().getServerIp();
        String url = SuperApplication.getContext().getResources().getString(R.string.api_delete_news, baseUrl);

        JSONObject object = new JSONObject();
        try {
            object.put("n_id", n_id);
        } catch (JSONException e){
            e.printStackTrace();
            return;
        }



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("NetworkNews","deleted news");
                deleteNotificationByID(n_id);
                Toast.makeText(SuperApplication.getContext(),"Nachricht wurde gelöscht.",Toast.LENGTH_SHORT);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public HashMap<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", userData.getCookie());
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    private void deleteNotificationByID(String id){
        List<NotificationData> tempNews = new ArrayList<>(notifications.getValue());
        for (int i = 0; i < tempNews.size(); i++) {
            if (tempNews.get(i).getId().equals(id)){
                tempNews.remove(i);
                notifications.postValue(tempNews);
                return;
            }
        }
    }
}