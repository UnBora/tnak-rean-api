package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.configuration.DataSourceConfiguration;
import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.NotificationClassRequest;
import com.kshrd.tnakrean.model.classmaterials.request.NotificationUserRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassResponse;
import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import com.kshrd.tnakrean.repository.*;
import com.kshrd.tnakrean.service.serviceImplementation.NotificationImp;
import com.kshrd.tnakrean.service.serviceInter.EmailService;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
@CrossOrigin(origins = "*")
public class NotificationController {
    final NotificationImp notificationImp;
    final NotificationRepository notificationRepository;
    final OneSignalPushNotificationRepository repository;
    final StudentRepository studentRepository;
    private final EmailService emailService;
    final UsersRepository usersRepository;
    final ClassMaterialRepository classMaterialRepository;
    final ClassRepository classRepository;

    final DataSourceConfiguration dataSourceConfiguration;

    public NotificationController(NotificationImp notificationImp, NotificationRepository notificationRepository, OneSignalPushNotificationRepository repository, StudentRepository studentRepository, EmailService emailService, UsersRepository usersRepository, ClassMaterialRepository classMaterialRepository, ClassRepository classRepository, DataSourceConfiguration dataSourceConfiguration) {
        this.notificationImp = notificationImp;
        this.notificationRepository = notificationRepository;
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.emailService = emailService;
        this.usersRepository = usersRepository;
        this.classMaterialRepository = classMaterialRepository;
        this.classRepository = classRepository;
        this.dataSourceConfiguration = dataSourceConfiguration;
    }

