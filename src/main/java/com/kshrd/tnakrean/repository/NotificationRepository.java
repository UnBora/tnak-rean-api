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
    @Result(property = "content", column = "content", typeHandler = JsonTypeHandler.class)
    List<NotificationResponse> getNotificationByUserId(Integer id);


    @Insert("INSERT INTO notification_detail (noti_id,action_id) " +
            " VALUES((insert into notification(notification_type_id" +
            ",sender_id,received_id,content,received_date) VALUES(#{notificationRequest.notification_type_id},#{notificationRequest.sender_id},#{notificationRequest.received_id},#{notificationRequest.content}" +
            ",#{notificationRequest.received_date}) returning id), #{notificationClassRequest.action_id})")
    void sendNotificationToUser(@Param("notificationRequest") NotificationUserRequest notificationRequest);

//    @Insert("INSERT INTO notification(notification_type_id, sender_id, received_class_id, content) " +
//            "VALUES (#{notificationClassRequest.notification_type_id}," +
//            "#{notificationClassRequest.sender_id}," +
//            " #{notificationClassRequest.classId},#{notificationClassRequest.content})")
//    Integer insertClassNotification(@Param("notificationClassRequest") NotificationClassRequest notificationClassRequest);


    @Insert("INSERT INTO notification_detail (noti_id,action_id)" +
            " VALUES((insert into notification(notification_type_id,sender_id" +
            " ,received_class_id,content,received_date) " +
            " VALUES(#{notificationClassRequest.notification_type_id},#{notificationClassRequest.sender_id}" +
            " ,#{notificationClassRequest.classId},#{notificationRequest.content}" +
            " ,#{notificationRequest.received_date}) returning id), #{notificationClassRequest.action_id})")
    void sendNotificationToClass(NotificationClassRequest notificationClassRequest);


}
