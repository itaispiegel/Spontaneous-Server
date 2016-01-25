package com.spontaneous.server.service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.spontaneous.server.model.entity.InvitedUser;
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

    private void sendNotification(String messageString, String to) {

        try {
            Message message = new Message.Builder().addData("message", messageString).build();

            Sender sender = new Sender(API_KEY);
            sender.sendNoRetry(message, to);

        } catch (IOException e) {
            mLogger.error(e.getMessage());
        }
    }

    public void notifyInvitedUser(InvitedUser invitedUser) {
        sendNotification("You have been invited to an event!", invitedUser.getUser()
                .getGcmToken());
    }

}
