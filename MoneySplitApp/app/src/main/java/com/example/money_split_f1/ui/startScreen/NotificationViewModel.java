package com.example.money_split_f1.ui.startScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.money_split_f1.Notification.NotificationData;
import com.example.money_split_f1.Repos.NotificationRepository;

import java.util.List;

public class NotificationViewModel extends ViewModel {
    private final NotificationRepository notificationRepository = NotificationRepository.getInstance();
    private LiveData<List<NotificationData>> notifications;

    public NotificationViewModel(){
        this.notifications = notificationRepository.getNotification();
    }

    public List<NotificationData> getNotifications(){
        return notifications.getValue();
    }
    public LiveData<List<NotificationData>> getLiveNotifications(){
        return notifications;
    }

}
