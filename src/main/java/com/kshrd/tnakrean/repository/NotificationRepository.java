package com.kshrd.tnakrean.repository;


import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationRepository {

    @Select("SELECT *" +
            "FROM notification where received_id = #{id}")
    @Result(property = "timestamp",column = "received_date")
    @Result(property = "content", column = "content", typeHandler = JsonTypeHandler.class)
    List<NotificationResponse> notificationResponseList(int id);
}
