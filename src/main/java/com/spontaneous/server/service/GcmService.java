package com.spontaneous.server.service;

import com.spontaneous.server.model.entity.InvitedUser;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is part of the service layer of the application and is used for handling GCM operations for push notifications.
 */
@Service
public class GcmService {

    private static final String API_KEY = "AIzaSyCZNSEfuv0Lk8AREq9avNappUouZwWtI6I";
    public static final String GCM_BASE_URL = "https://android.googleapis.com/gcm/send";

    private final Logger mLogger = LoggerFactory.getLogger(this.getClass());


    private void sendNotification(String message, String to) {

        try {
            JSONObject jGcmData = new JSONObject();
            JSONObject jData = new JSONObject();

            jGcmData.put("to", to);

            jData.put("message", message);
            jGcmData.put("data", jData);

            // Create connection to send GCM Message request.
            URL url = new URL(GCM_BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "key=" + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send GCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jGcmData.toString().getBytes());

            // Read GCM response.
            InputStream inputStream = conn.getInputStream();
            String resp = IOUtils.toString(inputStream);
            System.out.println(resp);

        } catch (IOException e) {
            mLogger.error(e.getMessage());
        }
    }

    public void notifyInvitedUser(InvitedUser invitedUser) {
        sendNotification("You have been invited to an event!", invitedUser.getUser()
                .getGcmToken());
    }

}
