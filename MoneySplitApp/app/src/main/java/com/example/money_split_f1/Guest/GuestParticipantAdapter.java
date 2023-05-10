package com.example.money_split_f1.Guest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.money_split_f1.Adapters.RecyclerViewClickListener;
import com.example.money_split_f1.Event.Data.Participant;
import com.example.money_split_f1.R;

import java.util.List;

public class GuestParticipantAdapter extends RecyclerView.Adapter<GuestParticipantAdapter.GuestParticipantViewHolder>{

    private List<Participant> participants;
    private final RecyclerViewClickListener listener;
    public GuestParticipantAdapter(List<Participant> participants, RecyclerViewClickListener listener){
        this.listener = listener;
        this.participants = participants;
    }

    @NonNull
    @Override
    public GuestParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_item, parent, false);
        return new GuestParticipantViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestParticipantViewHolder holder, int position) {
        Participant participant = participants.get(position);
        holder.participantName.setText(participant.getName());
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public static class GuestParticipantViewHolder extends RecyclerView.ViewHolder{
        private TextView participantName, participantPosts, participantDebt, participantOwnerTag;
        private ImageView userPic;
        public GuestParticipantViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            userPic = itemView.findViewById((R.id.participantImage));
            userPic.setImageResource(R.mipmap.ic_launcher);
            participantName = itemView.findViewById(R.id.participantName);
            participantPosts = itemView.findViewById(R.id.participantSharedItemCount);
            participantDebt = itemView.findViewById(R.id.participantDebt);
            participantOwnerTag = itemView.findViewById(R.id.admintag);

            participantPosts.setVisibility(View.GONE);
            participantDebt.setVisibility(View.GONE);
            participantOwnerTag.setVisibility(View.GONE);

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
