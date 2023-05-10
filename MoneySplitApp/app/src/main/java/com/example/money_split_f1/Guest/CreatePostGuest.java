package com.example.money_split_f1.Guest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.R;
import com.example.money_split_f1.LocalData.DataFile;

import java.util.List;

public class CreatePostGuest extends AppCompatActivity {
    private ImageView back, home, plus, bill;
    private Button cancel, createPost;
    private TextView activityTitle;
    private EditText title, price, details;
    private String eventTitle;
    private int v_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_guest);
        initView();
        v_id = getIntent().getIntExtra("eventID", -1);
        eventTitle = getIntent().getStringExtra("title");

        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidPost();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreatePostGuest.this, EventGuestUi.class);
                intent.putExtra("id", v_id);
                intent.putExtra("title", eventTitle);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreatePostGuest.this, EventGuestUi.class);
                intent.putExtra("id", v_id);
                intent.putExtra("title", eventTitle);
                startActivity(intent);
            }
        });
    }

    private void checkValidPost() {
        DataFile data = DataFile.getPostsInstance();
        List<Post> posts = data.getPosts();
        int id = data.getNewPostID();
        String t = title.getText().toString().trim();
        String p = price.getText().toString();
        String d = details.getText().toString().trim();
        double PRICE;

        if (t.isEmpty()){
            title.setError("Titel ist erforderlich*");
            title.requestFocus();
            return;
        }

        if (p.isEmpty()){
            price.setError("Wert ist erforderlich*");
            price.requestFocus();
            return;
        }else{
            PRICE = Double.parseDouble(p);
        }

        if (d.isEmpty()){
            d = "Keine Beschreibung!";
        }
        Post newPost = new Post(t, PRICE, d);
        newPost.setOriginEventName(eventTitle);
        posts.add(newPost);
        data.setPosts(posts, v_id);
        Intent intent = new Intent(CreatePostGuest.this, EventGuestUi.class);
        intent.putExtra("id", v_id);
        intent.putExtra("title", eventTitle);
        startActivity(intent);

    }

    private void initView(){
        back = (ImageView) findViewById(R.id.event_back_toolbar_Guest);
        home = (ImageView) findViewById(R.id.event_settings_Guest);
        plus = (ImageView) findViewById(R.id.addPostGuest);
        bill = (ImageView) findViewById(R.id.billUploadGuest);

        createPost = (Button) findViewById(R.id.postCreateGuest);
        cancel = (Button) findViewById(R.id.postCancelGuest);

        activityTitle = (TextView) findViewById(R.id.setting_title_Guest);
        activityTitle.setText("Post erstellen");

        title = (EditText) findViewById(R.id.titleTextGuest);
        price = (EditText) findViewById(R.id.priceTextGuest);
        details = (EditText) findViewById(R.id.postDetailsTextGuest);

        home.setVisibility(View.GONE);
        plus.setVisibility(View.GONE);
    }
}