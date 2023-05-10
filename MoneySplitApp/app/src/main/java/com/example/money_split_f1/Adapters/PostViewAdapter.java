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
import com.example.money_split_f1.Event.Data.Post;
import com.example.money_split_f1.R;
import java.util.List;
/**This class is a adapter for Participant list in post view
 *
 * @author Anas Salameh
 * */
public class PostViewAdapter extends RecyclerView.Adapter<PostViewAdapter.PostViewViewHolder>{
    List<Participant> participantList;
    Post post;
    private final RecyclerViewClickListener listener;

    public PostViewAdapter(Post post, RecyclerViewClickListener listener) {
        this.post = post;
        this.listener = listener;
        this.participantList = post.getParticipants();
    }

    @NonNull
    @Override
    public PostViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.participant_profile, parent, false);
        return new PostViewViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewViewHolder holder, int position) {
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

    public static class PostViewViewHolder extends RecyclerView.ViewHolder{
        ImageView profilePic;
        TextView memberName;
        CardView cardView;
        public PostViewViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
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
