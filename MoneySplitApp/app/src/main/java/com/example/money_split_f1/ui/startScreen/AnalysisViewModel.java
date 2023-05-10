package com.example.money_split_f1.ui.startScreen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.money_split_f1.Event.Data.EventList;
import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.Event.Data.PostUserList;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.Repos.EventRepository;
import com.github.mikephil.charting.data.BarEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnalysisViewModel extends ViewModel {
    private EventRepository eventRepo = EventRepository.getInstance();
    private MutableLiveData<List<EventList>> eventList;
    private PostUserList postList;
    private static final String[] dropdown = SuperApplication.getContext().getResources().getStringArray(R.array.filter);

    public AnalysisViewModel() {

    }

    /**
     * Returns values for bar chart of all posts the user is involved in
     * @return values of all posts (no date limit)
     */
    public ArrayList<BarEntry> getAllTimeBarEntries(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        postList = eventRepo.getAllPosts();
        List<Post> userCreated = postList.getUserCreated();
        List<Post> userParticipates = postList.getUserParticipates();

        float created = 0;

        for(int i = 0; !userCreated.isEmpty() && i < userCreated.size(); i++){
            created += (float) userCreated.get(i).getCost();
        }

        float participates = 0;

        for(int i = 0; !userParticipates.isEmpty() && i < userParticipates.size(); i++){
            participates += (float) userParticipates.get(i).getCost();
        }

        BarEntry plus = new BarEntry(1, created);
        BarEntry minus = new BarEntry(2, participates);
        barEntries.add(plus);
        barEntries.add(minus);

        return barEntries;
    }

    /** Returns Entries for BarChart of a specific Eventslist
     * @param list 0 for open, 1 for overdue, 2 for done, 3 for archived
     * @return ArrayList of BarEntries
     */
    public ArrayList<BarEntry> getListBarEntries(int list){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        postList = eventRepo.getAllPostsFrom(list);
        List<Post> userCreated = postList.getUserCreated();
        List<Post> userParticipates = postList.getUserParticipates();

        float created = 0;

        for(int i = 0; !userCreated.isEmpty() && i < userCreated.size(); i++){
            created += (float) userCreated.get(i).getCost();
        }

        float participates = 0;

        for(int i = 0; !userParticipates.isEmpty() && i < userParticipates.size(); i++){
            participates += (float) userParticipates.get(i).getCost();
        }

        BarEntry plus = new BarEntry(1, created);
        BarEntry minus = new BarEntry(2, participates);
        barEntries.add(plus);
        barEntries.add(minus);

        return barEntries;
    }

    /**
     * Get BarEntries for the last x days.
     * @param days number of days that should be included in chart entries
     * @return
     */
    public ArrayList<BarEntry> getLimitedBarEntries(long days){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        postList = eventRepo.getAllPosts();
        List<Post> userCreated = new ArrayList<>();
        List<Post> userParticipates = new ArrayList<>();
        LocalDate minimum = LocalDate.now().minusDays(days);


        for(int i = 0; i < postList.getUserCreated().size(); i++){
            LocalDate date = postList.getUserCreated().get(i).getDate();
            if(date != null && date.isAfter(minimum)){
                userCreated.add(postList.getUserCreated().get(i));
            }
        }

        for(int i = 0; i < postList.getUserParticipates().size(); i++){
            LocalDate date = postList.getUserParticipates().get(i).getDate();
            if(date != null && date.isAfter(minimum)){
                userParticipates.add(postList.getUserParticipates().get(i));
            }
        }

        float created = 0;

        for(int i = 0; !userCreated.isEmpty() && i < userCreated.size(); i++){
            created += (float) userCreated.get(i).getCost();
        }

        float participates = 0;

        for(int i = 0; !userParticipates.isEmpty() && i < userParticipates.size(); i++){
            participates += (float) userParticipates.get(i).getCost();
        }

        BarEntry plus = new BarEntry(1, created);
        BarEntry minus = new BarEntry(2, participates);
        barEntries.add(plus);
        barEntries.add(minus);

        return barEntries;
    }

    /**
     * Dummy Entries for Test purposes
     */
    public ArrayList<BarEntry> getDummyEntries(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for(int i = 1; i <= 2; i++){
            BarEntry barEntry = new BarEntry(i, (float) 10 / i);
            barEntries.add(barEntry);
        }

        return barEntries;
    }

    /** Get labels for dropdown menu */
    public String[] getDropdownLabels(){ return dropdown;}

}
