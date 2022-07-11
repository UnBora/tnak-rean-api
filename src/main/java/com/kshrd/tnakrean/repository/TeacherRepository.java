package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.user.response.TeacherByClassAndClassroomResponse;
import com.kshrd.tnakrean.model.user.response.TeacherResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherRepository {
    // get all
    @Select("SELEct u.*, r.role FROM users u " +
            "JOIN user_role r ON u.user_role_id = r.id " +
            "WHERE user_role_id= 2")
    @Result(property = ("user_id"), column = ("id"))
    List<TeacherResponse> getAllTeacher();

    // get by id
    @Select("SELEct u.*, r.role FROM users u " +
            "JOIN user_role r ON u.user_role_id = r.id " +
            "WHERE user_role_id= 2 AND u.id = #{user_id}")
    @Result(property = ("user_id"), column = ("id"))
    TeacherResponse getTeacherById(@Param("user_id") Integer id);

    // get By Class And Classrooms
    @Select("SELECT u.id, u.name, u.username, u.gender, u.email, u.status, d.class_id, d.classroom_id " +
            "FROM users u " +
            "JOIN classroom c ON u.id = c.created_by " +
            "JOIN classroom_detail d ON d.classroom_id = c.id " +
            "WHERE user_role_id = 2 AND classroom_id = #{classroom_id} AND class_id = #{class_id}")
    @Result(property = "user_id", column = "id")
    @Result(property = "teacher_name", column = "name")
    List<TeacherByClassAndClassroomResponse> getByClassAndClassrooms(Integer user_id, Integer class_id, Integer classroom_id);

    @Select("select exists (select * from student where user_id= #{student_id} " +
            "AND classroom_id=#{classroomId} AND class_id= #{classId})")
    Boolean checkIfStudentExists(Integer student_id, Integer classroomId, Integer classId);

    @Select("select username from users where id= #{student_id}")
    String getStudentName(Integer student_id);

    @Select("select class_name from class where id= #{classId}")
    String getClassName(Integer classId);



    @Delete("DELETE  from student where user_id= #{user_id} AND classroom_id=#{classroom_id} AND class_id= #{class_id}")
    @Result(property = "user_id", column = "user_id")
    @Result(property = "classroom_id", column = "classroom_id")
    void removeStudentById(@Param("user_id") Integer user_id, @Param("classroom_id") Integer classroom_id, @Param("class_id") Integer class_id);
}