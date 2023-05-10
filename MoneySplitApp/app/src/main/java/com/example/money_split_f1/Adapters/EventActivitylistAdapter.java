package com.example.money_split_f1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventActivitylistAdapter extends RecyclerView.Adapter<EventActivitylistAdapter.PostDetailsViewHolder> {

    private List<Post> posts;
    private EventData event;
    RecyclerViewClickListener listener;

    public EventActivitylistAdapter(EventData event, RecyclerViewClickListener listener){
        this.event = event;
        this.posts = event.getPostList();
        Collections.sort(this.posts, Comparator.comparing(Post::getDate).reversed());
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new EventActivitylistAdapter.PostDetailsViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostDetailsViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.itemName.setText(post.getTitle());

        holder.itemCost.setText(formatCost(post.getCost()));
        holder.itemParticipantCount.setText(formatParticipantCount(posts.get(position).getParticipants().size()));
    }

    private String formatCost(double cost){
        String costString = String.format("%3.2f", cost);
        costString = costString.replace(".", ",");
        return costString + SuperApplication.getContext().getText(R.string.Currency);
    }

    private String formatParticipantCount(int count){
        if(count == 0){
            //TODO: take from strings.xml
            return SuperApplication.getContext().getText(R.string.no_participant).toString();
        }
        if(count == 1){
            return SuperApplication.getContext().getText(R.string.single_participant).toString();
        }
        return String.format("%d %s",count, SuperApplication.getContext().getText(R.string.x_participant));
    }

    @Override
    public int getItemCount() {
        if(posts == null){
            return 0;
        }
        return posts.size();
    }

    public void update(EventData event) {
        this.event = event;
        this.posts = event.getPostList();
        notifyDataSetChanged();
    }

    public static class PostDetailsViewHolder extends RecyclerView.ViewHolder{
        TextView itemName, itemCost, itemParticipantCount;
        ImageView positionImage;

        public PostDetailsViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
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
