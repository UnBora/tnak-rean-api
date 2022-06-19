package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import com.kshrd.tnakrean.repository.NotificationRepository;
import com.kshrd.tnakrean.service.serviceInter.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationImp implements NotificationService {
    final
    NotificationRepository notificationRepository;

    public NotificationImp(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<NotificationResponse> notificationList(int id) {
        return notificationRepository.getNotificationByUserId(id);
    }
}
