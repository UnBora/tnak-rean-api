package com.kshrd.tnakrean.repository;


import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationRepository {

    @Select("SELECT content, received_date" +
            "FROM notification where received_id = #{id}")
    List<NotificationResponse> notificationResponseList(int id);
}
