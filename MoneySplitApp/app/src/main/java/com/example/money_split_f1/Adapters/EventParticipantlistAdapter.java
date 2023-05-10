package com.example.money_split_f1.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.Data.Participant;
import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.Event.Data.PostUserList;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.User.UserData;

import java.util.List;

public class EventParticipantlistAdapter extends RecyclerView.Adapter<EventParticipantlistAdapter.PostViewHolder> {

    private List<Participant> participants;
    private EventData event;
    private final RecyclerViewClickListener listener;
    private PostUserList posts;
    public EventParticipantlistAdapter(EventData event, RecyclerViewClickListener listener){
        this.event = event;
        this.participants = event.getParticipantList();
        this.listener = listener;
        this.posts = new PostUserList(event.getPostList());
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_item, parent, false);
        return new PostViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Participant participant = participants.get(position);

        if (participant.getEmail().equals(event.getOwner().getEmail()))
            holder.participantOwnerTag.setVisibility(View.VISIBLE);

        if (!participant.getEmail().equals(UserData.getInstance().getEmail())) {
            holder.participantName.setText(participant.getName());

            double debt = calculateDebt(participant);
            Context context = SuperApplication.getContext();

            if (debt > 0)
                holder.participantDebt.setTextColor(context.getResources().getColor(R.color.SK_darkgreen_B));
            else if (debt < 0)
                holder.participantDebt.setTextColor(context.getResources().getColor(R.color.SK_red_B));


            holder.participantDebt.setText(formatDebt(debt));

            holder.participantPosts.setText(formatPostCount(calculateRelevantPostCount(participant)));
        }else{
            holder.participantName.setText(SuperApplication.getContext().getText(R.string.self_name));
            holder.participantDebt.setText("-");
            holder.participantPosts.setText(formatOwnPostCount(posts.getUserCreated().size()));
        }
    }

    @Override
    public int getItemCount() {
        if (participants != null)
            return participants.size();
        else
            return 0;
    }

    //convert double to string formatted as currency
    private String formatDebt(double debt){
        String debtString="";
        if (debt > 0) {
            debtString = "+";
        }
        debtString += String.format("%3.2f", debt);
        debtString = debtString.replace(".", ",");
        return debtString + SuperApplication.getContext().getText(R.string.Currency);
    }

    //convert int to string formatted as post count subtitle
    private String formatPostCount(int count){
        if(count == 0){
            //TODO: take from strings.xml
            return SuperApplication.getContext().getText(R.string.no_shared).toString();
        }
        if(count == 1){
            return SuperApplication.getContext().getText(R.string.single_shared).toString();
        }
        return String.format("%d %s",count, SuperApplication.getContext().getText(R.string.x_shared));
    }
    private String formatOwnPostCount(int count){
        if(count == 0){

            return SuperApplication.getContext().getText(R.string.no_shared).toString();
        }
        if(count == 1){
            return SuperApplication.getContext().getText(R.string.single_shared).toString();
        }
        return String.format("%d %s",count, SuperApplication.getContext().getText(R.string.x_shared));
    }

    private double calculateDebt(Participant participant){
        double debt = 0.0;
        for (Post post: posts.getUserCreated()) {
            if(post.getParticipantEmails().contains(participant.getEmail()))
                debt += post.getCost() / post.getParticipants().size();
        }
        for (Post post: posts.getUserParticipates()) {
            if(post.getCreator().getEmail().equals(participant.getEmail()))
                debt -= post.getCost() / post.getParticipants().size();
        }
        return debt;
    }


    //calculates number of posts current user and given participant share
    private int calculateRelevantPostCount(Participant participant){
        int count = 0;
        for (Post post:this.posts.getAllPosts()) {
            if (post.getParticipantEmails().contains(participant.getEmail())){
                count++;
            }
        }
        return count;
    }

    public void update(EventData event){
        this.event = event;
        this.participants = event.getParticipantList();
        this.posts = new PostUserList(event.getPostList());
        notifyDataSetChanged();
    }
    public static class PostViewHolder extends RecyclerView.ViewHolder{

        private TextView name, participantName, participantPosts, participantDebt, participantOwnerTag;
        private ImageView userPic;
        public PostViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);

            //name = (TextView) itemView.findViewById(R.id.memberNameLayout);
            ImageView userPic = itemView.findViewById((R.id.participantImage));
            participantName = itemView.findViewById(R.id.participantName);
            participantPosts = itemView.findViewById(R.id.participantSharedItemCount);
            participantDebt = itemView.findViewById(R.id.participantDebt);
            participantOwnerTag = itemView.findViewById(R.id.admintag);
            userPic.setImageResource(R.mipmap.ic_launcher);

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
