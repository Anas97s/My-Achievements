package com.example.money_split_f1.Guest;


import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.example.money_split_f1.Event.Data.EventList;
import com.example.money_split_f1.R;
import com.example.money_split_f1.LocalData.DataFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class EventSettingGuest extends AppCompatActivity {
    Button changePic, addMember, shareLink, eventDone, leaveEvent, changeDate, setDate;
    TextView endDate, title, endDateText;
    ImageView  back, add, eventPic;
    CalendarView calendarView;
    String dateText = "";
    LocalDate setterDate;
    EventData currentEvent;
    int eventID;
    private DataFile data = DataFile.getEventsInstance();
    List<EventList> events = data.getEvents();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_settings_guest);

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());

        String language = "de";
        setLocale(language);

        changePic = (Button) findViewById(R.id.changeEventPicGuest);
        addMember = (Button) findViewById(R.id.addMemberGuest);
        shareLink = (Button) findViewById(R.id.shareLinkGuest);
        eventDone = (Button) findViewById(R.id.eventDoneGuest);
        changeDate =(Button) findViewById(R.id.changeDateGuest);
        leaveEvent = (Button) findViewById(R.id.leaveEventGuest);
        setDate = (Button) findViewById(R.id.setDateTestGuest);

        endDate = (TextView) findViewById(R.id.editEndDateGuest);
        title = (TextView) findViewById(R.id.setting_title_Guest);
        endDateText = (TextView) findViewById(R.id.endDateGuest);


        back = (ImageView) findViewById(R.id.event_back_toolbar_Guest);
        add = (ImageView) findViewById(R.id.addPostGuest);
        eventPic = (ImageView) findViewById(R.id.eventImageGuest);

        calendarView = (CalendarView) findViewById(R.id.calendarView3);

        eventID = getIntent().getIntExtra("id", -1);
        currentEvent = DataFile.getEventbyID(eventID, events);


        add.setVisibility(View.GONE);
        LocalDate serverDate = currentEvent.getDueDate();// DataFile.getEventbyID(eventID,events).getDueDate();
        String dateToShow = getDeDate(serverDate);



        endDate.setText(dateToShow);
        String title_ = getIntent().getStringExtra("title");
        title.setText(title_);


        //hide calendar and buttons of calendar on main view
        calendarView.setVisibility(View.GONE);
        setDate.setVisibility(View.GONE);

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
                setDate.setVisibility(View.VISIBLE);

                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                        dateText = year + "-" + (month + 1) + "-" + day;
                        if (year < LocalDate.now().getYear()){
                            Toast.makeText(EventSettingGuest.this, "Datum ungültig", Toast.LENGTH_SHORT).show();
                            setDate.setVisibility(View.GONE);
                        }else if((year >= LocalDate.now().getYear() && month + 1 < LocalDate.now().getMonthValue())){
                            Toast.makeText(EventSettingGuest.this, "Datum ungültig", Toast.LENGTH_SHORT).show();
                            setDate.setVisibility(View.GONE);
                        }else if((year == LocalDate.now().getYear() && month + 1 == LocalDate.now().getMonthValue() && day < LocalDate.now().getDayOfMonth())){
                            Toast.makeText(EventSettingGuest.this, "Datum ungültig", Toast.LENGTH_SHORT).show();
                            setDate.setVisibility(View.GONE);
                        }else{
                            setDate.setVisibility(View.VISIBLE);
                            setterDate = LocalDate.of(year, month + 1, day);
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

                DataFile.getEventbyID(eventID, events).setDueDate(setterDate);
                data.setEvents(events);
                currentEvent.setDueDate(setterDate);
                endDate.setText(getDeDate(currentEvent.getDueDate()));
            }
        });

        leaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventSettingGuest.this, LeaveEventOneGuest.class);
                intent.putExtra("id", eventID);
                intent.putExtra("name", DataFile.getEventbyID(eventID, events).getName());
                startActivity(intent);
            }
        });

        eventDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //determine in which category the event is right now
                int i = 0;
                for(; i < events.size(); i++){
                    if(events.get(i).getEventById(eventID) != null){
                        break;
                    }
                }

                List<EventData> open = events.get(i).getEvents();
                EventData currentEvent = events.get(i).getEventById(eventID);
                open.remove(currentEvent);
                events.get(0).setEvents(open);

                List<EventData> done = events.get(2).getEvents();
                if(done == null || done.isEmpty()) {
                    done = new ArrayList<EventData>();
                }
                done.add(currentEvent);
                events.get(2).setEvents(done);
                data.setEvents(events);
                startActivity(new Intent(EventSettingGuest.this, GuestActivity.class));

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventSettingGuest.this, EventGuestUi.class);
                intent.putExtra("id", eventID);
                intent.putExtra("title", title_);
                startActivity(intent);
            }
        });
    }

    private void setLocale(String language) {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(language);

        resources.updateConfiguration(configuration,metrics);
        onConfigurationChanged(configuration);
    }

    private String getDeDate(LocalDate date){
        String resultDate = "";
        if(date == null){
            return "00.00.0000";
        }
        int day = date.getDayOfMonth();
        if (day < 10){
            resultDate += "0" + day + ".";
        }else{
            resultDate += day + ".";
        }

        int month = date.getMonthValue();
        if (month < 10){
            resultDate += "0" + month + ".";
        }else{
            resultDate += month + ".";
        }

        return resultDate + date.getYear();

    }
}
