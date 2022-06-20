package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.classmaterials.response.ClassResponse;
import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import com.kshrd.tnakrean.model.user.request.StudentRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OneSignalPushNotificationRepository {

    @Insert("INSERT INTO reqee ")
    Boolean studentRequestToJoinClass(StudentRequest studentRequest);

    @Select("SELECT")
    Boolean checkingStudentAlreadyRequest(Integer userId);

    @Select("SELECT content , received_date FROM notification n INNER JOIN notification_type nt on n.notification_type_id =" + " nt.id INNER JOIN users u on u.id = n.received_id WHERE u.id = = #{id}")
    NotificationResponse getNotificationByUserId(Integer id);


    @Select("SELECT c.class_name , cr.name,cr.created_by FROM class c" +
            " INNER JOIN classroom_detail cd on c.id = cd.class_id" +
            " INNER JOIN classroom cr on cd.classroom_id = cr.id WHERE c.id = #{classId} AND cd.classroom_id = #{classRoomId}")
    ClassResponse getClassById(int classRoomId, int classId);


}