    @GetMapping("/get")
    ApiResponse<List<NotificationResponse>> getNotificationByUserId(@RequestParam int userId) {
        System.out.println("id:"+userId);
        List<NotificationResponse> notificationResponseList = notificationImp.notificationList(userId);
        System.out.println(notificationResponseList.get(0).toString());
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
//                pushNotificationService
//                        .sendMessageToUser(AuthRestController.userDetails.getName() + " Request to Join " + classResponse.getClass_name(), classResponse.getCreated_by() + "");

                emailService.send(AuthRestController.userDetails.getName() + " Request to Join ", AuthRestController.userDetails.getName() + " Request to Join Class: " + classResponse.getClass_name() + " of " + classResponse.getClassRoomName(), usersRepository.getUserById(classResponse.getCreated_by()).getEmail());
                return ApiResponse.<ClassResponse>ok(ClassResponse.class.getSimpleName()).setData(classResponse).setResponseMsg("Request Join ClassRoom: " + classResponse.getClassRoomName() + "  Class: " + classResponse.getClass_name());
            } else {
                return ApiResponse.<ClassResponse>notFound("Your classID , ClassroomID , StudentID not Matched");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.setError(e.getMessage());
        }
    }

    enum NOTI_TYPE {
        REQUEST(1), COMMENT(2), UPLOAD(3), RESULT(4);

        private int id;

        NOTI_TYPE(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

    }

    @PostMapping("/send-notification-to-user")
    ApiResponse<?> sendNotificationToUser(@RequestBody @Valid NotificationUserRequest notificationClassRequest, NOTI_TYPE notificationType) {
        if (AuthRestController.userDetails == null) {
            return ApiResponse.<NotificationUserRequest>unAuthorized(NotificationUserRequest.class.getSimpleName()).setResponseMsg("Unauthorized!");
        } else if (!usersRepository.checkUserById(notificationClassRequest.getReceived_id())) {
            return ApiResponse.<NotificationUserRequest>notFound(NotificationUserRequest.class.getSimpleName()).setResponseMsg("Received id does not exist");
        }
        notificationClassRequest.setNotification_type_id(notificationType.id);
        notificationClassRequest.setSender_id(AuthRestController.userDetails.getId());
        String username = notificationRepository.findUsername(notificationClassRequest.getReceived_id());
        String class_name = null;
        String materail = null;
        String result = null;
        String message;
        String messages=null;
        if(notificationType.toString().equalsIgnoreCase("request")){
            message = "request to join";
            class_name = notificationRepository.findClassName(notificationClassRequest.getAction_id());
            messages = username+" "+" "+message+" "+ class_name;
        }
        if(notificationType.toString().equalsIgnoreCase("comment")){
            message = "add new commented on ";
            materail = notificationRepository.findMaterial(notificationClassRequest.getAction_id());
            messages = username+" "+message+" "+materail;
        }
        if(notificationType.toString().equalsIgnoreCase("upload")){
            message = "has been uplaod new rescource ";
            materail = notificationRepository.findMaterial(notificationClassRequest.getAction_id());
            messages = username+" "+message+" "+materail;
        }
        if(notificationType.toString().equalsIgnoreCase("result")){
            message = "has been sent the result of ";
            materail = notificationRepository.findMaterial(notificationClassRequest.getAction_id());
            messages = username+" "+message+" "+materail;
        }
        notificationClassRequest.setContent(messages);
        notificationRepository.sendNotificationToUser(notificationClassRequest);
        return ApiResponse.<NotificationClassRequest>
                        ok(NotificationUserRequest.class.getSimpleName())
                .setResponseMsg(messages);
    }




    @PostMapping("/send-notification-to-class")
    ApiResponse<NotificationClassRequest> sendNotificationToClass(@RequestBody @Valid NotificationClassRequest notificationClassRequest, NOTI_TYPE notificationType) {
        System.out.println("noti:"+notificationType);
        try {
            if (AuthRestController.userDetails == null) {
                return ApiResponse.<NotificationClassRequest>unAuthorized(NotificationClassRequest.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");
            } else if (!classRepository.checkIfClassRoomDetailExists(notificationClassRequest.getClassId())) {
                return ApiResponse.<NotificationClassRequest>notFound(NotificationClassRequest.class.getSimpleName())
                        .setResponseMsg("class id does not exist");
//            } else if (!classMaterialRepository.checkMaterialsId(notificationClassRequest.getAction_id())) {
//                return ApiResponse.<NotificationClassRequest>notFound(NotificationClassRequest.class.getSimpleName())
//                        .setResponseMsg("Action id does not exist");
            }
            notificationClassRequest.setNotification_type_id(notificationType.id);
            notificationClassRequest.setSender_id(AuthRestController.userDetails.getId());
            String username = notificationRepository.findUsername(AuthRestController.userDetails.getId());
            String class_name = null;
            String materail = null;
            String result = null;
            String message;
            String messages=null;
            if(notificationType.toString().equalsIgnoreCase("request")){
                message = "request to join";
                class_name = notificationRepository.findClassName(notificationClassRequest.getClassId());
                messages = username+" "+" "+message+" "+ class_name;
            }
            if(notificationType.toString().equalsIgnoreCase("comment")){
                message = "add new commented on ";
                materail = notificationRepository.findMaterial(notificationClassRequest.getAction_id());
                class_name = notificationRepository.findClassName(notificationClassRequest.getClassId());
                messages = username+" "+message+" "+materail+" on class "+class_name;
            }
            if(notificationType.toString().equalsIgnoreCase("upload")){
                message = "has been uplaod new rescource ";
                class_name = notificationRepository.findClassName(notificationClassRequest.getClassId());
                materail = notificationRepository.findMaterial(notificationClassRequest.getAction_id());
                messages = username+" "+message+" "+materail+" in "+ class_name;
            }
            if(notificationType.toString().equalsIgnoreCase("result")){
                message = "has been sent the result of ";
                class_name = notificationRepository.findClassName(notificationClassRequest.getClassId());
                materail = notificationRepository.findMaterial(notificationClassRequest.getAction_id());
                messages = username+" "+message+" "+materail+" in "+ class_name;
            }
            notificationClassRequest.setContent(messages);
            notificationRepository.sendNotificationToClass(notificationClassRequest);
            return ApiResponse.<NotificationClassRequest>
                            ok(NotificationClassRequest.class.getSimpleName())
                    .setResponseMsg(messages);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
