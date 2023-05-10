package com.example.money_split_f1.Event;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.R;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.User.UserData;

import java.time.LocalDate;
import java.util.Locale;
/**This class contains every thing for event setting view, such as a buttons for Event pic, invite member to event, change end date of event, set event on done and
 * leave event
 *
 * @author  Anas Salameh
 * */
public class EventSetting extends AppCompatActivity {
    Button changePic, addMember, shareLink, eventDone, leaveEvent, changeDate, setDate;
    TextView eventName, endDate, endDateText;
    ImageView eventPic, back, add, edit;
    CalendarView calendarView;
    String dateText = "";
    EventRepository eventRepository = EventRepository.getInstance();
    boolean delete = false;
    UserData userData = UserData.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_settings);

        initView();

        String name = getIntent().getStringExtra("title");
        int v_id = getIntent().getIntExtra("v_id", -1);
        String date = getIntent().getStringExtra("eventDate");
        String serverDate = getIntent().getStringExtra("serverDate");

        EventData eventData = EventRepository.getInstance().getEventbyID(v_id);

        //default info's: event name, date and picture
        eventName.setText(name);
        endDate.setText(date);
        eventPic.setImageResource(R.mipmap.ic_launcher);
        add.setVisibility(View.GONE);

        if (eventData.getOwner().getEmail().equals(UserData.getInstance().getEmail())){
            leaveEvent.setText(R.string.deleteEvent);
            delete = true;
        }

        //invite member via Email
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventSetting.this, InviteMemberToEvent.class);
                intent.putExtra("title", name);
                intent.putExtra("v_id", v_id);
                intent.putExtra("eventDate", date);
                startActivity(intent);
            }
        });

        //should be deleted!
        shareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventSetting.this, ShareLinkOfEvent.class);
                intent.putExtra("title", name);
                intent.putExtra("v_id", v_id);
                intent.putExtra("eventDate", date);
                startActivity(intent);
            }
        });

        //set event to done
        eventDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventSetting.this, MoveToDoneEvent.class);
                intent.putExtra("title", name);
                intent.putExtra("v_id", v_id);
                intent.putExtra("serverDate", serverDate);
                intent.putExtra("eventDate", date);
                startActivity(intent);
            }
        });

        //changing event's date
        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventPic.setVisibility(View.GONE);
                endDate.setVisibility(View.GONE);
                endDateText.setVisibility(View.GONE);
                eventPic.setVisibility(View.GONE);
                changePic.setVisibility(View.GONE);
                addMember.setVisibility(View.GONE);
                shareLink.setVisibility(View.GONE);
                eventDone.setVisibility(View.GONE);
                changeDate.setVisibility(View.GONE);
                leaveEvent.setVisibility(View.GONE);
                calendarView.setVisibility(View.VISIBLE);
                
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                        dateText = year + "-" + (month + 1) + "-" + day;
                        if (year < LocalDate.now().getYear()){
                            Toast.makeText(EventSetting.this, SuperApplication.getContext().getText(R.string.invalidDate), Toast.LENGTH_SHORT).show();
                            setDate.setVisibility(View.GONE);
                        }else if((year >= LocalDate.now().getYear() && month + 1 < LocalDate.now().getMonthValue())){
                            Toast.makeText(EventSetting.this, SuperApplication.getContext().getText(R.string.invalidDate), Toast.LENGTH_SHORT).show();
                            setDate.setVisibility(View.GONE);
                        }else if((year == LocalDate.now().getYear() && month + 1 == LocalDate.now().getMonthValue() && day < LocalDate.now().getDayOfMonth())){
                            Toast.makeText(EventSetting.this, SuperApplication.getContext().getText(R.string.invalidDate), Toast.LENGTH_SHORT).show();
                            setDate.setVisibility(View.GONE);
                        }else{
                            if (userData.getEmail().equals(eventData.getOwner().getEmail())){
                                setDate.setVisibility(View.VISIBLE);
                            }else{
                                setDate.setVisibility(View.GONE);
                                Toast.makeText(EventSetting.this, SuperApplication.getContext().getText(R.string.noPermission), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            }
        });


        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.setVisibility(View.GONE);
                setDate.setVisibility(View.GONE);

                eventPic.setVisibility(View.VISIBLE);
                endDate.setVisibility(View.VISIBLE);
                endDateText.setVisibility(View.VISIBLE);
                eventPic.setVisibility(View.VISIBLE);
                changePic.setVisibility(View.VISIBLE);
                addMember.setVisibility(View.VISIBLE);
                shareLink.setVisibility(View.VISIBLE);
                eventDone.setVisibility(View.VISIBLE);
                changeDate.setVisibility(View.VISIBLE);
                leaveEvent.setVisibility(View.VISIBLE);

                eventRepository.makeChangeEventRequest(v_id, name, dateText, false);
                eventRepository.updateEventList();
                endDate.setText(dateText);
            }
        });

        //switchs to leave event view
        leaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventSetting.this, LeaveEventOne.class);
                intent.putExtra("title", name);
                intent.putExtra("v_id", v_id);
                intent.putExtra("eventDate", date);
                intent.putExtra("delete",delete);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventSetting.this, EventUi.class);
                intent.putExtra("Name", name);
                intent.putExtra("id", v_id);
                startActivity(intent);
            }
        });
    }

    /**This methode is only for initilize the View of UI*/
    private void initView() {
        String language = "de";
        setLocale(language);

        changePic = (Button) findViewById(R.id.changeEventPic);
        addMember = (Button) findViewById(R.id.addMember);
        shareLink = (Button) findViewById(R.id.shareLink);
        eventDone = (Button) findViewById(R.id.eventDone);
        changeDate =(Button) findViewById(R.id.changeDate);
        leaveEvent = (Button) findViewById(R.id.leaveEvent);
        setDate = (Button) findViewById(R.id.setDateTest);
        eventName = (TextView) findViewById(R.id.setting_title);
        endDate = (TextView) findViewById(R.id.editEndDate);
        endDateText = (TextView) findViewById(R.id.endDate);
        eventPic = (ImageView) findViewById(R.id.eventImage);
        add = (ImageView) findViewById(R.id.addPost);
        back = (ImageView) findViewById(R.id.custom_toolbar_back);
        calendarView = (CalendarView) findViewById(R.id.calendarView2);
        edit = (ImageView) findViewById(R.id.editPost);
        //hide calendar and buttons of calendar on main view
        calendarView.setVisibility(View.GONE);
        setDate.setVisibility(View.GONE);
        edit.setVisibility(View.GONE);
    }

    /**This methode to change Calendar view to chosen language
     * @param language  String name of language
     * */
    private void setLocale(String language) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(language);

        resources.updateConfiguration(configuration,metrics);
        onConfigurationChanged(configuration);
    }



}
