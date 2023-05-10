package com.example.money_split_f1.Event;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.money_split_f1.R;
import com.example.money_split_f1.databinding.ActivityEventCreationBinding;
import com.example.money_split_f1.Repos.EventRepository;

import java.time.LocalDate;


public class EventCreationActivity extends AppCompatActivity {

    ActivityEventCreationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEventCreationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back button

        binding.btnFinishEventCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEventCreation();
            }
        });

        binding.eventCreationDueDateButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    binding.eventCreationDueDate.setVisibility(View.VISIBLE);
                }else{
                    binding.eventCreationDueDate.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean dateInPast(){
        DatePicker datePicker = binding.eventCreationDueDate;
        LocalDate localDate = LocalDate.now();
        LocalDate givenDate = LocalDate.of(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth());

        return givenDate.isBefore(localDate);
    }

    private void checkEventCreation(){
        if (binding.tvEventName.getText().toString().length() > 2){
            LocalDate dueDate = null;
            if (binding.eventCreationDueDateButton.isChecked()){
                if (dateInPast()){
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.eventCreationPastDateError),Toast.LENGTH_SHORT).show();
                    return;
                }
                DatePicker datePicker = binding.eventCreationDueDate;
                dueDate = LocalDate.of(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth());
            }
            EventRepository eventRepo = EventRepository.getInstance();
            eventRepo.makeEventCreationRequest(binding.tvEventName.getText().toString(),dueDate, false);
            finish();

        }else{
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.eventCreationNameError),Toast.LENGTH_SHORT).show();
        }
    }


}
