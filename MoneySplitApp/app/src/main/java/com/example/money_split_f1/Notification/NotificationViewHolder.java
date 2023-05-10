package com.example.money_split_f1.Notification;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_split_f1.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    TextView header;
    TextView text;
    TextView button;
    CardView cardView;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        header = (TextView) itemView.findViewById(R.id.tv_notification_header);
        text = (TextView) itemView.findViewById(R.id.tv_notification_text);
        button = (TextView) itemView.findViewById(R.id.tv_notification_deny);
        cardView = (CardView) itemView.findViewById(R.id.notification_cardview);
    }


}
