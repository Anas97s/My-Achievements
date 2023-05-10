package com.example.money_split_f1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.User.UserData;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class participantPostListAdapter extends RecyclerView.Adapter<participantPostListAdapter.PostViewHolder> {

    private final RecyclerViewClickListener listener;
    private List<Post> postList;
    public participantPostListAdapter(List<Post> postList, RecyclerViewClickListener listener) {
        this.listener = listener;
        this.postList = postList;
    }

    @NonNull
    @Override
    public participantPostListAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_detail_item, parent, false);
        return new participantPostListAdapter.PostViewHolder(view, listener);
    }

    @Override
    public int getItemCount(){
        return postList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull participantPostListAdapter.PostViewHolder holder, int position){
        Post post = postList.get(position);
        Context context = SuperApplication.getContext();

        holder.title.setText(post.getTitle());
        if (post.getOriginEventName().equals("")){
            holder.originEvent.setText(SuperApplication.getContext().getText(R.string.Payment));
            holder.originEvent.setTextColor(context.getResources().getColor(R.color.SK_red));
            holder.title.setText(post.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }else {
            holder.originEvent.setText(post.getOriginEventName());
            holder.originEvent.setTextColor(context.getResources().getColor(R.color.SK_grey));
        }

        double cost = post.getCost()/post.getParticipants().size();
        if(post.getCreator().getEmail().equals(UserData.getInstance().getEmail())){
            holder.cost.setText(formatCost(cost,"+"));
            holder.cost.setTextColor(context.getResources().getColor(R.color.SK_darkgreen_B));
        }else{
            holder.cost.setText(formatCost(cost,"-"));
            holder.cost.setTextColor(context.getResources().getColor(R.color.SK_red_B));
        }
    }

    //convert double to string formatted as currency
    private String formatCost(double cost, String sign){
        String costString = String.format("%s%3.2f", sign, cost);
        costString = costString.replace(".", ",");
        return costString + SuperApplication.getContext().getText(R.string.Currency);
    }

    //TODO:LiveRefresh: implement update
    public void update(List<Post> postList){
        this.postList.clear();
        this.postList = postList;
        notifyDataSetChanged();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{

        private TextView title, originEvent, cost;
        public PostViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);

            title = itemView.findViewById(R.id.participant_detail_itemTitle);
            originEvent = itemView.findViewById(R.id.participant_detail_originEvent);
            cost = itemView.findViewById(R.id.participant_detail_itemCost);

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
