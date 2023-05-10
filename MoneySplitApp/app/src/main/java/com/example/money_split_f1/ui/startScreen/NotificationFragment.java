package com.example.money_split_f1.ui.startScreen;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.money_split_f1.Adapters.RecyclerViewClickListener;
import com.example.money_split_f1.Event.Data.EventData;
import com.example.money_split_f1.Event.EventUi;
import com.example.money_split_f1.Notification.NotificationData;
import com.example.money_split_f1.Notification.RecyclerAdapterNotification;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.databinding.FragmentNotificationBinding;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.Repos.NotificationRepository;
import com.example.money_split_f1.User.UserData;

import java.util.List;


public class NotificationFragment extends Fragment implements RecyclerViewClickListener {
    NotificationViewModel notificationViewModel;
    private FragmentNotificationBinding binding;


    public NotificationFragment() {
        // Required empty public constructor
        NotificationRepository.getInstance().makeNewsRequest();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        binding = FragmentNotificationBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        List<NotificationData> notificationData = notificationViewModel.getNotifications();
        RecyclerAdapterNotification notificationAdapter = new RecyclerAdapterNotification(notificationData,this.getContext(),this);
        binding.rvNotifications.setAdapter(notificationAdapter);
        binding.rvNotifications.setLayoutManager(new LinearLayoutManager(this.getContext()));

        notificationViewModel.getLiveNotifications().observe(this.getActivity(), new Observer<List<NotificationData>>() {
            @Override
            public void onChanged(List<NotificationData> notificationData) {
                notificationAdapter.update(notificationData);
            }
        });

        binding.swipeRefreshNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                EventRepository.getInstance().updateEventList();
                binding.swipeRefreshNews.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void openDialog(EventData eventData, String n_id){
        ConfirmationDialogue dialogue = new ConfirmationDialogue(eventData,n_id);
        dialogue.show(getActivity().getSupportFragmentManager(),"confirmation dialog");
    }

    public static class ConfirmationDialogue extends AppCompatDialogFragment {
        EventData eventData;
        String n_id;

        public ConfirmationDialogue(EventData eventData,String n_id){
            this.eventData = eventData;
            this.n_id = n_id;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(SuperApplication.getContext().getResources().getString(R.string.join_title))
                    .setMessage(SuperApplication.getContext().getResources().getString(R.string.join_text))
                    .setPositiveButton(SuperApplication.getContext().getResources().getString(R.string.join_positive), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NotificationRepository.getInstance().deleteNewsFromID(n_id);
                            Intent intent = new Intent(getContext(), EventUi.class);
                            intent.putExtra("Notification",true);
                            intent.putExtra("Name", eventData.getName());
                            intent.putExtra("id", eventData.getEventID());
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(SuperApplication.getContext().getResources().getString(R.string.join_negative), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NotificationRepository.getInstance().deleteNewsFromID(n_id);
                            EventRepository.getInstance().makeRemoveUserFromEventRequest(eventData.getEventID(), UserData.getInstance().getEmail());
                        }
                    });
            return builder.create();
        }
    }


    @Override
    public void ItemClicked(int v_id) {
        EventData eventData = EventRepository.getInstance().getEventbyID(v_id);
        Intent intent = new Intent(getContext(), EventUi.class);
        intent.putExtra("Notification",true);
        intent.putExtra("Name", eventData.getName());
        intent.putExtra("id", eventData.getEventID());
        startActivity(intent);
    }

    @Override
    public void ItemClicked(int id,String notificationID) {
        EventData eventData = EventRepository.getInstance().getEventbyID(id);
        openDialog(eventData, notificationID);

    }

    @Override
    public void ItemClicked() {

    }
}