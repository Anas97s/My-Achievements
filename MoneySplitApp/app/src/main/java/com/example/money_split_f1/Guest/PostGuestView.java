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
import com.example.money_split_f1.Event.Data.Participant;
import com.example.money_split_f1.R;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;

public class PostGuestView extends AppCompatActivity  implements RecyclerViewClickListener {
    ImageView back, setting, add, postCreaterpic, imageInPost;
    TextView postCreaterName, dateOfPost, pricePost, postDetails, title;
    TextInputLayout detailsPostLayout;
    RecyclerView postMembers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_guest_view);
        intiView();

        int v_id = getIntent().getIntExtra("v_id", -1);
        String v_title = getIntent().getStringExtra("v_title");

        String postTitle = getIntent().getStringExtra("p_title");
        String details = getIntent().getStringExtra("details");
        String price = getIntent().getStringExtra("price");
        String event_name = getIntent().getStringExtra("v_name");

        title.setText(postTitle);
        postCreaterName.setText("Gast");
        pricePost.setText(price + "€");
        dateOfPost.setText(event_name);
        postDetails.setText(details);

        List<Participant> participants = new ArrayList<>();
        participants.add(new Participant("Gast", " "));

        GuestParticipantAdapter guestParticipantAdapter = new GuestParticipantAdapter(participants, this);
        postMembers.setAdapter(guestParticipantAdapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostGuestView.this, EventGuestUi.class);
                intent.putExtra("id", v_id);
                intent.putExtra("title", v_title);
                startActivity(intent);
            }
        });
    }

    private void intiView() {
        postCreaterpic = (ImageView) findViewById(R.id.postCreaterPicGuest);
        back = (ImageView) findViewById(R.id.event_back_toolbar_Guest);
        setting = (ImageView) findViewById(R.id.event_settings_Guest);
        add = (ImageView) findViewById(R.id.addPostGuest);

        title = (TextView) findViewById(R.id.setting_title_Guest);
        postCreaterName = (TextView) findViewById(R.id.postCreaterNameGuest);
        dateOfPost = (TextView) findViewById(R.id.dateOfPostGuest);
        pricePost = (TextView) findViewById(R.id.pricePostGuest);
        postDetails = (TextView) findViewById(R.id.postDetailsGuest);

        detailsPostLayout = (TextInputLayout) findViewById(R.id.detailsPostLayoutGuest);

        postMembers = (RecyclerView) findViewById(R.id.postMembersGuest);
        postMembers.setHasFixedSize(true);
        postMembers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        setting.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        postCreaterName.setEnabled(false);
        dateOfPost.setEnabled(false);
        pricePost.setEnabled(false);
        postDetails.setEnabled(false);
        detailsPostLayout.setEnabled(false);
        postCreaterpic.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public void ItemClicked(int pos) {

    }

    @Override
    public void ItemClicked(int pos, String notificationId) {

    }

    @Override
    public void ItemClicked() {

    }
}