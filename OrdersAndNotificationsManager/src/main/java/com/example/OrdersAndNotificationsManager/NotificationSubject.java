package com.example.OrdersAndNotificationsManager;

public interface NotificationSubject {
    void attach(NotificationObserver observer);
    void detach(NotificationObserver observer);
    void notifyObservers(String notification);
}