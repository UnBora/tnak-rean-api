package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.classmaterials.response.ClassResponse;
import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import com.kshrd.tnakrean.model.user.request.UserActivateAccountRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OneSignalPushNotificationRepository {

    @Insert("INSERT INTO reqee ")
    Boolean studentRequestToJoinClass(UserActivateAccountRequest userActivateAccountRequest);

    @Select("SELECT")
    Boolean checkingStudentAlreadyRequest(Integer userId);

    @Select("SELECT content , received_date FROM notification n INNER JOIN notification_type nt on n.notification_type_id =" + " nt.id INNER JOIN users u on u.id = n.received_id WHERE u.id = = #{id}")
    NotificationResponse getNotificationByUserId(Integer id);


    @Select("SELECT cr.created_by , cr.name FROM classroom cr " +
            "INNER JOIN class c on c.id = cr.class_id WHERE cr.id = #{classRoomId} AND c.id = #{classId}")
    ClassResponse getClassById(int classRoomId,int classId);


}
