package com.example.money_split_f1.Event;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.money_split_f1.Post.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.money_split_f1.Adapters.EventActivitylistAdapter;
import com.example.money_split_f1.Adapters.EventParticipantlistAdapter;
import com.example.money_split_f1.Adapters.RecyclerViewClickListener;
import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.EventList;
import com.example.money_split_f1.Event.Data.Participant;
import com.example.money_split_f1.Event.Data.Post;

import com.example.money_split_f1.Post.PostViewUi;
import com.example.money_split_f1.R;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.Repos.SharedPrefsRepo;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.User.UserData;
import com.example.money_split_f1.ui.startScreen.EventViewModel;
import com.example.money_split_f1.ui.startScreen.StartActivity;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**This class contains everything for event view, such like buttons for adding new Post to event, both tabs (Activity and Participant) and event setting
 *
 * @author Anas Salameh
 * */
public class EventUi  extends AppCompatActivity implements RecyclerViewClickListener {

    EventViewModel viewModel;
    ConstraintLayout settingsLayout;
    ImageView back, setting, add, edit;
    TextView eventName;
    RecyclerView activityView, membersView;
    TabLayout tabs;
    private int selectedTab, eventID;
    private EventRepository eventRepo = EventRepository.getInstance();
    EventData event;
    LocalDate date;
    String sDate, name = "";
    boolean goBackToNews;
    EventParticipantlistAdapter eventParticipantlistAdapter;

