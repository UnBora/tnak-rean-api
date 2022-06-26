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
    @Select("SELECT nt.id,n.content, n.received_date, nt.title, nt.action_on,nt.type, n.received_id" +
            " from notification n" +
            " JOIN notification_type nt on n.notification_type_id = nt.id" +
            " JOIN notification_detail nd on n.id = nd.noti_id" +
            " JOIN class_materials_detail cmd on nd.action_id = cmd.id" +
            " WHERE n.received_id = #{id}")
    @Result(property = "timestamp", column = "received_date")
    @Result(property = "content", column = "content", typeHandler = JsonTypeHandler.class)
    List<NotificationResponse> getNotificationByUserId(Integer id);

}
