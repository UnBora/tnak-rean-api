package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.response.ClassResponse;
import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import com.kshrd.tnakrean.model.user.response.GetStudentByIDResponse;
import com.kshrd.tnakrean.repository.OneSignalPushNotificationRepository;
import com.kshrd.tnakrean.repository.StudentRepository;
import com.kshrd.tnakrean.service.PushNotificationService;
import com.kshrd.tnakrean.service.serviceImplementation.NotificationImp;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    final
    NotificationImp notificationImp;
    final OneSignalPushNotificationRepository repository;
    final StudentRepository studentRepository;

    public NotificationController(NotificationImp notificationImp, OneSignalPushNotificationRepository repository, StudentRepository studentRepository) {
        this.notificationImp = notificationImp;
        this.repository = repository;
        this.studentRepository = studentRepository;
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

    @PostMapping("/studentRequestToJoin/{studentId}/{classId}/{classRoomId}")
    ApiResponse<ClassResponse> requestJoinClassNotification(@RequestParam int studentId, @RequestParam int classId, @RequestParam int classRoomId) {
        try {
            ClassResponse classResponse = repository.getClassById(classRoomId, classId);
            GetStudentByIDResponse response = studentRepository.getStudentFromDBById(studentId);
            if (classResponse != null && response != null) {
                PushNotificationService
                        .sendMessageToUser(response.getName() + " Request to Join " + classResponse.getName(), classResponse.getCreated_by() + "");
                return ApiResponse
                        .<ClassResponse>ok(ClassResponse.class.getSimpleName()).setData(classResponse);
            } else {
                return ApiResponse
                        .<ClassResponse>notFound(GetStudentByIDResponse.class.getSimpleName()).setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.setError(e.getMessage());
        }
    }






}
