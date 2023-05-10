package com.example.money_split_f1.ui.startScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.EventList;
import com.example.money_split_f1.Event.EventUi;
import com.example.money_split_f1.Adapters.RecyclerViewClickListener;
import com.example.money_split_f1.Adapters.eventListAdapter;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.databinding.FragmentEventBinding;
import com.example.money_split_f1.Repos.EventRepository;


import java.util.List;




public class EventFragment extends Fragment implements RecyclerViewClickListener {
    EventViewModel eventViewModel;
    private FragmentEventBinding binding;
    EventRepository eventRepository = EventRepository.getInstance();


    eventListAdapter adapterV;
    public EventFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        binding = FragmentEventBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        setAdapter();

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                eventViewModel.update();
                adapterV.notifyDataSetChanged();
                adapterV.closeSwipedMenus();
                binding.swipeRefresh.setRefreshing(false);
            }
        });

        //update();

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
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint(SuperApplication.getContext().getResources().getString(R.string.search_hint));

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
                                Intent intent = new Intent(getContext(), EventUi.class);
                                intent.putExtra("Name", query);
                                intent.putExtra("id", current.get(j).getEventID());
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
        }
    }


    @Override
    public void ItemClicked(int id) {
        Intent intent = new Intent(getContext(), EventUi.class);
        intent.putExtra("Name", eventRepository.getEventbyID(id).getName());
        intent.putExtra("id", eventRepository.getEventbyID(id).getEventID());
        startActivity(intent);
    }

    @Override
    public void ItemClicked(int pos, String notificationId) {

    }

    @Override
    public void ItemClicked() {

    }


    private void setAdapter(){
        List<EventList> eventListData = eventViewModel.getMutableLiveDataEventList().getValue();
        adapterV = new eventListAdapter(eventListData,getActivity(),this);
        binding.rvEvents.setAdapter(adapterV);
        binding.rvEvents.setLayoutManager(new LinearLayoutManager(getActivity()));
        //adapterV.notifyDataSetChanged();

        binding.rvEvents.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                adapterV.closeSwipedMenus();
                return false;
            }
        });

        eventViewModel.getMutableLiveDataEventList().observe(getActivity(),eventListUpdateObserver);




    }


    Observer<List<EventList>> eventListUpdateObserver = new Observer<List<EventList>>() {
        @Override
        public void onChanged(@Nullable List<EventList> eventLists) {
            Log.d("Network","Data changed -> updating Recyclerview");
            adapterV.updateEventList(eventLists);
            adapterV.notifyDataSetChanged();
        }
    };

}