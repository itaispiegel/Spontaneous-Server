package com.spontaneous.server.service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.spontaneous.server.model.entity.Guest;
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
     * @param message        The notification details.
     * @param recipientToken The GCM token of the recipient.
     * @throws ServiceException Is thrown in case that no user is found.
     * @throws IOException      Is thrown in case that the notificationType was not sent.
     */
    private void sendNotification(Message message, String recipientToken) throws ServiceException, IOException {
        mLogger.info(String.format("Notifying user with token #%s", recipientToken));

        Sender sender = new Sender(API_KEY);
        sender.sendNoRetry(message, recipientToken);
    }

    /**
     * Send a broadcast message to a given user.
     *
     * @param guest The user  to send the message to.
     * @param content     The content of the message.
     * @throws ServiceException
     * @throws IOException
     */
    public void sendBroadcastMessage(Guest guest, String content) throws ServiceException, IOException {

        //The title of the message is the title of the event.
        Message message = new Message.Builder()
                .addData("type", NotificationType.BROADCAST.toString())
                .addData("title", guest.getEvent().getTitle())
                .addData("content", content)
                .build();

        sendNotification(message, guest.getUser().getGcmToken());
    }

    /**
     * Notify an {@link Guest} that he was invited to a new event.
     *
     * @param guest The invited user we wish to notify.
     * @throws ServiceException
     * @throws IOException
     */
    public void sendInvitation(Guest guest) throws ServiceException, IOException {
        final String title = "Spontaneous";
        final String content = "You have been invited to an event!";

        Message message = new Message.Builder()
                .addData("type", NotificationType.INVITATION.toString())
                .addData("title", title)
                .addData("content", content)
                .addData("data", guest.getEvent().toString())
                .build();

        sendNotification(message, guest.getUser()
                .getGcmToken());
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
