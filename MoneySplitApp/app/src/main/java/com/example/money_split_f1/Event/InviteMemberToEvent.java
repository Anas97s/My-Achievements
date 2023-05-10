package com.example.money_split_f1.Event;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.money_split_f1.R;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.SuperApplication;

/**This class contains everything need to invite a user to the event, its checks also if email is valid and checks if its written in correct form.
 * and its make a request with a server to send an invite.
 *
 * @author Anas Salameh
 * */
public class InviteMemberToEvent extends AppCompatActivity {
    EditText email;
    Button invite;
    ImageView back, edit;
    TextView title;
    EventRepository eventRepository = EventRepository.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_member_to_event);

        email = (EditText) findViewById(R.id.emailInvite);
        title = (TextView) findViewById(R.id.setting_title);
        invite = (Button) findViewById(R.id.send_invite);
        back = (ImageView) findViewById(R.id.custom_toolbar_back);
        edit = (ImageView) findViewById(R.id.editPost);
        edit.setVisibility(View.GONE);
        title.setText(R.string.add_member_to_event);

        String name = getIntent().getStringExtra("title");
        String date = getIntent().getStringExtra("eventDate");
        int v_id = getIntent().getIntExtra("v_id", -1);




        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendInvite();
            }

            /**This methode is to check if email that has been written is valid or not and make network request
             * to send a invite to that email and add it to event*/
            private void sendInvite() {

                String e_mail = email.getText().toString().trim();
                String error = SuperApplication.getContext().getText(R.string.email_required).toString();

                if(!e_mail.isEmpty()){
                    if (!Patterns.EMAIL_ADDRESS.matcher(e_mail).matches()){
                        email.setError(SuperApplication.getContext().getText(R.string.invalid_email));
                        email.requestFocus();
                        return;
                    }else{
                        startActivity(new Intent(InviteMemberToEvent.this, EventUi.class));
                    }
                }
                if (e_mail.isEmpty()){
                    Toast.makeText(InviteMemberToEvent.this, error, Toast.LENGTH_LONG).show();
                    return;
                }
                eventRepository.makeAddUserToEventRequest(v_id, e_mail);
                Intent intent = new Intent(InviteMemberToEvent.this, EventUi.class);
                intent.putExtra("Name", name);
                intent.putExtra("id", v_id);
                startActivity(intent);
            }
        });

        //switch back to event Setting
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InviteMemberToEvent.this, EventSetting.class);
                intent.putExtra("title", name);
                intent.putExtra("v_id", v_id);
                intent.putExtra("eventDate", date);
                startActivity(intent);
            }
        });
    }

}