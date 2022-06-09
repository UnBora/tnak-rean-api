package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import com.kshrd.tnakrean.service.serviceImplementation.NotificationImp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    final
    NotificationImp notificationImp;

    public NotificationController(NotificationImp notificationImp) {
        this.notificationImp = notificationImp;
    }

    @GetMapping("/get")
    ApiResponse<List<NotificationResponse>> getNotification(int userId) {
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
}
