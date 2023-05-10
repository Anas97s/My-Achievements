package com.example.money_split_f1.Guest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_split_f1.Adapters.RecyclerViewClickListener;
import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.R;
import com.example.money_split_f1.LocalData.DataFile;

import java.util.List;

public class GuestEventActivityAdapter extends RecyclerView.Adapter<GuestEventActivityAdapter.GuestActivityViewHolder> {
    private final List<Post> posts;
    private final RecyclerViewClickListener listener;
    private DataFile dataFile;

    public GuestEventActivityAdapter(List<Post> posts, RecyclerViewClickListener listener) {
        this.posts = posts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GuestActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new GuestActivityViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestActivityViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.itemName.setText(post.getTitle());
        holder.itemCost.setText(formatCost(post.getCost()));
        holder.itemParticipantCount.setText("Ein Teilnehmer");
    }

    private String formatCost(double cost){
        String costString = String.format("%3.2f", cost);
        costString = costString.replace(".", ",");
        return costString + "€";
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class GuestActivityViewHolder extends RecyclerView.ViewHolder{
        TextView itemName, itemCost, itemParticipantCount;
        ImageView positionImage;

        public GuestActivityViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.itemTitle);
            itemCost = (TextView) itemView.findViewById(R.id.itemCost);
            itemParticipantCount = (TextView) itemView.findViewById(R.id.itemParticipantCount);
            positionImage = (ImageView) itemView.findViewById(R.id.itemImage);
            positionImage.setImageResource(R.mipmap.ic_launcher);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            listener.ItemClicked(position);
                        }
                    }
                }
            });
        }
    }
}
