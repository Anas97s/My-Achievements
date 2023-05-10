package com.example.money_split_f1.Guest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_split_f1.Adapters.RecyclerViewClickListener;
import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.EventList;
import com.example.money_split_f1.Event.Data.Participant;
import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.LocalData.DataFile;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class EventGuestUi extends AppCompatActivity implements RecyclerViewClickListener {
    private ImageView back, setting, add;
    private TextView eventName;
    private TabLayout tabs;
    private int selectedTab, eventID;
    private RecyclerView activityView, membersView;
    private EventData event;
    private DataFile data = DataFile.getEventsInstance();
    private DataFile dataPost = DataFile.getPostsInstance();
    private List<EventList> events = data.getEvents();
    private List<Post> posts = new ArrayList<>();
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_ui_guest);

        title = getIntent().getStringExtra("title");

        initView();

        eventID = getIntent().getIntExtra("id", -1);    //get current event
        event = DataFile.getEventbyID(eventID, events);
        eventName.setText(title);

        tabs.addTab(tabs.newTab().setText(SuperApplication.getContext().getResources().getString(R.string.event_activity)));
        tabs.addTab(tabs.newTab().setText(SuperApplication.getContext().getResources().getString(R.string.event_participants)));


        List<Participant> participants = new ArrayList<>();
        participants.add(new Participant(SuperApplication.getContext().getResources().getString(R.string.guest), " "));

        GuestParticipantAdapter guestEventAdapter = new GuestParticipantAdapter(participants, this);


        for (int i = 0; i < dataPost.getPosts().size(); i++){
            if (title.equals(dataPost.getPosts().get(i).getOriginEventName())){
                posts.add(dataPost.getPosts().get(i));
            }
        }
        GuestEventActivityAdapter eventActivitylistAdapter = new GuestEventActivityAdapter(posts, this);
        if (!posts.isEmpty()){
            activityView.setAdapter(eventActivitylistAdapter);
        }

        //by default
        tabs.selectTab(tabs.getTabAt(0));
        activityView.setVisibility(View.VISIBLE);
        setSelectedTab(0);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EventGuestUi.this, CreatePostGuest.class);
                intent.putExtra("title", title);
                intent.putExtra("eventID", eventID);
                startActivity(intent);
            }
        });

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals(SuperApplication.getContext().getResources().getString(R.string.event_activity))){
                    activityView.setVisibility(View.VISIBLE);
                    membersView.setVisibility(View.GONE);
                    setSelectedTab(0);
                }else{
                    activityView.setVisibility(View.GONE);
                    membersView.setAdapter(guestEventAdapter);
                    membersView.setVisibility(View.VISIBLE);
                    setSelectedTab(1);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventGuestUi.this, GuestActivity.class));
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventGuestUi.this, EventSettingGuest.class);
                intent.putExtra("id", eventID);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.event_back_toolbar_Guest);
        setting = (ImageView) findViewById(R.id.event_settings_Guest);
        add = (ImageView) findViewById(R.id.addPostGuest);
        eventName = (TextView) findViewById(R.id.setting_title_Guest);
        tabs = (TabLayout) findViewById(R.id.tabLayoutGuest);

        activityView = (RecyclerView) findViewById(R.id.activityViewGuest);     //set up activity view
        activityView.setHasFixedSize(true);
        activityView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        membersView = (RecyclerView) findViewById(R.id.membersViewGuest);       //set up members view
        membersView.setHasFixedSize(true);
        membersView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private int getSelectedTab(){
        return this.selectedTab;
    }

    private void setSelectedTab(int selectedTab){
        this.selectedTab = selectedTab;
    }

    @Override
    public void ItemClicked(int pos) {
        if (getSelectedTab() == 0){
            Intent intent = new Intent(EventGuestUi.this, PostGuestView.class);
            Post post = dataPost.getPosts().get(pos);
            intent.putExtra("v_id", eventID);
            intent.putExtra("v_title", title);
            intent.putExtra("p_title", post.getTitle());
            intent.putExtra("price", String.valueOf(post.getCost()));
            intent.putExtra("details", post.getDescription());
            intent.putExtra("v_name", post.getOriginEventName());
            startActivity(intent);
        }
    }

    @Override
    public void ItemClicked(int pos, String notificationId) {

    }

    @Override
    public void ItemClicked() {

    }
}