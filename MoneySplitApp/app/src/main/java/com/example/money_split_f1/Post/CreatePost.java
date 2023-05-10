package com.example.money_split_f1.Post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
import com.example.money_split_f1.Event.EventUi;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.Repos.SharedPrefsRepo;
import com.example.money_split_f1.User.UserData;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**This class contains UI of creating post and Network request for creating post
 *
 * @author  Anas Salameh
 * */
public class CreatePost extends AppCompatActivity implements RecyclerViewClickListener {

    private EditText titleText, priceText, postDetailsText;
    private RecyclerView memberList, chosenMembersList;
    private TextView chosenMembersText, setting_title, postID_, membersTextRecy;
    private RadioButton allMembers, chosenMembers;
    private Button  cancelPost, sharePost, createPost, createPostCancel;
    private ImageView back, addPost, homePic, edit;
    EventRepository eventRepo = EventRepository.getInstance();
    private EventData event;
    private List<String> emails = new ArrayList<>();
    private List<Participant> participantList = new ArrayList<>();
    private List<Participant> eventParticipants = new ArrayList<>();
    UserData userData = UserData.getInstance();
    private String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        baseUrl = SharedPrefsRepo.getInstance().getServerIp();
        initView();

        String name = getIntent().getStringExtra("title");
        int EventID = getIntent().getIntExtra("v_id", -1);
        if (EventID == -1){
            //TODO: show error
            System.err.println("Failed to get event by provided EventID");
        }else {
            event = eventRepo.getEventbyID(EventID);
        }

        //to show event participant list in post.
        for (int i = 0; i < event.getParticipantList().size(); i++){
            if (!userData.getEmail().equals(event.getParticipantList().get(i).getEmail())){ //hiding post creator email from a participant list
                eventParticipants.add(new Participant(event.getParticipantList().get(i).getName(), event.getParticipantList().get(i).getEmail(), ""));
            }
        }

        ParticipantPostAdapter participantPostAdapter = new ParticipantPostAdapter(eventParticipants, this);
        memberList.setAdapter(participantPostAdapter);

        allMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenMembersText.setVisibility(View.GONE);
                chosenMembersList.setVisibility(View.GONE);
            }
        });

        chosenMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenMembersText.setVisibility(View.VISIBLE);
                chosenMembersList.setVisibility(View.VISIBLE);
                chosenMembersList.setEnabled(false);
                chosenMembersList.setClickable(false);
            }
        });

        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPost();
            }

            private void checkPost() {
                String checkTitle = titleText.getText().toString().trim();
                String checkPrice = priceText.getText().toString();
                String checkPostDetails = postDetailsText.getText().toString().trim();

                if (checkTitle.isEmpty()){
                    titleText.setError(SuperApplication.getContext().getResources().getString(R.string.title_required));
                    titleText.requestFocus();
                    return;
                }

                if (checkPrice.isEmpty()){
                    priceText.setError(SuperApplication.getContext().getResources().getString(R.string.price_required));
                    priceText.requestFocus();
                    return;
                }

                if (checkPostDetails.isEmpty()){
                    checkPostDetails = SuperApplication.getContext().getResources().getString(R.string.no_description);
                }

                makeCreatePostRequest(checkTitle, checkPrice, checkPostDetails, EventID);
                Toast.makeText(CreatePost.this, SuperApplication.getContext().getResources().getString(R.string.post_has_been_created), Toast.LENGTH_SHORT).show();
                showHidden();
            }
        });

        createPostCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreatePost.this, EventUi.class);
                intent.putExtra("Name", name);
                intent.putExtra("id", EventID);
                startActivity(intent);
            }
        });


        sharePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidPost();
            }

            private void checkValidPost() {
                if (!allMembers.isChecked() && !chosenMembers.isChecked()){
                    String message = "Wähle unter wem soll der Post aufgeteilt werden, Bitte!";
                    Toast.makeText(CreatePost.this, message, Toast.LENGTH_LONG).show();
                    return;
                }

                if (chosenMembers.isChecked() && emails.isEmpty()){
                    Toast.makeText(CreatePost.this, "mindestens muss ein Teilnehmer ausgewählt werden!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (allMembers.isChecked() && !chosenMembers.isChecked()){
                    for (int i = 0; i < event.getParticipantList().size() ; i++){
                        emails.add(event.getParticipantList().get(i).getEmail());
                    }
                }

                int id = Integer.parseInt(postID_.getText().toString()); // post ID

                for (int i = 0; i < emails.size(); i++){
                    makeAddUserToPostRequest(id, emails.get(i));
                }

                EventRepository.getInstance().updateEventList();

                Intent intent = new Intent(CreatePost.this, EventUi.class);
                intent.putExtra("id", EventID);
                intent.putExtra("Name", name);
                startActivity(intent);
            }
        });

        //cancel creating post (delete post)
        cancelPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(postID_.getText().toString()); // post ID
                makeDeletePostRequest(id);
                Toast.makeText(CreatePost.this, "Post wurde gelöscht!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreatePost.this, EventUi.class);
                intent.putExtra("id", EventID);
                intent.putExtra("Name", name);
                startActivity(intent);
            }
        });

    }


    /**This methode creates a network request with the server to create new post
     * @param title title of Post
     * @param amount amount of Post to be paid
     * @param description description for what is this post for
     * @param eventID id of event
     **/
    public void makeCreatePostRequest(String title, String amount, String description, int eventID){
        String url = SuperApplication.getContext().getResources().getString(R.string.api_create_post,baseUrl);
        JSONObject object = new JSONObject();
        try {
            object.put("name", title);
            object.put("amount", amount);
            object.put("description", description);
            object.put("v_id", eventID);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                postID_.setText(response.opt("p_id").toString());
                EventRepository.getInstance().updateEventList();
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

        RequestQueue queue = Volley.newRequestQueue(CreatePost.this);
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

    /**This methode is only for initilize the View of UI*/
    private void initView(){
        //Edit Texts
        titleText = (EditText) findViewById(R.id.titleText);
        priceText = (EditText) findViewById(R.id.priceText);
        postDetailsText = (EditText) findViewById(R.id.postDetailsText);

        //Text View
        chosenMembersText = (TextView) findViewById(R.id.chosenMembersText);
        setting_title = (TextView) findViewById(R.id.setting_title);
        postID_ = (TextView)  findViewById(R.id.postID);
        membersTextRecy = (TextView) findViewById(R.id.membersText);

        //Radio buttons
        allMembers = (RadioButton) findViewById(R.id.allMembers);
        chosenMembers = (RadioButton) findViewById(R.id.chosenMembers);

        //Buttons
        cancelPost =(Button) findViewById(R.id.cancelPost);
        sharePost = (Button) findViewById(R.id.sharePost);
        createPost = (Button) findViewById(R.id.postErstellen);
        createPostCancel = (Button) findViewById(R.id.postAbbrechen);

        //Image View
        back = (ImageView) findViewById(R.id.custom_toolbar_back);
        addPost = (ImageView) findViewById(R.id.addPost);
        edit = (ImageView) findViewById(R.id.editPost);
        homePic = (ImageView) findViewById(R.id.event_settings);

        //members view
        memberList = (RecyclerView) findViewById(R.id.membersList);
        memberList.setHasFixedSize(true);
        memberList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //chosen members view
        chosenMembersList =(RecyclerView) findViewById(R.id.chosenMemebrsList);
        chosenMembersList.setHasFixedSize(true);
        chosenMembersList.setClickable(false);
        chosenMembersList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        // ---------- by default ----------
        setting_title.setText("Post erstellen");
        chosenMembersText.setVisibility(View.GONE);
        chosenMembersList.setVisibility(View.GONE);
        addPost.setVisibility(View.GONE);
        homePic.setVisibility(View.GONE);
        postID_.setVisibility(View.GONE);
        allMembers.setVisibility(View.GONE);
        chosenMembers.setVisibility(View.GONE);
        sharePost.setVisibility(View.GONE);
        cancelPost.setVisibility(View.GONE);
        memberList.setVisibility(View.GONE);
        membersTextRecy.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);
        // ---------- by default ----------

    }

    private void showHidden(){
        allMembers.setVisibility(View.VISIBLE);
        chosenMembers.setVisibility(View.VISIBLE);
        sharePost.setVisibility(View.VISIBLE);
        cancelPost.setVisibility(View.VISIBLE);
        memberList.setVisibility(View.VISIBLE);
        membersTextRecy.setVisibility(View.VISIBLE);
    }

    /**This methode is to show which user has been added to post by clicking on name
     * @param pos position of clicked item in adapter
     * */
    @Override
    public void ItemClicked(int pos) {
        String email = eventParticipants.get(pos).getEmail();
        String name = eventParticipants.get(pos).getName();
        Participant p = eventParticipants.get(pos);

        if (!isChosen(email, emails)){
            emails.add(email);
            participantList.add(new Participant(p.getName(), email, ""));
            String message = name + " wurde hinzugefügt!";
            Toast.makeText(CreatePost.this, message, Toast.LENGTH_SHORT).show();
        }else{
            emails.remove(email);
            for (int i = 0; i < participantList.size(); i++){
                if (email.equals(participantList.get(i).getEmail())){
                    participantList.remove(participantList.get(i));
                }
            }
            String removeMessage = name + " wurde gelöscht!";
            Toast.makeText(CreatePost.this, removeMessage, Toast.LENGTH_SHORT).show();
        }

        InPostParticipantAdapter inPostParticipantAdapter = new InPostParticipantAdapter(participantList);
        chosenMembersList.setAdapter(inPostParticipantAdapter);
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