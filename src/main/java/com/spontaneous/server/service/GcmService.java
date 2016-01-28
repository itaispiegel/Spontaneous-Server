package com.spontaneous.server.service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.spontaneous.server.model.entity.Event;
import com.spontaneous.server.model.entity.InvitedUser;
import com.spontaneous.server.model.entity.Notification;
import com.spontaneous.server.model.entity.User;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * This class is part of the service layer of the application and is used for handling GCM operations for push notifications.
 */
@Service
public class GcmService {

    private static final String API_KEY = "AIzaSyCZNSEfuv0Lk8AREq9avNappUouZwWtI6I";

    private final Logger mLogger = LoggerFactory.getLogger(this.getClass());
    private final UserService mUserService;

    @Autowired
    public GcmService(UserService userService) {
        this.mUserService = userService;
    }

    /**
     * Send a notification to a given user.
     *
     * @param notification The notification data.
     * @param userId       Id of the user we wish to send the notification to.
     * @throws ServiceException Is thrown in case that no user is found.
     * @throws IOException      Is thrown in case that the notification was not sent.
     */
    private void sendNotification(Notification notification, long userId) throws ServiceException, IOException {
        User recipient = mUserService.getUserById(userId);

        mLogger.info(String.format("Sending a notification to user with id #%d", userId));

        Message message = new Message.Builder()
                .addData("notification", notification.toString())
                .build();

        Sender sender = new Sender(API_KEY);
        sender.sendNoRetry(message, recipient.getGcmToken());
    }

    /**
     * Notify an {@link InvitedUser} that he was invited to a new event.
     *
     * @param invitedUser The invited user we wish to notify.
     */
    public void notifyInvitedUser(InvitedUser invitedUser) throws ServiceException, IOException {
        final String messageTitle = "Spontaneous";
        final String messageContent = "You have been invited to an event!";

        Notification<Event> notification = new Notification<>(messageTitle, messageContent,
                Notification.NotificationType.INVITATION, invitedUser.getEvent());

        sendNotification(notification, invitedUser.getUser()
                .getId());
    }

}
