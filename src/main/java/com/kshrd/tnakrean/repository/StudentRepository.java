package com.kshrd.tnakrean.repository;


import com.kshrd.tnakrean.model.user.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.model.user.response.GetStudentByIDResponse;
import com.kshrd.tnakrean.model.user.response.GetAllStudentResponse;
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

    //  Student Leave Class
    @Delete("DELETE  from student where user_id= #{user_id} AND classroom_id=#{classroom_id} AND class_id= #{class_id} ")
    void studentLeaveClassDB(@Param("user_id") Integer user_id, @Param("classroom_id") Integer classroom_id, @Param("class_id") Integer class_id);


    //    Select User by class ID
    @Select("SELECT u.id as user_id,u.name,u.username,u.email,u.gender,s.class_id from  student s " +
            "inner join users u on u.id = s.user_id where s.class_id = #{class_id} and s.classroom_id=#{classroom_id}")
    List<GetStudentByClassIDResponse> selectStudentByClassID(@Param("class_id") Integer class_id, @Param("classroom_id") Integer classroom_id);

    @Insert("INSERT INTO student (user_id, classroom_id, class_id) VALUES (#{user_id},#{classroom_id},#{class_id})")
    void insertUserToTableStudent(@Param("user_id") Integer user_id, @Param("classroom_id") Integer classroom_id, @Param("class_id") Integer class_id);

//    check id class_id
   @Select("select exists (select * from student where user_id= #{user_id} AND classroom_id=#{classroom_id} AND class_id= #{class_id})")
    Boolean checkIfClassExists(@Param("user_id") Integer user_id, @Param("classroom_id") Integer classroom_id, @Param("class_id") Integer class_id);

   //    check id class_id
   @Select("select exists (select * from class where id = #{id})")
    Boolean checkIfClassExistsClassroom(Integer id);
}
