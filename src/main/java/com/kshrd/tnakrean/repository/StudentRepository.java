package com.kshrd.tnakrean.repository;


import com.kshrd.tnakrean.model.user.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.model.user.response.GetStudentByIDResponse;
import com.kshrd.tnakrean.model.user.response.GetAllStudentResponse;
import com.kshrd.tnakrean.model.user.response.StudentRequestClassResponse;
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
    @Update("UPDATE users SET status = 1 WHERE id = #{id}")
    void studentDeactivateAccount(@Param("id") Integer id);

    //  Student Leave Class
    @Delete("DELETE  from student where user_id= #{user_id} AND classroom_id=#{classroom_id} AND class_id= #{class_id} ")
    void studentLeaveClassDB(@Param("user_id") Integer user_id, @Param("classroom_id") Integer classroom_id, @Param("class_id") Integer class_id);


    //    Select User by class ID
    @Select("SELECT u.id as user_id,u.name,u.username,u.email,u.gender,s.class_id from  student s " +
            "inner join users u on u.id = s.user_id where s.class_id = #{class_id} and s.classroom_id=#{classroom_id}")
    List<GetStudentByClassIDResponse> selectStudentByClassID(@Param("class_id") Integer class_id, @Param("classroom_id") Integer classroom_id);

    @Insert("INSERT INTO student (user_id, classroom_id, class_id) VALUES (#{user_id},#{classroom_id},#{class_id})")
    void insertUserToTableStudent(@Param("user_id") Integer user_id, @Param("classroom_id") Integer classroom_id, @Param("class_id") Integer class_id);

    //    Activate Account
    @Update("UPDATE users SET status = 2 WHERE id = #{user_id}")
    void studentActivateAccount(@Param("user_id") Integer id);

    //    Update profile
    @Update("UPDATE users SET name=#{name}, username=#{username}, img=#{img} gender=#{gender} WHERE id = #{user_id}")
    void updateProfile(Integer user_id, String name, String username, String img, String gender);

    // student request
    @Select("SELECT r.id, r.user_id, u.name, u.email, u.gender, u.img, s.class_id,s.classroom_id, u.status " +
            "FROM student_request r " +
            "JOIN users u ON r.user_id = u.id " +
            "JOIN student s ON u.id = s.user_id " +
            "WHERE status = -1 AND classroom_id = #{classroom_id} AND class_id = #{class_id}")
    @Result(property = "student_request_id" ,column = "id")
    List<StudentRequestClassResponse> getRequestClass(Integer classroom_id, Integer class_id);
}
