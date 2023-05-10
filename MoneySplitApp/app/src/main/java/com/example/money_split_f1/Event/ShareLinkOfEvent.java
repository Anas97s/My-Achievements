package com.example.money_split_f1.Event;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.money_split_f1.R;

public class ShareLinkOfEvent extends AppCompatActivity {
    Button copy;
    EditText eventLink;
    ImageView back, edit, add;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_link_of_event);

        copy = (Button) findViewById(R.id.copyLink);
        eventLink = (EditText) findViewById(R.id.eventLink);
        back = (ImageView) findViewById(R.id.custom_toolbar_back);
        title = (TextView) findViewById(R.id.setting_title);
        edit = (ImageView) findViewById(R.id.editPost);
        add = (ImageView) findViewById(R.id.addPost);

        title.setText("Mit Link einladen");
        edit.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        String name = getIntent().getStringExtra("title");
        int v_id = getIntent().getIntExtra("v_id", -1);
        String date = getIntent().getStringExtra("eventDate");


        eventLink.setInputType(InputType.TYPE_NULL); //NOT CHANGEABLE!
        eventLink.setText(name);




        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyLink();
            }

            private void copyLink() {
                String done = "Kopiert!";
                String eventsLink = eventLink.getText().toString();
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("data", eventsLink);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(ShareLinkOfEvent.this, done, Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShareLinkOfEvent.this, EventSetting.class);
                intent.putExtra("title", name);
                intent.putExtra("v_id", v_id);
                intent.putExtra("eventDate", date);
                startActivity(intent);
            }
        });

    }
}