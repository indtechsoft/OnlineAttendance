package com.example.onlineattendance.Notification;

public class NotificationModel {
    public String getNotification_title() {
        return notification_title;
    }

    public void setNotification_title(String notification_title) {
        this.notification_title = notification_title;
    }

    public String getNotification_text() {
        return notification_text;
    }

    public void setNotification_text(String notification_text) {
        this.notification_text = notification_text;
    }

    String notification_title;

    public NotificationModel(String notification_title, String notification_text) {
        this.notification_title = notification_title;
        this.notification_text = notification_text;
    }

    String notification_text;

    public NotificationModel(){}

}
