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
/**This class is adapter only for chosen members in a post, its shows Participant that are invited to a post
 *
 * @author Anas Salameh
 * */
public class InPostParticipantAdapter extends RecyclerView.Adapter<InPostParticipantAdapter.InPostViewHolder> {
    private List<Participant> participants;
    public InPostParticipantAdapter(List<Participant> participants){
       this.participants = participants;
    }

    @NonNull
    @Override
    public InPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_profile, parent, false);
        return new InPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InPostViewHolder holder, int position) {
        Participant participant = participants.get(position);
        holder.memberName.setText(participant.getName());
    }

    @Override
    public int getItemCount() {
        return this.participants.size();
    }

    public static class InPostViewHolder extends RecyclerView.ViewHolder{
        ImageView profilePic;
        TextView memberName;
        CardView cardView;
        public InPostViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.participantProfileImage);
            memberName = itemView.findViewById(R.id.participantProfileName);
            cardView = itemView.findViewById(R.id.memberCard);
            profilePic.setImageResource(R.mipmap.ic_launcher);
        }
    }
}
