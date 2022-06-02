package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import com.kshrd.tnakrean.repository.NotificationRepository;
import com.kshrd.tnakrean.service.serviceInter.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationImp implements NotificationService {
    NotificationRepository notificationRepository;

    @Override
    public List<NotificationResponse> notificationList(int id) {
        return notificationRepository.notificationResponseList(id);
    }
}
