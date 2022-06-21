package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.ClassMaterialType;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
import com.kshrd.tnakrean.model.classmaterials.response.ClassroomResponse;
import com.kshrd.tnakrean.model.user.request.TeacherStatusRequest;
import com.kshrd.tnakrean.model.user.response.TeacherByClassAndClassroomResponse;
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

    // delete teacher account
    @Update("UPDATE users SET status = 0 WHERE id = #{user_id}")
    Boolean teacherDeleteAccount(Integer user_id);

    // deactivate account
    @Update("UPDATE users SET status = 1 WHERE id = #{user_id}")
    Boolean deactivateTeacherAccount(Integer user_id);

    // activate account
    @Update("UPDATE users SET status = 2 WHERE id = #{user_id}")
    Boolean activateTeacherAccount(Integer user_id);

    // get By Class And Classrooms
    @Select("SELECT u.id, u.name, u.username, u.gender, u.email, u.status, d.class_id, d.classroom_id\n" +
            "FROM users u\n" +
            "JOIN classroom c ON u.id = c.created_by\n" +
            "JOIN classroom_detail d ON d.classroom_id = c.id\n" +
            "WHERE user_role_id = 2 AND classroom_id = #{classroom_id} AND class_id = #{class_id}")
    @Result(property = "user_id", column = "id")
    @Result(property = "teacher_name", column = "name")
    List<TeacherByClassAndClassroomResponse> getByClassAndClassrooms(Integer user_id, Integer class_id, Integer classroom_id);
}