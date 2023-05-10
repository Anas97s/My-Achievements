package com.example.money_split_f1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.R;
import com.example.money_split_f1.LocalData.DataFile;
import com.example.money_split_f1.Repos.EventRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Tim oliver Wolter
 */
public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    List<EventData> list = new ArrayList<>();
    Context context;
    private boolean isArchive;
    private final RecyclerViewClickListener listener;
    List<EventData> allEvents;
    boolean isGuest = false;

    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;

    public EventAdapter( List<EventData> list, Context context, RecyclerViewClickListener listener,boolean isArchive,boolean isGuest) {
        this.list = list;
        this.context = context;
        this.listener = listener;
        this.isArchive = isArchive;
        this.allEvents = new ArrayList<>(list);
        this.isGuest = isGuest;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        if(viewType == SHOW_MENU){
            if (isArchive){
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_object_archived_slide, parent, false);
                return new EventViewHolderSwipeArchived(v);
            }
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_object_slidemenu, parent, false);
            return new EventViewHolderSwipe(v);
        }else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_object, parent, false);
            return new EventViewHolder(v,listener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EventData event = list.get(position);
        if (holder instanceof EventViewHolder) {
            ((EventViewHolder)holder).name.setText(event.getName());
            ((EventViewHolder)holder).profilePicture.setImageResource(R.mipmap.ic_launcher);

        }
        if (holder instanceof EventViewHolderSwipe){
            ((EventViewHolderSwipe)holder).profilePicture.setImageResource(R.mipmap.ic_launcher);

        }
        if (holder instanceof EventViewHolderSwipeArchived){
            ((EventViewHolderSwipeArchived)holder).profilePicture.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).isShowSwipeMenu()) {
            return SHOW_MENU;
        } else {
            return HIDE_MENU;
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void showSwipeMenu(int position) {
        closeSwipeMenu();
        list.get(position).setShowSwipeMenu(true);
        notifyDataSetChanged();

    }
    public boolean isSwipeMenuShown() {
        for (EventData listItem:
                list) {
            if (listItem.isShowSwipeMenu()){
                return true;
            }
        }
        return false;
    }

    public boolean closeSwipeMenu(){
        boolean action = false;
        for (int i = 0; i < getItemCount(); i++) {
            if (list.get(i).isShowSwipeMenu()){
                list.get(i).setShowSwipeMenu(false);
                notifyItemChanged(i);
                action = true;
            }
        }
        return action;
    }

    public void setList(List<EventData> events){
        list = events;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<EventData> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(allEvents);
            }else{
                for (EventData event: allEvents){
                    if(event.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(event);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((Collection<? extends EventData>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public class EventViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView profilePicture;
        CardView cardView;


        public EventViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_veranstaltung_name);
            profilePicture = (ImageView) itemView.findViewById(R.id.pic_veranstaltung);
            cardView = (CardView) itemView.findViewById(R.id.eventCard);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventData event = list.get(getAdapterPosition());
                    listener.ItemClicked(event.getEventID());

                }
            });

        }
    }

    public class EventViewHolderSwipe extends RecyclerView.ViewHolder {
        ImageView profilePicture;
        CardView cardView;
        public EventViewHolderSwipe(@NonNull View itemView) {
            super(itemView);
            profilePicture = (ImageView) itemView.findViewById(R.id.pic_veranstaltungSwipe);
            cardView = (CardView) itemView.findViewById(R.id.eventCardSwipeArchive);
            if (isGuest){
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        closeSwipeMenu();
                        EventData event = list.get(getAdapterPosition());
                        DataFile.getEventsInstance().archive(event);
                        notifyItemRemoved(getAdapterPosition());
                    }
                });
            }else {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        closeSwipeMenu();
                        EventData event = list.get(getAdapterPosition());
                        EventRepository.getInstance().archive(event.getEventID());
                        notifyItemRemoved(getAdapterPosition());

                    }
                });
            }

        }
    }

    public class EventViewHolderSwipeArchived extends RecyclerView.ViewHolder {
        ImageView profilePicture;
        CardView cardView;
        public EventViewHolderSwipeArchived(@NonNull View itemView) {
            super(itemView);
            profilePicture = (ImageView) itemView.findViewById(R.id.pic_veranstaltungSwipe);
            cardView = (CardView) itemView.findViewById(R.id.eventCardSwipeArchive);
            if (isGuest){
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        closeSwipeMenu();
                        EventData event = list.get(getAdapterPosition());
                        DataFile.getEventsInstance().removeArchived(event);
                        notifyItemRemoved(getAdapterPosition());
                    }
                });

            }else {
                cardView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        closeSwipeMenu();
                        EventData event = list.get(getAdapterPosition());
                        EventRepository.getInstance().removeArchived(event.getEventID());
                        notifyItemRemoved(getAdapterPosition());

                    }
                });
            }


        }
    }
}
