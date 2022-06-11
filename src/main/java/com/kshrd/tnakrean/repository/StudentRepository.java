package com.kshrd.tnakrean.repository;


import com.kshrd.tnakrean.model.student.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.model.student.response.GetStudentByIDResponse;
import com.kshrd.tnakrean.model.student.response.GetAllStudentResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentRepository {
    //Get All Student
    @Select("Select * from users where user_role_id= 1")
    @Result(property = ("student_id"), column = ("id"))
    List<GetAllStudentResponse> getStudentFromDB();

    //   Get Student By ID
    @Select("Select * from users where user_role_id=1 and id= #{id}")
    GetStudentByIDResponse getStudentFromDBById(@Param("id") Integer id);


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
    @Select("SELECT  u.id, u.name,u.username,u.email,u.gender,s.class_id from  student s inner join users u on u.id = s.users_id where s.class_id = #{class_id}")
    @Result(property = ("user_id"), column = ("u.id"))
    List<GetStudentByClassIDResponse> selectStudentByClassID(@Param("class_id") Integer class_id);

}
