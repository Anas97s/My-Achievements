package com.example.money_split_f1.Guest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.money_split_f1.Adapters.RecyclerViewClickListener;
import com.example.money_split_f1.Adapters.eventListAdapter;
import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.EventList;
import com.example.money_split_f1.LocalData.DataFile;
import com.example.money_split_f1.R;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.databinding.FragmentEventGuestBinding;
import com.example.money_split_f1.ui.startScreen.EventViewModel;

import java.util.List;


public class EventGuestFragment extends Fragment implements RecyclerViewClickListener {
    FragmentEventGuestBinding binding;
    EventViewModel eventViewModel;
    DataFile internalData = DataFile.getEventsInstance();
    List<EventList> events = internalData.getEvents();
    eventListAdapter adapterV;

    public EventGuestFragment(){
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        binding = FragmentEventGuestBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapterV = new eventListAdapter(events,this.getContext(),this,true);

        binding.rvEventsGuest.setAdapter(adapterV);
        binding.rvEventsGuest.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.rvEventsGuest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                adapterV.closeSwipedMenus();
                return false;
            }
        });

        internalData.getLocalEventsLiveData().observe(getActivity(), new Observer<List<EventList>>() {
            @Override
            public void onChanged(List<EventList> eventLists) {
                events = eventLists;
                adapterV.updateEventList(eventLists);
                adapterV.notifyDataSetChanged();
            }
        });

        if(events == null){
            internalData.initialize();
            events = internalData.getEvents();
        }else {
            events = DataFile.checkOverdue(events);
            internalData.setEvents(events);
        }

    }

    private EventData getEventFromId(int eventID){
        for (EventList eventList: events ) {
            EventData event = eventList.getEventById(eventID);
            if (event != null) return event;
        }
        return null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Initialize the contents of the Fragment host's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {@link #setHasOptionsMenu}.  See
     * {@link Activity#onCreateOptionsMenu(Menu) Activity.onCreateOptionsMenu}
     * for more information.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     * @deprecated {@link ComponentActivity} now implements {@link MenuHost},
     * an interface that allows any component, including your activity itself, to add menu items
     * by calling {@link #addMenuProvider(MenuProvider)} without forcing all components through
     * this single method override. As this provides a consistent, optionally {@link Lifecycle}
     * -aware, and modular way to handle menu creation and item selection, replace usages of this
     * method with one or more calls to {@link #addMenuProvider(MenuProvider)} in your Activity's
     * {@link #onCreate(Bundle)} method, having each provider override
     * {@link MenuProvider#onCreateMenu(Menu, MenuInflater)} to create their menu items.
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        MenuItem menuItem = menu.findItem(R.id.account);
        menuItem.setVisible(true);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();


        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {


                    EventRepository repo = EventRepository.getInstance();
                    List<EventList> events = repo.getEventList().getValue();

                    if (query.isEmpty()) return false;

                    for (int i = 0; i < 4; i++) {
                        List<EventData> current = events.get(i).getEvents();
                        for (int j = 0; j < current.size(); j++) {
                            if (current.get(j).getName().equals(query)) {
                                Intent intent = new Intent(getContext(), EventGuestUi.class);
                                intent.putExtra("id", current.get(j).getEventID());
                                intent.putExtra("title", query);
                                startActivity(intent);
                            }
                        }

                    }

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (adapterV != null){
                        adapterV.getFilter().filter(newText);
                    }
                    return false;
                }
            });
            super.onCreateOptionsMenu(menu,inflater);
        }
    }

    @Override
    public void ItemClicked(int id) {
        Intent intent = new Intent(getContext(), EventGuestUi.class);
        intent.putExtra("id", DataFile.getEventbyID(id, events).getEventID());
        intent.putExtra("title", DataFile.getEventbyID(id, events).getName());

        startActivity(intent);
    }

    @Override
    public void ItemClicked(int id, String notificationId) {
        EventData eventData = getEventFromId(id);
        if (eventData != null) {

        }
    }

    @Override
    public void ItemClicked() {

    }

}