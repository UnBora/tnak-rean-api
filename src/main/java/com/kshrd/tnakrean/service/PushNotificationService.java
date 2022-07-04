package com.kshrd.tnakrean.service;

import com.kshrd.tnakrean.model.classmaterials.response.ClassResponse;
import com.kshrd.tnakrean.repository.ClassRepository;
import com.kshrd.tnakrean.repository.OneSignalPushNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Service
public class PushNotificationService {

    public static final String REST_API_KEY = "YTM0MjYzOGYtMDNmNy00OWQ1LWE3MzUtY2IxMzZkMGJmY2Fj";
//    public static final String APP_ID = "1557ea45-8f4a-473d-ad64-dff9355214ec";
    public static final String APP_ID = "1557ea45-8f4a-473d-ad64-dff9355214ec";
    final
    OneSignalPushNotificationRepository oneSignalPushNotificationRepository;


    public PushNotificationService(OneSignalPushNotificationRepository oneSignalPushNotificationRepository) {
        this.oneSignalPushNotificationRepository = oneSignalPushNotificationRepository;
    }

    public static HttpURLConnection httpURLConnection(String method) throws IOException {
        URL url = new URL("https://onesignal.com/api/v1/notifications");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Authorization",
                "Basic " + REST_API_KEY);
        con.setRequestMethod(method);
        return con;
    }


    public static void sendMessageToAllUsers(String message) throws IOException {

        String jsonResponse;
        HttpURLConnection con = httpURLConnection("POST");
        String strJsonBody = "{"
                + "\"app_id\": \"" + APP_ID + "\","
                + "\"included_segments\": [\"Subscribed Users\"],"
                + "\"data\": {\"foo\": \"bar\"},"
                + "\"contents\": {\"en\": \"" + message + "\"}"
                + "}";

        System.out.println("strJsonBody:\n" + strJsonBody);



        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
        con.setFixedLengthStreamingMode(sendBytes.length);

        OutputStream outputStream = con.getOutputStream();
        outputStream.write(sendBytes);

        int httpResponse = con.getResponseCode();
        System.out.println("httpResponse: " + httpResponse);

        jsonResponse = mountResponseRequest(con, httpResponse);
        System.out.println("jsonResponse:\n" + jsonResponse);

    }

    private static String mountResponseRequest(HttpURLConnection con, int httpResponse) throws IOException {
        String jsonResponse;
        if (httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        } else {
            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        return jsonResponse;
    }


    public static void sendMessageToUser(
            String message, String userId) throws IOException {

        String jsonResponse;
        HttpURLConnection con = httpURLConnection("POST");
        String strJsonBody = "{"
                + "\"app_id\": \"" + APP_ID + "\","
                + "\"include_external_user_ids\": [\"" + userId + "\"],"
                + "\"channel_for_external_user_ids\": \"push\","
                + "\"data\": {\"foo\": \"bar\"},"
                + "\"contents\": {\"en\": \"" + message + "\"}"
                + "}";

        System.out.println("strJsonBody:\n" + strJsonBody);

        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
        con.setFixedLengthStreamingMode(sendBytes.length);

        OutputStream outputStream = con.getOutputStream();
        outputStream.write(sendBytes);

        int httpResponse = con.getResponseCode();
        System.out.println("httpResponse: " + httpResponse);

        jsonResponse = mountResponseRequest(con, httpResponse);
        System.out.println("jsonResponse:\n" + jsonResponse);
    }

    public void sendMessageFilterToAllUsers(String message, ClassResponse classResponse) throws IOException {

        String jsonResponse;
        HttpURLConnection con = httpURLConnection("POST");
        String strJsonBody = "{"
                + "\"app_id\": \"" + APP_ID + "\","
                + "\"filters\": [{\"field\": \"tag\", \"key\": \"class\", \"relation\": \"=\", \"value\": \""+classResponse.getClass_name()+"\"},{\"field\": \"tag\", \"key\": \"classRoom\"," +
                " \"relation\": \"=\", \"value\": \""+classResponse.getClassRoomName()+"\"}],"
                + "\"data\": {\"foo\": \"bar\"},"
                + "\"contents\": {\"en\": \"" + message + "\"}"
                + "}";


        System.out.println("strJsonBody:\n" + strJsonBody);

        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
        con.setFixedLengthStreamingMode(sendBytes.length);

        OutputStream outputStream = con.getOutputStream();
        outputStream.write(sendBytes);

        int httpResponse = con.getResponseCode();
        System.out.println("httpResponse: " + httpResponse);

        if (httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        } else {
            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        System.out.println("jsonResponse:\n" + jsonResponse);

    }
}
