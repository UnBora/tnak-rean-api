package com.kshrd.tnakrean.repository;
import com.kshrd.tnakrean.model.user.request.TeacherStatusRequest;
import com.kshrd.tnakrean.model.user.response.TeacherResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherRepository {
    // get all
    @Select("SELEct * FROM users WHERE user_role_id= 2")
    @Result(property = ("user_id"), column = ("id"))
    List<TeacherResponse> getAllTeacher();

    // get by id
    @Select("SELECT * FROM users WHERE user_role_id= 2 AND id = #{user_id}")
    @Result(property = ("user_id"), column = ("id"))
    TeacherResponse getTeacherById(@Param("user_id") Integer id);

    // update status
    @Update("UPDATE users SET status = #{status} WHERE id = #{user_id}")
    @Result(property = ("user_id"), column = ("id"))
    boolean teacherStatus(TeacherStatusRequest teacherStatusRequest);

    // delete teacher account
    @Update("UPDATE users SET status = 0 WHERE id = #{user_id}")
    Boolean teacherDeleteAccount(Integer user_id);

    // deactivate account
    @Update("UPDATE users SET status = 1 WHERE id = #{user_id}")
    Boolean deactivateTeacherAccount(Integer user_id);

    // activate account
    @Update("UPDATE users SET status = 2 WHERE id = #{user_id}")
    Boolean activateTeacherAccount(Integer user_id);
}