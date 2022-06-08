package com.kshrd.tnakrean.controller.notification;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.classmaterials.response.OneSignalNotificationResponse;
import com.kshrd.tnakrean.repository.OneSignalPushNotificationRepository;
import com.kshrd.tnakrean.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@RestController
@RequestMapping("/api/v1/notification")
public class OneSignalPushNotificationController {

    @Autowired
    private OneSignalPushNotificationRepository repository;

//    @PostMapping("/sendMessageToAllUsers/{message}")
//    public void sendMessageToAllUsers(@PathVariable("message") String message)
//    {
//        PushNotificationOptions.sendMessageToAllUsers(message);
//
//    }

//    @PostMapping("/sendMessageToUser/{userId}/{message}")
//    public void sendMessageToUser(@PathVariable("userId") String userId,
//                                  @PathVariable("message") String message) {
//        PushNotificationOptions.sendMessageToUser(message, userId);
//        OneSignalPushNotification notification = new OneSignalPushNotification();
//    }

    @PostMapping("/to-user/{userId}/{message}")
    ApiResponse<OneSignalNotificationResponse> sendMessageToUser
            (@PathVariable("userId") String userId,
             @PathVariable("message") String message) {
        OneSignalNotificationResponse response = new OneSignalNotificationResponse();
        try {
            PushNotificationService.sendMessageToUser(message, userId);
            return ApiResponse.<OneSignalNotificationResponse>
                    ok(OneSignalNotificationResponse.class.getSimpleName()).setData(response);
        } catch (IOException e) {
            return ApiResponse.<OneSignalNotificationResponse>exception(e).setData(response);
        }
    }

    @PostMapping("/to-all-users/{message}")
    ApiResponse<OneSignalNotificationResponse> sendMessageToAllUsers(@PathVariable("message") String message) {
        OneSignalNotificationResponse notification = new OneSignalNotificationResponse();
        try {
            notification.setMessage(message);
            PushNotificationService.sendMessageToAllUsers(message);
            return ApiResponse.<OneSignalNotificationResponse>
                            ok(OneSignalNotificationResponse.class.getSimpleName())
                    .setData(notification);
        } catch (IOException e) {
            return ApiResponse.<OneSignalNotificationResponse>exception(e).setData(notification);
        }

    }

    @PostMapping("/send-by-class/{message}/{classId}")
    ApiResponse<OneSignalNotificationResponse>
    sendMessageFilterToAllUsers(@PathVariable("message") String message,
                                @PathVariable("classId") int classId) {
        OneSignalNotificationResponse notification = new OneSignalNotificationResponse();
        try {
            notification.setMessage(message);
            notification.setClassId(classId);
            PushNotificationService.sendMessageFilterToAllUsers(message, classId);
            return ApiResponse.<OneSignalNotificationResponse>
                    ok(OneSignalNotificationResponse.class.getSimpleName()).setData(notification);
        } catch (IOException e) {
            return ApiResponse.<OneSignalNotificationResponse>setError(e.getMessage()).setData(notification);
        }

    }

}
