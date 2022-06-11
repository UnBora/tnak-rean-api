package com.kshrd.tnakrean.repository;


import com.kshrd.tnakrean.model.student.response.StudentResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentRepository {
    //Get All Student
    @Select("Select * from users where user_role_id= #{user_role_id}")
    List<StudentResponse> getStudentFromDB(@Param("user_role_id") Integer id);

    //   Get Student By ID
    @Select("Select * from users where user_role_id=1 and id= #{id}")
    StudentResponse getStudentFromDBById(@Param("id") Integer id);

    //    User Update Class ID
    @Update("UPDATE student SET class_id = #{new_class_id} WHERE users_id = #{user_id}")
    void updateClassID(@Param("new_class_id") Integer new_class_id, @Param("user_id") Integer user_id);

    //    Delete Student By ID
    @Update("UPDATE users SET status = 0 WHERE id = #{user_id}")
    void studentDeleteAccount(@Param("user_id") Integer user_id);

    //    Student Deactivate Account
    @Delete("UPDATE users SET status = 1 WHERE id = #{id}")
    void studentDeactivateAccount(@Param("id") Integer id);

    //  Student Leave Class
    @Delete("DELETE FROM student WHERE user_id = #{user_id}")
    void studentLeaveClass(@Param("user_id") Integer user_id);

//    Select User by class ID
    @Select("Select * from users where user_id=#{user_id} and id= #{class_id}")
    void selectStudentByClassID(Integer user_id, Integer class_id);

}