    EventActivitylistAdapter eventActivitylistAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_ui);

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
        goBackToNews = getIntent().getBooleanExtra("Notification",false);

        name = getIntent().getStringExtra("Name");

        initView();
        eventID = getIntent().getIntExtra("id", -1);
        if (eventID == -1){
            //TODO: show error
            System.err.println("Failed to get event by provided EventID");
        }else {
            event = eventRepo.getEventbyID(eventID);
        }

        tabs.addTab(tabs.newTab().setText(SuperApplication.getContext().getText(R.string.event_activity)));
        tabs.addTab(tabs.newTab().setText(SuperApplication.getContext().getText(R.string.event_participants)));

        eventName.setText(name);
        if (event != null)
            date = event.getDueDate();
        sDate = getDeDate(date);
        String serverDate = String.valueOf(date);


        eventParticipantlistAdapter = new EventParticipantlistAdapter(event, this);
        
        eventActivitylistAdapter = new EventActivitylistAdapter(event, this);
        activityView.setAdapter(eventActivitylistAdapter);


        //by default
        tabs.selectTab(tabs.getTabAt(0));
        activityView.setVisibility(View.VISIBLE);
        membersView.setVisibility(View.GONE);
        setSelectedTab(0);


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals(SuperApplication.getContext().getText(R.string.event_activity))){
                    activityView.setVisibility(View.VISIBLE);
                    activityView.setAdapter(eventActivitylistAdapter);
                    membersView.setVisibility(View.GONE);
                    setSelectedTab(0);
                }else{
                    activityView.setVisibility(View.GONE);
                    membersView.setAdapter(eventParticipantlistAdapter);
                    membersView.setVisibility(View.VISIBLE);
                    setSelectedTab(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //required
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //required
            }
        });

        //create new post in event
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventUi.this, CreatePost.class);
                intent.putExtra("title", name);
                intent.putExtra("v_id", eventID);
                startActivity(intent);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goBackToNews){
                    finish();
                }else {
                    startActivity(new Intent(EventUi.this, StartActivity.class));
                }

            }
        });

        //switch to setting UI
        settingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventUi.this, EventSetting.class);
                intent.putExtra("title", name);
                intent.putExtra("v_id", eventID);
                intent.putExtra("eventDate", sDate);
                intent.putExtra("serverDate", serverDate);
                startActivity(intent);
            }
        });

        viewModel.getMutableLiveDataEventList().observe(this,observer);

    }

    Observer<List<EventList>> observer = new Observer<List<EventList>>() {
        @Override
        public void onChanged(@Nullable List<EventList> eventLists) {
            event = eventRepo.getEventbyID(eventID);
            eventParticipantlistAdapter.update(event);
            eventActivitylistAdapter.update(event);
        }
    };

    /**This methode is only for initilize the View of UI*/
    private void initView() {
        back = (ImageView) findViewById(R.id.custom_toolbar_back);
        setting = (ImageView) findViewById(R.id.event_settings);
        add = (ImageView) findViewById(R.id.addPost);
        edit = (ImageView) findViewById(R.id.editPost);
        eventName = (TextView) findViewById(R.id.setting_title);
        tabs = (TabLayout) findViewById(R.id.tabLayout);
        settingsLayout = (ConstraintLayout) findViewById(R.id.event_settings_Layout);
        activityView = (RecyclerView) findViewById(R.id.activityView);
        activityView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        membersView = (RecyclerView) findViewById(R.id.membersView);
        membersView.setHasFixedSize(true);
        membersView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        edit.setVisibility(View.GONE);
    }

    /**This methode is to convert date from YYYY-MM-DD to DD.MM.YYYY in German format
     * @param date  date format
     * @return String a german date format
     * */
    private String getDeDate(LocalDate date){
        String resultDate = "";
        if(date == null){
            return "00.00.0000";
        }
        int day = date.getDayOfMonth();
        if (day < 10){
            resultDate += "0" + day + ".";
        }else{
            resultDate += day + ".";
        }

        int month = date.getMonthValue();
        if (month < 10){
            resultDate += "0" + month + ".";
        }else{
            resultDate += month + ".";
        }

        return resultDate + date.getYear();

    }


    @Override
    public void ItemClicked(int pos) {
        if (getSelectedTab() == 0){

            Intent intent = new Intent(EventUi.this, PostViewUi.class);
            Post post = event.getPostList().get(pos);
            intent.putExtra("Name", name);
            intent.putExtra("post_id", post.getPostID());
            intent.putExtra("eventID", eventID);
            startActivity(intent);

        }else if (getSelectedTab() == 1){
            Participant participant = event.getParticipantList().get(pos);
            startParticipantActivity(participant.getEmail(), pos);
            //code below should be in startParticipantActivity after bug fix
            /*Intent intent = new Intent(EventUi.this, ParticipantDetail.class);
            intent.putExtra("EventId", eventID);
            //intent.putExtra("userPaypal",response.optString("paypalme","no PayPal"));
            Participant user = event.getParticipantList().get(pos);
            intent.putExtra("EventId", eventID);
            intent.putExtra("userEmail", user.getEmail());
            intent.putExtra("userName", user.getName());
            intent.putExtra("userPaypal", user.getPayPal());
            startActivity(intent);;
             */
        }
    }


    public void startParticipantActivity(String email,int pos){
        //Instantiate a RequestQueue
        UserData userData = UserData.getInstance();
        RequestQueue queue = Volley.newRequestQueue(this);
        String baseUrl = SharedPrefsRepo.getInstance().getServerIp();
        String url = SuperApplication.getContext().getResources().getString(R.string.api_get_paypal,baseUrl);

        JSONObject object = null;
        try {
            object = new JSONObject().put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(EventUi.this, "JSONObject error!", Toast.LENGTH_SHORT).show();
        }

        //Request a JSON response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String paypal = response.optString("paypalme","");
                Participant user = event.getParticipantList().get(pos);;
                user.setPayPal(paypal);
                Intent intent = new Intent(EventUi.this, ParticipantDetail.class);
                intent.putExtra("EventId", eventID);
                intent.putExtra("userPaypal",paypal);
                intent.putExtra("userEmail", user.getEmail());
                intent.putExtra("userName", user.getName());
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERORR", error.toString());
                Toast.makeText(EventUi.this, "get paypalme failure", Toast.LENGTH_LONG).show();
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

    @Override
    public void ItemClicked(int pos, String notificationId) {

    }

    @Override
    public void ItemClicked() {

    }

    private int getSelectedTab(){
        return this.selectedTab;
    }

    private void setSelectedTab(int selectedTab){
        this.selectedTab = selectedTab;
    }
}
