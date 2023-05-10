package com.example.money_split_f1.Event;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_split_f1.Adapters.RecyclerViewClickListener;
import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.ui.startScreen.EventViewModel;
import com.example.money_split_f1.User.UserData;
import com.google.android.material.tabs.TabLayout;

import com.example.money_split_f1.Adapters.participantPostListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//displays all posts concerning selected and logged in user either in just the current event or globally
//as well as providing a button for adding payments and reaching the paypal page of selected user (if possible)
public class ParticipantDetail extends AppCompatActivity implements RecyclerViewClickListener {

    private ImageView back, participantImage;
    private TextView participantName, totalCost;
    private TabLayout TabLayout;
    private RecyclerView allEventList, thisEventList;
    private Button paypalButton, paymentButton;
    private double thisEventCost, allEventCost;
    private String participantEmail, participantPaypal;
    private participantPostListAdapter allPostAdapter;
    private participantPostListAdapter thisEventPostAdapter;
    //lists for the 2 different viewmodes
    private List<Post> allPosts;
    private List<Post> thisEventPosts;

    private static final EventRepository repo = EventRepository.getInstance();
    private EventData event;
    EventViewModel viewModel;

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
        setContentView(R.layout.participant_detail);
        init();

        //get all intent information
        int eventID = getIntent().getIntExtra("EventId", -1);
        if (eventID == -1){
            //TODO: show error
            System.err.println("Failed to get event by provided EventID");
        }else {
            event = repo.getEventbyID(eventID);
        }
        participantEmail = getIntent().getStringExtra("userEmail");
        participantName.setText(getIntent().getStringExtra("userName"));
        participantPaypal = getIntent().getStringExtra("userPaypal");


        initButtons();

        //populate allposts and thiseventposts with relevant data for
        fillLists();

        calculateCosts(allPosts, thisEventPosts);

        allPostAdapter = new participantPostListAdapter(allPosts, this);
        thisEventPostAdapter = new participantPostListAdapter(thisEventPosts, this);
        allEventList.setAdapter(allPostAdapter);
        thisEventList.setAdapter(thisEventPostAdapter);

        //select "this event" tab by default
        TabLayout.selectTab(TabLayout.getTabAt(0));
        thisEventList.setVisibility(View.VISIBLE);
        allEventList.setVisibility(View.GONE);;
        totalCost.setText(formatCost(thisEventCost));


        TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == TabLayout.getTabAt(0)){
                    allEventList.setVisibility(View.GONE);
                    thisEventList.setVisibility(View.VISIBLE);
                    totalCost.setText(formatCost(thisEventCost));
                    TabLayout.selectTab(tab);

                }else if(tab == TabLayout.getTabAt(1)){
                    thisEventList.setVisibility(View.GONE);
                    allEventList.setVisibility(View.VISIBLE);
                    totalCost.setText(formatCost(allEventCost));
                    TabLayout.selectTab(tab);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewModel.getLivePayments().observe(this,observer);

        back.setOnClickListener(v -> {
            finish();
        });
    }

    private void calculateCosts(List<Post> allPosts,List<Post> thisEventPosts){
        allEventCost = 0;
        thisEventCost = 0;
        //calculate total debt/outstanding money
        for (Post p:allPosts) {
            if(p.getCreator().getEmail().equals(UserData.getInstance().getEmail()))
                allEventCost += p.getCost() / p.getParticipants().size();
            else
                allEventCost -= p.getCost() / p.getParticipants().size();
        }
        for (Post p:thisEventPosts) {
            if(p.getCreator().getEmail().equals(UserData.getInstance().getEmail()))
                thisEventCost += p.getCost() / p.getParticipants().size();
            else
                thisEventCost -= p.getCost() / p.getParticipants().size();
        }

    }


    private Boolean isRelevant (Post p){
        String creatorEmail = p.getCreator().getEmail();
        String userEmail = UserData.getInstance().getEmail();
        return creatorEmail.equals(participantEmail) && p.getParticipantEmails().contains(userEmail)
                || creatorEmail.equals(userEmail) && p.getParticipantEmails().contains(participantEmail);

    }

    private void fillLists(){
        //reset lists
        allPosts = new ArrayList<>();
        thisEventPosts = new ArrayList<>();

        //populating lists with relevant data
        for (Post p:repo.getAllPosts().getAllPosts()) {
            if (p.getParticipantEmails().contains(participantEmail)){
                allPosts.add(p);
            }
        }
        if (event != null){
            for(Post p:event.getPostList())
                if(isRelevant(p)){
                    thisEventPosts.add(p);
                }
        }

        //sort by date such that newest are at the top
        Collections.sort(allPosts, Comparator.comparing(Post::getDate));
        Collections.sort(thisEventPosts, Comparator.comparing(Post::getDate));

        Collections.reverse(allPosts);
        Collections.reverse(thisEventPosts);
    }

    Observer<List<Post>> observer = new Observer<List<Post>>() {
        @Override
        public void onChanged(@Nullable List<Post> payments) {

            fillLists();

            calculateCosts(allPosts,thisEventPosts);

            allPostAdapter.update(allPosts);
            thisEventPostAdapter.update(thisEventPosts);

            if (TabLayout.getSelectedTabPosition() == 0){
                totalCost.setText(formatCost(thisEventCost));
            }else if(TabLayout.getSelectedTabPosition() == 1){

                totalCost.setText(formatCost(allEventCost));

            }

        }
    };

    private void initButtons() {
        if (participantPaypal == null || participantPaypal.equals("")){
            Toast.makeText(ParticipantDetail.this , participantName + " " + SuperApplication.getContext().getString(R.string.noPaypal), Toast.LENGTH_LONG*2);
        } else {
            paypalButton.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(participantPaypal)));
            });
        }
        paymentButton.setOnClickListener(v -> {
            PaymentDialogue dialogue = new PaymentDialogue(participantEmail);
            dialogue.show(getSupportFragmentManager(), "payment dialog");
        });
    }


    //convert double to string formatted as currency, also set fitting text colour
    private String formatCost(double cost){
        String costString = String.format("%3.2f", cost);
        costString = costString.replace(".", ",");
        if (cost > 0.0){
            costString = String.format("%s%s","+", costString);
            totalCost.setTextColor(SuperApplication.getContext().getColor(R.color.SK_darkgreen_B));
        }else if(cost < 0.0){
            totalCost.setTextColor(SuperApplication.getContext().getColor(R.color.SK_red_B));
        }else
            totalCost.setTextColor(SuperApplication.getContext().getColor(R.color.SK_grey));

        return costString + "€";
    }

    @Override
    public void ItemClicked(int pos, String string) {}

    @Override
    public void ItemClicked(int pos) {}

    @Override
    public void ItemClicked() {}

    //assign all the xml elements to variables
    private void init(){
        back = (ImageView) findViewById(R.id.participant_detail_toolbar_back);
        participantImage = (ImageView) findViewById(R.id.participant_detail_toolbar_image);
        participantName = (TextView) findViewById(R.id.participant_detail_toolbar_name);
        totalCost = (TextView) findViewById(R.id.participant_total_cost);

        paypalButton = (Button) findViewById(R.id.participant_detail_paypalbutton);
        paymentButton = (Button) findViewById(R.id.participant_detail_paymentbutton);

        TabLayout = (TabLayout) findViewById(R.id.participant_detail_tablayout);
        allEventList = (RecyclerView) findViewById(R.id.AllEventPosts);
        allEventList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        thisEventList = (RecyclerView) findViewById(R.id.ThisEventPosts);
        thisEventList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

}


