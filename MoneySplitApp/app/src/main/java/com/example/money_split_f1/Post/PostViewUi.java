package com.example.money_split_f1.Post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.money_split_f1.Adapters.PostViewAdapter;
import com.example.money_split_f1.Adapters.RecyclerViewClickListener;
import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.Event.EventUi;
import com.example.money_split_f1.R;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.User.UserData;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.List;

public class PostViewUi extends AppCompatActivity implements RecyclerViewClickListener {
    ImageView back, setting, add, postCreaterpic, editPost;
    TextView postCreaterName, dateOfPost, pricePost, postDetails, title;
    RecyclerView postMembers;
    TextInputLayout detailsPostLayout;
    EventRepository event = EventRepository.getInstance();
    Post post;
    UserData userData = UserData.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        String name = getIntent().getStringExtra("Name");
        String postID = getIntent().getStringExtra("post_id");
        int v_id = getIntent().getIntExtra("eventID", -1);

        List<Post> postList = event.getEventbyID(v_id).getPostList();
        for (int i = 0; i < postList.size(); i++){
            if (postList.get(i).getPostID().equals(postID)){
                post = postList.get(i);
            }
        }

        String sDate = getDeDate(post.getDate());


        //----- default -----
        initView();
        title.setText(post.getTitle());
        postCreaterName.setText(post.getCreator().getName());
        dateOfPost.setText(sDate);
        pricePost.setText(String.valueOf(post.getCost()) + " €");
        postDetails.setText(post.getDescription());
        //----- default -----




        editPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostViewUi.this, EditPost.class);
                intent.putExtra("Name", name);
                intent.putExtra("title", post.getTitle());
                intent.putExtra("creator_name", post.getCreator().getName());
                intent.putExtra("date", sDate);
                intent.putExtra("price", String.valueOf(post.getCost()));
                intent.putExtra("details", post.getDescription());
                intent.putExtra("post_id", post.getPostID());
                intent.putExtra("eventID", v_id);
                startActivity(intent);
            }
        });

        //if the logged-in user is a post creator
        if(userData.getEmail().equals(post.getCreator().getEmail())){
            AdminView();
        }

        PostViewAdapter postViewAdapter = new PostViewAdapter(post, this);
        postMembers.setAdapter(postViewAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostViewUi.this, EventUi.class);
                intent.putExtra("Name", name);
                intent.putExtra("id", v_id);
                startActivity(intent);
            }
        });

    }

    private void initView() {
        postCreaterpic = (ImageView) findViewById(R.id.postCreaterPic);
        back = (ImageView) findViewById(R.id.custom_toolbar_back);
        setting = (ImageView) findViewById(R.id.event_settings);
        add = (ImageView) findViewById(R.id.addPost);

        title = (TextView) findViewById(R.id.setting_title);
        postCreaterName = (TextView) findViewById(R.id.postCreaterName);
        dateOfPost = (TextView) findViewById(R.id.dateOfPost);
        pricePost = (TextView) findViewById(R.id.pricePost);
        postDetails = (TextView) findViewById(R.id.postDetails);
        postMembers = (RecyclerView) findViewById(R.id.postMembers);
        postMembers.setHasFixedSize(true);
        postMembers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        detailsPostLayout = (TextInputLayout) findViewById(R.id.detailsPostLayout);
        editPost = (ImageView) findViewById(R.id.editPost);

        //by deafult
        setting.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        postCreaterName.setEnabled(false);
        dateOfPost.setEnabled(false);
        pricePost.setEnabled(false);
        postDetails.setEnabled(false);
        detailsPostLayout.setEnabled(false);
        editPost.setVisibility(View.GONE);
        postCreaterpic.setImageResource(R.mipmap.ic_launcher);

    }

    private void AdminView(){
        editPost.setVisibility(View.VISIBLE);
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

    }

    @Override
    public void ItemClicked(int pos, String notificationId) {

    }

    @Override
    public void ItemClicked() {

    }



}