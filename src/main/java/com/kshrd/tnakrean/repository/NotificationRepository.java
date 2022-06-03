package com.kshrd.tnakrean.repository;


import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.NotificationTypes;
import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationRepository {

    @Select("SELECT *" +
            "FROM notification where received_id = #{id}")
    @Result(property = "timestamp", column = "received_date")
    @Result(property = "content", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "notificationTypes", column = "notification_type_id",
            one = @One(select = "getNotificationTypeById"))
    List<NotificationResponse> notificationResponseList(int id);


    @Select("SELECT * FROM notification_type WHERE id = #{id}")
    NotificationTypes getNotificationTypeById(int id);
}
