package com.example.money_split_f1.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.EventList;

import com.example.money_split_f1.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Observer;

public class eventListAdapter extends RecyclerView.Adapter<eventListAdapter.EventListViewHolder> implements Filterable {
    List<EventList> list = new ArrayList<>();
    private final RecyclerViewClickListener listener;
    Context context;
    List<EventList> allEventLists = new ArrayList<>();
    private boolean isGuest = false;

    public eventListAdapter(List<EventList> list,Context context ,RecyclerViewClickListener listener) {
        if (list != null){
            this.list = new ArrayList<>(list);
            this.allEventLists = new ArrayList<>(list);
        }

        this.context = context;
        this.listener = listener;

    }
    public eventListAdapter(List<EventList> list,Context context ,RecyclerViewClickListener listener,boolean isGuest) {
        if (list != null){
            this.list = new ArrayList<>(list);
            this.allEventLists = new ArrayList<>(list);
        }

        this.context = context;
        this.listener = listener;
        this.isGuest = isGuest;

    }

    /**
     * closes all Events that were swiped
     */
    public void closeSwipedMenus(){
        for (int i = 0; i < getItemCount(); i++) {
            if (list.get(i).closeSwipeMenu()){
                Log.d("EventListAdapter","closeSwipedMenus()");
                notifyItemChanged(i);
            }

        }
    }

    /**
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(EventListViewHolder, int)
     */
    @NonNull
    @Override
    public EventListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventlist_list, parent, false);
        return new EventListViewHolder(v);
    }

    /**

     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull EventListViewHolder holder, int position) {
        EventList list1= list.get(position);
        holder.category.setText(list1.getCategory());


        //EventAdapter eventAdapter  = new EventAdapter(list1.getEvents(),context,listener,list1.isArchive());

        holder.recyclerView.setAdapter(new EventAdapter(list1.getEvents(),context,listener,list1.isArchive(),isGuest));

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));

        ItemTouchHelper.SimpleCallback touchHelperCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            private final ColorDrawable background = new ColorDrawable(context.getColor(R.color.SK_lightgreen_A));

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                EventAdapter adapter = (EventAdapter) ((RecyclerView)viewHolder.itemView.getParent()).getAdapter();
                adapter.showSwipeMenu(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();
            }

            /**

             * @param c                 The canvas which RecyclerView is drawing its children
             * @param recyclerView      The RecyclerView to which ItemTouchHelper is attached to
             * @param viewHolder        The ViewHolder which is being interacted by the User or it was
             *                          interacted and simply animating to its original position
             * @param dX                The amount of horizontal displacement caused by user's action
             * @param dY                The amount of vertical displacement caused by user's action
             * @param actionState       The type of interaction on the View. Is either {@link
             *
             * @param isCurrentlyActive True if this view is currently being controlled by the user or
             *                          false it is simply animating back to its original state.
             * @see #onChildDrawOver(Canvas, RecyclerView, ViewHolder, float, float, int,
             * boolean)
             */
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if(viewHolder instanceof EventAdapter.EventViewHolder){
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    View itemView = viewHolder.itemView;

                    if (dX > 0) {
                        background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                    } else if (dX < 0) {
                        background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    } else {
                        background.setBounds(0, 0, 0, 0);
                    }

                    background.draw(c);
                }

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallBack);
        itemTouchHelper.attachToRecyclerView(holder.recyclerView);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }else {
            return 0;
        }

    }

    /**
     * updates the current list with the new list and notifies the change
     * @param list The new ListData
     */
    public void updateEventList(List<EventList> list){
        if (list == null){
            return;
        }

        //this.list.clear();
        //this.list = new ArrayList<>(list);
        this.allEventLists.clear();
        this.allEventLists = new ArrayList<>(list);

        for (int i = 0; i < getItemCount(); i++) {
            notifyItemChanged(i);
        }
        filter.filter("");

    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<EventList> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(allEventLists);
            }else{
                for (EventList eventList: allEventLists){
                    EventList tempEventList = new EventList(eventList) ;
                    List<EventData> filteredEventList = new ArrayList<>();
                    for (EventData event: eventList.getEvents()) {

                        if(event.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                            filteredEventList.add(event);
                        }
                    }
                    tempEventList.setEvents(filteredEventList);
                    filteredList.add(tempEventList);
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((Collection<? extends EventList>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class EventListViewHolder extends RecyclerView.ViewHolder {
        TextView category;
        RecyclerView recyclerView;
        CardView textCardView;
        ImageView arrowImage;

        public EventAdapter getAdapter(){
            return (EventAdapter) recyclerView.getAdapter();
        }

        public EventListViewHolder(@NonNull View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.tv_listName);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.eventList);
            textCardView = (CardView) itemView.findViewById(R.id.eventCardName);
            arrowImage = (ImageView) itemView.findViewById(R.id.down_arrow_image);

            textCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerView.getVisibility() == View.VISIBLE){
                        recyclerView.setVisibility(View.GONE);
                        arrowImage.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);

                    }else{
                        recyclerView.setVisibility(View.VISIBLE);
                        arrowImage.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);

                    }

                }
            });
        }
    }
}
