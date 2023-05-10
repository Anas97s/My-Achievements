package com.example.money_split_f1.Event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.R;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.ui.startScreen.StartActivity;
import com.example.money_split_f1.User.UserData;
/**This class contains a view for moving event to done
 *
 * @author  Anas Salameh*/
public class MoveToDoneEvent extends AppCompatActivity {

    Button yes, no;
    ImageView back, add;
    TextView title;
    EventRepository eventRepository = EventRepository.getInstance();
    UserData userData = UserData.getInstance();
    EventData event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_to_done_event);

        String name = getIntent().getStringExtra("title");
        int v_id = getIntent().getIntExtra("v_id", -1);
        String date = getIntent().getStringExtra("eventDate");
        String serverDate = getIntent().getStringExtra("serverDate");

        initView();

        title.setText(name);
        event = eventRepository.getEventbyID(v_id);
        String ownerEmail = event.getOwner().getEmail();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userData.getEmail().equals(ownerEmail)){
                    eventRepository.makeChangeEventRequest(v_id, name, serverDate, true);
                    Intent intent = new Intent(MoveToDoneEvent.this, StartActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MoveToDoneEvent.this, SuperApplication.getContext().getText(R.string.noPermission), Toast.LENGTH_SHORT).show();
                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoveToDoneEvent.this, EventUi.class);
                intent.putExtra("id", v_id);
                intent.putExtra("Name", name);
                startActivity(intent);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MoveToDoneEvent.this, EventSetting.class);
               intent.putExtra("title", name);
               intent.putExtra("v_id", v_id);
               intent.putExtra("eventDate", date);
               startActivity(intent);
            }
        });
    }

    /**This methode is only for initilize the View of UI*/
    private void initView() {
        yes = (Button) findViewById(R.id.yesMove);
        no = (Button) findViewById(R.id.noMove);
        title = (TextView) findViewById(R.id.setting_title);
        back = (ImageView) findViewById(R.id.custom_toolbar_back);
        add = (ImageView) findViewById(R.id.addPost);
        add.setVisibility(View.GONE);
    }
}