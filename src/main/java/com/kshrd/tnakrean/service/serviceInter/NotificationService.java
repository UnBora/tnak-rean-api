package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> notificationList(int id);

}
