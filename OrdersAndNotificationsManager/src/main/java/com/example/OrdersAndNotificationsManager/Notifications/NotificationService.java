package com.example.OrdersAndNotificationsManager.Notifications;

import java.util.ArrayList;
import java.util.List;

public class NotificationService implements NotificationSubject {

    List<NotificationObserver> observers=new ArrayList<>();
    String mainState;
    @Override
    public void attach(NotificationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(NotificationObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String notification) {
        for(NotificationObserver observer:observers)
        {
            observer.update(notification);
        }
    }
}
