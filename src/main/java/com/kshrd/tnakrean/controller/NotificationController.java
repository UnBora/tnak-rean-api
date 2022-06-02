package com.kshrd.tnakrean.controller;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import com.kshrd.tnakrean.service.serviceInter.NotificationService;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    NotificationService notificationService;

//    @GetMapping("/get")
//    ApiResponse<List<NotificationResponse>> getNotification(int userId) {
//        List<NotificationResponse> notificationResponseList = notificationService.notificationList(userId);
////        if(notificationResponseList.isEmpty()){
////
////        }
//    }
}
