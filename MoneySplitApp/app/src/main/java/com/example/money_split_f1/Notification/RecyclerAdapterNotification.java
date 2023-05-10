package com.example.money_split_f1.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_split_f1.Adapters.RecyclerViewClickListener;
import com.example.money_split_f1.R;
import com.example.money_split_f1.Repos.NotificationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerAdapterNotification extends RecyclerView.Adapter<NotificationViewHolder> {
    List<NotificationData> list = Collections.emptyList();
    Context context;
    private final RecyclerViewClickListener listener;


    public RecyclerAdapterNotification(List<NotificationData> data, Context context,RecyclerViewClickListener listener) {
        if (data != null){
            this.list = new ArrayList<>(data);
            Collections.reverse(this.list);
        }
        this.listener = listener;
        this.context = context;
    }


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_object, parent, false);
        return new NotificationViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.header.setText(list.get(position).getHeader());
        holder.text.setText(list.get(position).getText());
        holder.button.setText(list.get(position).getButton());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(holder.getAdapterPosition()) instanceof InvitationNotification){
                    InvitationNotification notification = (InvitationNotification) list.get(holder.getAdapterPosition());
                    listener.ItemClicked(notification.getV_id(),notification.getId());
                }else if (list.get(holder.getAdapterPosition()) instanceof NewPostenNotification){
                    listener.ItemClicked(list.get(holder.getAdapterPosition()).getV_id());
                }

            }
        });
        if (list.get(holder.getAdapterPosition()) instanceof InvitationNotification){
            holder.button.setVisibility(View.GONE);
        }else {
            holder.button.setVisibility(View.VISIBLE);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NotificationRepository.getInstance().deleteNewsFromID(list.get(holder.getAdapterPosition()).getId());
                }
            });
        }


    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<NotificationData> newNotifications) {
        this.list.clear();
        this.list = new ArrayList<>(newNotifications);
        Collections.reverse(this.list);
        notifyDataSetChanged();
    }

}
