package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.teacher.response.TeacherResponse;
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


}
