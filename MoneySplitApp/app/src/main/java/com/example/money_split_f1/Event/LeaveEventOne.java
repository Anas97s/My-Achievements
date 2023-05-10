package com.example.money_split_f1.Event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.money_split_f1.R;
/**This class contains a leave event UI
 *
 * @author Anas Salameh
 * */
public class LeaveEventOne extends AppCompatActivity {
    ImageView back, edit, add;
    Button yes, no;
    TextView eventName;
    boolean delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_event_one);


        String name = getIntent().getStringExtra("title");
        int v_id = getIntent().getIntExtra("v_id", -1);
        String date = getIntent().getStringExtra("eventDate");
        delete = getIntent().getBooleanExtra("delete", false);

        back = (ImageView) findViewById(R.id.custom_toolbar_back);
        edit = (ImageView) findViewById(R.id.editPost);
        yes = (Button) findViewById(R.id.yesLeave1);
        no = (Button) findViewById(R.id.noLeave1);
        add = (ImageView) findViewById(R.id.addPost);
        eventName = (TextView) findViewById(R.id.setting_title);

        eventName.setText(name);
        add.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaveEventOne.this, LeaveEventTwo.class);
                intent.putExtra("title", name);
                intent.putExtra("v_id", v_id);
                intent.putExtra("eventDate", date);
                intent.putExtra("delete",delete);
                startActivity(intent);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LeaveEventOne.this, EventSetting.class);
                intent.putExtra("title", name);
                intent.putExtra("v_id", v_id);
                intent.putExtra("eventDate", date);
                startActivity(intent);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LeaveEventOne.this, EventSetting.class);
                intent.putExtra("title", name);
                intent.putExtra("v_id", v_id);
                intent.putExtra("eventDate", date);
                startActivity(intent);
            }
        });
    }
}