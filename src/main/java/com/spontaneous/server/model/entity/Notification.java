package com.spontaneous.server.model.entity;

import com.spontaneous.server.config.GsonFactory;

/**
 * This class represents a notification sent to a user's device.
 */
public class Notification<T> {

    private final String title;
    private final String messageBody;
    private final NotificationType notificationType;
    private final T data;

    public Notification(String title, String messageBody, NotificationType notificationType, T data) {
        this.title = title;
        this.messageBody = messageBody;
        this.notificationType = notificationType;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return GsonFactory.getGson().toJson(this);
    }

    public enum NotificationType {
        INVITATION
    }
}
