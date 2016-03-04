package com.spontaneous.server.service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.spontaneous.server.model.entity.Guest;
import com.spontaneous.server.model.entity.User;
import com.spontaneous.server.model.entity.representational.EventRO;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * This class is part of the service layer of the application and is used for handling GCM operations for push notifications.
 */
@Service
public class GcmService {

    private static final String API_KEY = "AIzaSyCZNSEfuv0Lk8AREq9avNappUouZwWtI6I";

    private final Logger mLogger = LoggerFactory.getLogger(this.getClass());

    /**
     * Send a notificationType to a given user.
     *
     * @param message The notification details.
     * @param user    User to notify.
     * @throws ServiceException Is thrown in case that the given user does not have a GCM token.
     */
    private void sendNotification(Message message, User user) throws ServiceException {

        //Throw a service exception in case that the user does not have a GCM token.
        if (user == null) {
            throw new ServiceException("The recipient user is null.");
        }

        //Throw a service exception if the user does not have a GCM token.
        if (user.getGcmToken() == null) {
            throw new ServiceException(("The recipient does not have a GCM token."));
        }

        mLogger.info(String.format("Notifying user with token #%s", user.getGcmToken()));

        try {
            Sender sender = new Sender(API_KEY);
            sender.sendNoRetry(message, user.getGcmToken());
        } catch (IOException e) {

            //Is thrown in case that the notificationType was not sent - Not supposed to occur.
            mLogger.error(e.getMessage());
        }
    }

    /**
     * Send a broadcast message to a given user.
     *
     * @param guest   The user  to send the message to.
     * @param content The content of the message.
     * @throws ServiceException If the notification was not able to be sent.
     */
    public void sendBroadcastMessage(Guest guest, String content) throws ServiceException {

        //The title of the message is the title of the event.
        Message message = new Message.Builder()
                .addData("type", NotificationType.BROADCAST.toString())
                .addData("title", guest.getEvent().getTitle())
                .addData("content", content)
                .build();

        try {
            sendNotification(message, guest.getUser());
        } catch (ServiceException e) {
            //In case that the notification was not sent successfully.
            mLogger.error(e.getMessage());
        }
    }

    /**
     * Notify an {@link Guest} that he was invited to a new event.
     *
     * @param guest The invited user we wish to notify.
     * @throws ServiceException If the notification was not able to be sent.
     */
    public void sendInvitation(Guest guest) throws ServiceException {

        final String title = "Spontaneous";
        final String content = "You have been invited to an event!";

        EventRO eventRO = guest.getEvent()
                .createRepresentationalObject();

        Message message = new Message.Builder()
                .addData("type", NotificationType.INVITATION.toString())
                .addData("title", title)
                .addData("content", content)
                .addData("data", eventRO.toString())
                .build();

        try {
            sendNotification(message, guest.getUser());
        } catch (ServiceException e) {
            //In case that the notification was not sent successfully.
            mLogger.error(e.getMessage());
        }
    }

    /**
     * This class represents the type of notificationType sent to the user's device.
     */
    enum NotificationType {

        /**
         * Invitation to a created event.
         */
        INVITATION,

        /**
         * The event host can send a broadcast message to his guests.
         */
        BROADCAST
    }
}
