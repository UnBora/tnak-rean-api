package com.kshrd.tnakrean.controller.notification;

import com.kshrd.tnakrean.model.EmailMessage;
import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.classmaterials.response.ClassResponse;
import com.kshrd.tnakrean.model.classmaterials.response.OneSignalNotificationResponse;
import com.kshrd.tnakrean.model.user.response.AppUserResponse;
import com.kshrd.tnakrean.repository.ClassRepository;
import com.kshrd.tnakrean.repository.ClassroomRepository;
import com.kshrd.tnakrean.repository.OneSignalPushNotificationRepository;
import com.kshrd.tnakrean.repository.UsersRepository;
import com.kshrd.tnakrean.service.PushNotificationService;
import com.kshrd.tnakrean.service.serviceInter.EmailService;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.constraints.Min;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/notification")
public class OneSignalPushNotificationController {

    private final OneSignalPushNotificationRepository oneSignalPushNotificationRepository;
    private final UsersRepository usersRepository;
    private final EmailService emailService;
    private final PushNotificationService pushNotificationService;
    private final ClassRepository classRepository;
    private final ClassroomRepository classroomRepository;

    public OneSignalPushNotificationController(OneSignalPushNotificationRepository oneSignalPushNotificationRepository, UsersRepository usersRepository, EmailService emailService, PushNotificationService pushNotificationService, ClassRepository classRepository, ClassroomRepository classroomRepository) {
        this.oneSignalPushNotificationRepository = oneSignalPushNotificationRepository;
        this.usersRepository = usersRepository;
        this.emailService = emailService;
        this.pushNotificationService = pushNotificationService;
        this.classRepository = classRepository;
        this.classroomRepository = classroomRepository;
    }


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
    ApiResponse<?> sendMessageToUser
            (@PathVariable("userId") String userId,
             @PathVariable("message") String message) {
        OneSignalNotificationResponse response = new OneSignalNotificationResponse();
        try {
            AppUserResponse appUserResponse = usersRepository.getUserById(Integer.parseInt(userId));
            if (appUserResponse != null) {
                PushNotificationService.sendMessageToUser(message, userId);
                emailService.send(userId, message, appUserResponse.getEmail());
                return ApiResponse.<OneSignalNotificationResponse>setError("Notification have sent to UserId: " + userId).setResponseCode(200);
            } else {
                return ApiResponse.<OneSignalNotificationResponse>setError("User Id does not exist").setResponseCode(404);
            }
        } catch (IOException e) {
            return ApiResponse.<OneSignalNotificationResponse>exception(e).setData(response);
        } catch (MessagingException e) {
            e.printStackTrace();
            return ApiResponse.<EmailMessage>setError("failed to send email");
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

    @PostMapping("/send-by-class/{message}/{classId}/{classRoomId}")
    ApiResponse<OneSignalNotificationResponse>
    sendMessageFilterToAllUsers(@PathVariable("message") String message,
                                @PathVariable("classId") @Min(value = 1, message = "{validation.classId.notNegative}") int classId,
                                @PathVariable("classRoomId") @Min(value = 1, message = "{validation.classroomId.notNegative}") int classRoomId) {
        OneSignalNotificationResponse notification = new OneSignalNotificationResponse();
        try {
            ClassResponse classResponse = oneSignalPushNotificationRepository.getClassById(classId, classRoomId);
            if (classResponse != null) {
                notification.setMessage(message);
                notification.setClassId(classId);
                notification.setClassRoomId(classRoomId);
                pushNotificationService.sendMessageFilterToAllUsers(message, classResponse);
                return ApiResponse.<OneSignalNotificationResponse>
                        ok(OneSignalNotificationResponse.class.getSimpleName()).setData(notification).setResponseMsg(
                        "Notification have sent to class: " + classResponse.getClass_name() + " classRoom:  " + classResponse.getClassRoomName());
            } else {
                return ApiResponse.<OneSignalNotificationResponse>notFound(OneSignalNotificationResponse.class.getSimpleName())
                        .setResponseMsg("Your classID and ClassroomID not Matched!");
            }
        } catch (IOException e) {
            return ApiResponse.<OneSignalNotificationResponse>setError(e.getMessage()).setData(notification);
        }

    }

}
