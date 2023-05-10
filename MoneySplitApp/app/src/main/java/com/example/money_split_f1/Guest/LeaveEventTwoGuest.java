package com.example.money_split_f1.Guest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.EventList;
import com.example.money_split_f1.R;
import com.example.money_split_f1.LocalData.DataFile;

import java.util.List;

public class LeaveEventTwoGuest extends AppCompatActivity {
    Button yes, no;
    int eventID;
    private DataFile data = DataFile.getEventsInstance();
    List<EventList> events = data.getEvents();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_event_two_guest);

        eventID = getIntent().getIntExtra("id", -1);
        String name = DataFile.getEventbyID(eventID, events).getName();
        TextView title = (TextView) findViewById(R.id.setting_title_Guest);
        title.setText(name);
        yes = (Button) findViewById(R.id.yesLeave2Guest);
        no = (Button) findViewById(R.id.noLeave2Guest);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventData currentEvent = null;
                for(int i = 0; i < 4; i++){
                    currentEvent = events.get(i).getEventById(eventID);
                    if(currentEvent != null){
                        List<EventData> e = events.get(i).getEvents();
                        e.remove(currentEvent);
                        events.get(i).setEvents(e);
                        data.setEvents(events);
                        break;
                    };
                }
                if(currentEvent == null){
                    Toast.makeText(LeaveEventTwoGuest.this, "error: eventId not found", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(LeaveEventTwoGuest.this, GuestActivity.class));
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaveEventTwoGuest.this, EventGuestUi.class));
            }
        });



    }
}