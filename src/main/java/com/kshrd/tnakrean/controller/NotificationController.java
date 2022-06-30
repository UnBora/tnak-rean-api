package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.EmailMessage;
import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.response.ClassResponse;
import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import com.kshrd.tnakrean.model.user.response.GetStudentByIDResponse;
import com.kshrd.tnakrean.repository.OneSignalPushNotificationRepository;
import com.kshrd.tnakrean.repository.StudentRepository;
import com.kshrd.tnakrean.repository.UsersRepository;
import com.kshrd.tnakrean.service.PushNotificationService;
import com.kshrd.tnakrean.service.serviceImplementation.NotificationImp;
import com.kshrd.tnakrean.service.serviceInter.EmailService;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    final
    NotificationImp notificationImp;
    final OneSignalPushNotificationRepository repository;
    final StudentRepository studentRepository;
    private final EmailService emailService;
    final UsersRepository usersRepository;

    public NotificationController(NotificationImp notificationImp, OneSignalPushNotificationRepository repository, StudentRepository studentRepository, EmailService emailService, UsersRepository usersRepository) {
        this.notificationImp = notificationImp;
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.emailService = emailService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/get/{userId}")
    ApiResponse<List<NotificationResponse>> getNotificationByUserId(@RequestParam int userId) {
        List<NotificationResponse> notificationResponseList = notificationImp.notificationList(userId);
        System.out.println(notificationResponseList);
        try {
            if (notificationResponseList.isEmpty()) {
                return ApiResponse.<List<NotificationResponse>>notFound(NotificationResponse.class.getSimpleName()).setData(notificationResponseList);
            }
            return ApiResponse.<List<NotificationResponse>>ok(NotificationResponse.class.getSimpleName()).setData(notificationResponseList);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }

    }

    @PostMapping("/studentRequestToJoin/{classId}/{classRoomId}")
    ApiResponse<ClassResponse> requestJoinClassNotification(@RequestParam int classId, @RequestParam int classRoomId) {
        try {
            ClassResponse classResponse = repository.getClassById(classRoomId, classId);
            if (classResponse != null && AuthRestController.userDetails != null) {
//                PushNotificationService
//                        .sendMessageToUser(AuthRestController.userDetails.getName()  + " Request to Join " + classResponse.getClass_name(), classResponse.getCreated_by() + "");
                emailService.send(AuthRestController.userDetails.getName() + " Request to Join ", AuthRestController.userDetails.getName()
                        + " Request to Join Class: " + classResponse.getClass_name()+" of " +classResponse.getClassRoomName(), usersRepository.getUserById(classResponse.getCreated_by()).getEmail());
                return ApiResponse
                        .<ClassResponse>ok(ClassResponse.class.getSimpleName())
                        .setData(classResponse).setResponseMsg("Request Join ClassRoom: " + classResponse.getClassRoomName() + "  Class: " + classResponse.getClass_name());
            } else {
                return ApiResponse
                        .<ClassResponse>notFound("Your classID , ClassroomID , StudentID not Matched");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.setError(e.getMessage());
        }
    }
}
