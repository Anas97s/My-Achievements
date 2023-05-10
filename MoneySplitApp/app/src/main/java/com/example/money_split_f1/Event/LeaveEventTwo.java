package com.example.money_split_f1.Event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.money_split_f1.R;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.ui.startScreen.StartActivity;
import com.example.money_split_f1.User.UserData;

/**This class contains a leave event UI
 *
 * @author Anas Salameh
 * */
public class LeaveEventTwo extends AppCompatActivity {
    private ImageView back, edit, add;
    private Button yes, no;
    private TextView eventName;
    private EventRepository eventRepo = EventRepository.getInstance();
    private UserData userData = UserData.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_event_two);

        String name = getIntent().getStringExtra("title");
        int v_id = getIntent().getIntExtra("v_id", -1);
        String date = getIntent().getStringExtra("eventDate");
        boolean delete = getIntent().getBooleanExtra("delete",false);

        initView();


        eventName.setText(name);
        add.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);

        if (delete){
            TextView textView = findViewById(R.id.textView6);
            textView.setText(R.string.delete_event_question);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eventRepo.makeDeleteEventRequest(v_id);
                    startActivity(new Intent(LeaveEventTwo.this, StartActivity.class));
                }
            });
        } else {
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eventRepo.makeRemoveUserFromEventRequest(v_id, userData.getEmail());
                    startActivity(new Intent(LeaveEventTwo.this, StartActivity.class));
                }
            });
        }



        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaveEventTwo.this, EventSetting.class);
                intent.putExtra("title", name);
                intent.putExtra("id", v_id);
                intent.putExtra("eventDate", date);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaveEventTwo.this, LeaveEventOne.class);
                intent.putExtra("title", name);
                intent.putExtra("id", v_id);
                intent.putExtra("eventDate", date);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        yes = (Button) findViewById(R.id.yesLeave2);
        no = (Button) findViewById(R.id.noLeave2);
        back = (ImageView) findViewById(R.id.custom_toolbar_back);
        edit = (ImageView) findViewById(R.id.editPost);
        add = (ImageView) findViewById(R.id.addPost);
        eventName = (TextView) findViewById(R.id.setting_title);
    }
}