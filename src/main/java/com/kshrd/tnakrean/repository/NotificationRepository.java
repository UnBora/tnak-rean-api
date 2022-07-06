package com.kshrd.tnakrean.repository;


import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.classmaterials.request.NotificationClassRequest;
import com.kshrd.tnakrean.model.classmaterials.request.NotificationUserRequest;
import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import org.apache.ibatis.annotations.*;

import javax.management.remote.NotificationResult;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface NotificationRepository {
    @Select("SELECT nt.id,n.content, n.received_date, nt.title, nt.action_on,nt.type, n.received_id" +
            " from notification n" +
            " JOIN notification_type nt on n.notification_type_id = nt.id" +
            " JOIN notification_detail nd on n.id = nd.noti_id" +
            " JOIN class_materials_detail cmd on nd.action_id = cmd.id" +
            " WHERE n.received_id = #{id}")
    List<NotificationResponse> getNotificationByUserId(Integer id);


    @Insert("insert into notification_draft(notification_type_id, received_id, content, received_date, received_class_id, sender_id, action_id)" +
            "values(#{notificationRequest.notification_type_id},#{notificationRequest.received_id},#{notificationRequest.content}" +
            ",#{notificationRequest.received_date},null,#{notificationRequest.sender_id},#{notificationRequest.action_id})")
    void sendNotificationToUser(@Param("notificationRequest") NotificationUserRequest notificationRequest);

    @Insert("insert into notification_draft(notification_type_id, received_id, content, received_date, received_class_id, sender_id, action_id)" +
            "values(#{notificationRequest.notification_type_id},null,#{notificationRequest.content}" +
            ",#{notificationRequest.received_date},#{notificationRequest.classId},#{notificationRequest.sender_id},#{notificationRequest.action_id})")
    void sendNotificationToClass(@Param("notificationRequest") NotificationClassRequest notificationClassRequest);


}
