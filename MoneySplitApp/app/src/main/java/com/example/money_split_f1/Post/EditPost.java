package com.example.money_split_f1.Post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.money_split_f1.Adapters.InPostParticipantAdapter;
import com.example.money_split_f1.Adapters.ParticipantPostAdapter;
import com.example.money_split_f1.Adapters.RecyclerViewClickListener;
import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.Participant;
import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.Event.EventUi;
import com.example.money_split_f1.R;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.Repos.SharedPrefsRepo;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.User.UserData;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditPost extends AppCompatActivity implements RecyclerViewClickListener {
    private ImageView back, setting, add, postCreaterpic, imageInPost, editPost;
    private TextView postTitle, dateOfPost, pricePost, postDetails, title, memberListEdit;
    private Button save, cancel, deletePost;
    private RecyclerView postMembers, eventMembers;
    private Post post;
    UserData userData = UserData.getInstance();
    EventRepository eventRepo = EventRepository.getInstance();
    private EventData event;
    private List<String> emails = new ArrayList<>();
    private List<Participant> participants = new ArrayList<>();
    private String baseUrl;
    private List<Participant> alreadyPartici = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        initView();
        baseUrl= SharedPrefsRepo.getInstance().getServerIp();

        String name = getIntent().getStringExtra("Name");
        String titlePost = getIntent().getStringExtra("title");
        String creatorName = getIntent().getStringExtra("creator_name");
        String postDate = getIntent().getStringExtra("date");
        String postPrice = getIntent().getStringExtra("price");
        String detailsPost = getIntent().getStringExtra("details");
        String postID = getIntent().getStringExtra("post_id");
        int v_id = getIntent().getIntExtra("eventID", -1);
        int POST_ID = Integer.parseInt(postID);


        title.setText(titlePost);
        dateOfPost.setText(postDate);
        List<Post> posts = eventRepo.getEventbyID(v_id).getPostList();
        for (int i = 0; i < posts.size(); i++){
            if (posts.get(i).getPostID().equals(postID)){
                post = posts.get(i);
            }
        }

        for (int i = 0; i < post.getParticipants().size(); i++){
            alreadyPartici.add(new Participant(post.getParticipants().get(i).getName(), post.getParticipants().get(i).getEmail(), ""));
        }


        InPostParticipantAdapter inPostParticipantAdapter = new InPostParticipantAdapter(alreadyPartici);
        postMembers.setAdapter(inPostParticipantAdapter);


        event = eventRepo.getEventbyID(v_id);
        for (int i = 0; i < event.getParticipantList().size(); i++){
            int j;
            for (j = 0; j < post.getParticipants().size(); j++){
                if (event.getParticipantList().get(i).getName().equals(post.getParticipants().get(j).getName())){
                    break;
                }
            }
            if (j == post.getParticipants().size()) {
                participants.add(new Participant(event.getParticipantList().get(i).getName(), event.getParticipantList().get(i).getEmail()));
            }
        }

        ParticipantPostAdapter postMemberAdapter = new ParticipantPostAdapter(participants, this);
        eventMembers.setAdapter(postMemberAdapter);

        if (participants.isEmpty()){
            memberListEdit.setVisibility(View.GONE);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidInput();
            }

            private void checkValidInput() {
                String newTitle = postTitle.getText().toString().trim();
                String newPrice = pricePost.getText().toString();
                String newDetails = postDetails.getText().toString().trim();

                if (newTitle.isEmpty()){
                    postTitle.setError(SuperApplication.getContext().getResources().getString(R.string.title_required));
                    postTitle.requestFocus();
                    return;
                }

                if (newPrice.isEmpty()){
                    pricePost.setError(SuperApplication.getContext().getResources().getString(R.string.price_required));
                    pricePost.requestFocus();
                    return;
                }

                if (newDetails.isEmpty()){
                    newDetails = detailsPost;
                }

                if (!emails.isEmpty()){
                    for (int i = 0; i < emails.size(); i++){
                        makeAddUserToPostRequest(POST_ID, emails.get(i));
                    }
                }

                makeChangePostRequest(POST_ID, newTitle, newPrice, newDetails);

                Intent intent = new Intent(EditPost.this, EventUi.class);
                intent.putExtra("Name", name);
                intent.putExtra("id", v_id);
                startActivity(intent);
            }
        });

        deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeDeletePostRequest(POST_ID);

                Intent intent = new Intent(EditPost.this, EventUi.class);
                intent.putExtra("Name", name);
                intent.putExtra("id", v_id);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditPost.this, PostViewUi.class);
                intent.putExtra("Name", name);
                intent.putExtra("title", titlePost);
                intent.putExtra("creator_name", creatorName);
                intent.putExtra("date", postDate);
                intent.putExtra("price", postPrice);
                intent.putExtra("details", detailsPost);
                intent.putExtra("post_id", postID);
                intent.putExtra("eventID", v_id);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditPost.this, PostViewUi.class);
                intent.putExtra("Name", name);
                intent.putExtra("title", titlePost);
                intent.putExtra("creator_name", creatorName);
                intent.putExtra("date", postDate);
                intent.putExtra("price", postPrice);
                intent.putExtra("details", detailsPost);
                intent.putExtra("post_id", postID);
                intent.putExtra("eventID", v_id);
                startActivity(intent);
            }
        });
    }


    private void makeChangePostRequest(int postID, String title, String amount, String description){
        String url = SuperApplication.getContext().getResources().getString(R.string.api_change_post,baseUrl);

        JSONObject object = new JSONObject();
        try {
            object.put("p_id", postID);
            object.put("name", title);
            object.put("amount", amount);
            object.put("description", description);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(EditPost.this, SuperApplication.getContext().getResources().getString(R.string.post_has_been_changed), Toast.LENGTH_SHORT).show();
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
        queue.add(objectRequest);
        eventRepo.updateEventList();
    }

    /**This methode is to create a network request to delete a post
     * @param postID  id of post
     * */
    public void makeDeletePostRequest(int postID){
        String url = SuperApplication.getContext().getResources().getString(R.string.api_delete_post,baseUrl);

        JSONObject object = new JSONObject();
        try {
            object.put("p_id", postID);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(EditPost.this, SuperApplication.getContext().getResources().getString(R.string.post_has_been_deleted), Toast.LENGTH_SHORT).show();
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
        queue.add(objectRequest);
        eventRepo.updateEventList();
    }

    /**This methode create a network request and tells the server to add a user in the given Post
     * @param postID id of post
     * @param email email of the user
     * */
    public void makeAddUserToPostRequest(int postID, String email){
        String url = SuperApplication.getContext().getResources().getString(R.string.api_add_user_to_post,baseUrl);

        JSONObject object = new JSONObject();
        try {
            object.put("p_id", postID);
            object.put("email", email);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

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
        queue.add(objectRequest);
        eventRepo.updateEventList();
    }

    private void initView() {
        postCreaterpic = (ImageView) findViewById(R.id.postCreaterPicEdit);
        back = (ImageView) findViewById(R.id.custom_toolbar_back);
        setting = (ImageView) findViewById(R.id.event_settings);
        add = (ImageView) findViewById(R.id.addPost);
        editPost = (ImageView) findViewById(R.id.editPost);

        title = (TextView) findViewById(R.id.setting_title);
        postTitle = (TextView) findViewById(R.id.postTitleEdit);
        dateOfPost = (TextView) findViewById(R.id.dateOfPostEdit);
        pricePost = (TextView) findViewById(R.id.pricePostEdit);
        postDetails = (TextView) findViewById(R.id.postDetailsEdit);
        memberListEdit = (TextView) findViewById(R.id.memberListEdit);

        save = (Button) findViewById(R.id.saveEdit);
        cancel = (Button) findViewById(R.id.cancelEdit);
        deletePost = (Button) findViewById(R.id.deleteEdit);

        postMembers = (RecyclerView) findViewById(R.id.postMembersEdit);
        postMembers.setHasFixedSize(true);
        postMembers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        eventMembers = (RecyclerView) findViewById(R.id.eventMembersEdit);
        eventMembers.setHasFixedSize(true);
        eventMembers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //----- default -----
        setting.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        editPost.setVisibility(View.GONE);
        dateOfPost.setEnabled(false);
        postCreaterpic.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public void ItemClicked(int pos) {
        String email = participants.get(pos).getEmail();
        String name = participants.get(pos).getName();

        if (!isChosen(email, emails)){
            emails.add(email);
            participants.add(new Participant(name, email));
            alreadyPartici.add(new Participant(name, email));
            String message = name + " wurde hinzugefügt!";
            Toast.makeText(EditPost.this, message, Toast.LENGTH_SHORT).show();
        }else{
            emails.remove(email);
            for (int i = 0; i < alreadyPartici.size(); i++){
                if (email.equals(alreadyPartici.get(i).getEmail())){
                    alreadyPartici.remove(alreadyPartici.get(i));
                }
            }
            String removeMessage = name + " wurde gelöscht!";
            Toast.makeText(EditPost.this, removeMessage, Toast.LENGTH_SHORT).show();
        }
        InPostParticipantAdapter inPostParticipantAdapter = new InPostParticipantAdapter(alreadyPartici);
        postMembers.setAdapter(inPostParticipantAdapter);
        System.out.println(emails);

    }

    @Override
    public void ItemClicked(int pos, String notificationId) {

    }

    @Override
    public void ItemClicked() {

    }

    /**This methode checks if the given email is already in email list or not
     * @param email email to be added
     * @param emailsList a list of emails, that already has been added
     * */
    private boolean isChosen(String email, List<String> emailsList){
        for (int i = 0; i < emailsList.size(); i++){
            if (email.equals(emailsList.get(i))){
                return true;
            }
        }
        return false;
    }
}