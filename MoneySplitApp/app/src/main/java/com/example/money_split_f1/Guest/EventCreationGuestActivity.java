package com.example.money_split_f1.Guest;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.EventList;
import com.example.money_split_f1.R;
import com.example.money_split_f1.databinding.ActivityEventCreationGuestBinding;
import com.example.money_split_f1.LocalData.DataFile;

import java.time.LocalDate;
import java.util.List;

public class EventCreationGuestActivity extends AppCompatActivity {

    ActivityEventCreationGuestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEventCreationGuestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back button
        binding.btnFinishEventCreationGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEventCreation();
            }
        });

        binding.eventCreationGuestDueDateButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    binding.eventCreationGuestDueDate.setVisibility(View.VISIBLE);
                }else{
                    binding.eventCreationGuestDueDate.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean dateInPast(){
        DatePicker datePicker = binding.eventCreationGuestDueDate;
        LocalDate localDate = LocalDate.now();
        LocalDate givenDate = LocalDate.of(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth());

        return givenDate.isBefore(localDate);
    }

    private void checkEventCreation(){
        if (binding.tvEventNameGuest.getText().toString().length() > 2){
            LocalDate dueDate = null;
            if (binding.eventCreationGuestDueDateButton.isChecked()){
                if (dateInPast()){
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.eventCreationPastDateError),Toast.LENGTH_SHORT).show();
                    return;
                }
                DatePicker datePicker = binding.eventCreationGuestDueDate;
                dueDate = LocalDate.of(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth());
            }
            DataFile data = DataFile.getEventsInstance();
            List<EventList> events = data.getEvents();
            //Log.d("date", dueDate.toString());
            int id = data.getNewEventID();
            events.get(0).getEvents().add(new EventData(id, binding.tvEventNameGuest.getText().toString(), dueDate));
            //Log.d("date set", events.get(0).getEventById(id).getDueDate().toString());
            data.setEvents(events);
            finish();

        }else{
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.eventCreationNameError),Toast.LENGTH_SHORT).show();
        }
    }
}