package com.example.money_split_f1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_split_f1.Event.Data.Participant;
import com.example.money_split_f1.R;

import java.util.List;

public class ParticipantPostAdapter extends RecyclerView.Adapter<ParticipantPostAdapter.PostMemberViewHolder> {
    private List<Participant> participantList;
    private final RecyclerViewClickListener listener;
    public ParticipantPostAdapter(List<Participant> participantList, RecyclerViewClickListener listener){
        this.participantList = participantList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public PostMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_profile, parent, false);
        return new PostMemberViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostMemberViewHolder holder, int position) {
        Participant participant = participantList.get(position);
        holder.memberName.setText(participant.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.ItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }

    public static class PostMemberViewHolder extends RecyclerView.ViewHolder{
        ImageView profilePic;
        TextView memberName;
        CardView cardView;

        public PostMemberViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);

            profilePic = itemView.findViewById(R.id.participantProfileImage);
            memberName = itemView.findViewById(R.id.participantProfileName);
            cardView = itemView.findViewById(R.id.memberCard);
            profilePic.setImageResource(R.mipmap.ic_launcher);

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
