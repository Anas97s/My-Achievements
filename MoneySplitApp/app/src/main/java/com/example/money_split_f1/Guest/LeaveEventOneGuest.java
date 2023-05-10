package com.example.money_split_f1.Guest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.money_split_f1.R;

public class LeaveEventOneGuest extends AppCompatActivity {
    Button yes, no;
    int eventID;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_event_one_guest);

        eventID = getIntent().getIntExtra("id",-1);

        TextView title = (TextView) findViewById(R.id.setting_title_Guest);
        title.setText(getIntent().getStringExtra("name"));
        yes = (Button) findViewById(R.id.yesLeave1Guest);
        no = (Button) findViewById(R.id.noLeave1Guest);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaveEventOneGuest.this, LeaveEventTwoGuest.class);
                intent.putExtra("id", eventID);
                startActivity(intent);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaveEventOneGuest.this, EventGuestUi.class);
                intent.putExtra("id", eventID);
                startActivity(intent);
            }
        });
    }
}