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
    @Result(property = "user_id", column = "id")
    GetStudentByIDResponse getStudentFromDBById(@Param("id") Integer id);

    //  Student Leave Class
    @Delete("DELETE  from student where user_id= #{user_id} AND classroom_id=#{classroom_id} AND class_id= #{class_id} ")
    void studentLeaveClassDB(@Param("user_id") Integer user_id, @Param("classroom_id") Integer classroom_id, @Param("class_id") Integer class_id);


    //  Get student by class ID
    @Select("SELECT * FROM student st \n" +
            "JOIN users u ON st.user_id = u.id\n" +
            "WHERE class_id = #{class_id} AND status = 2")
    @Result(column = "id", property = "stu_user_id")
    List<GetStudentByClassIDResponse> selectStudentByClassID(@Param("class_id") Integer class_id);

//    @Insert("INSERT INTO student (user_id, classroom_id, class_id) VALUES (#{user_id},#{classroom_id},#{class_id})")
    @Update("UPDATE users SET status = 2 WHERE id = #{user_id}")
    void insertUserToTableStudent(@Param("user_id") Integer user_id);

//    check id Student
   @Select("select exists (select * from student where user_id= #{user_id} " +
           "AND classroom_id=#{classroomId} AND class_id= #{class_id})")
    Boolean checkIfStudentExists(Integer user_id, Integer classroomId, Integer class_id);

    @Select("select exists (select * from student where " +
            " class_id= #{class_id})")
    Boolean checkIfStudentclassIDClassroomIDExists(Integer class_id);

//   Check User ID
    @Select("select exists (select * from users where id = #{id})")
   Boolean checkIfUserIDExists(Integer user_id);

    //    Update profile
    @Update("UPDATE users SET name=#{name}, username=#{username}, img=#{img} gender=#{gender} WHERE id = #{user_id}")
    void updateProfile(Integer user_id, String name, String username, String img, String gender);

    // get student request
    @Select("SELECT r.id, r.user_id, c.class_name, s.class_id,u.name, u.img, status, " +
            " (SELECT count(uc.id) FROM student_request src\n" +
            "JOIN users uc ON src.user_id = uc.id\n" +
            "JOIN student stc ON uc.id = stc.user_id\n" +
            "WHERE status = -1 AND class_id = s.class_id)\n" +
            "FROM student_request r \n" +
            "JOIN users u ON r.user_id = u.id \n" +
            "JOIN student s ON u.id = s.user_id \n" +
            "JOIN class c ON s.class_id = c.id\n" +
            "WHERE status = -1 AND classroom_id = #{classroom_id} AND class_id = #{class_id} ")
    @Result(property = "student_request_id" ,column = "id")
    @Result(property = "total_request" ,column = "count")
    List<StudentRequestClassResponse> getRequestClass(Integer classroom_id, Integer class_id);
//    check role user before insert to table
    @Select("select user_role_id from users where id =#{userId}")
    Integer checkUserRole(Integer userId);

    //   Check Classroom ID
    @Select("select exists (select * from classroom where id = #{classroomId})")
    Boolean checkIfClassroomIDExists(Integer classroomId);

    //   Check Classroom ID
    @Select("select exists (select * from class where id = #{classId})")
    Boolean checkIfClassIDExists(Integer classId);


    @Select("SELECT u.id,u.name,u.email,u.gender,u.status ,s.class_id,s.classRoom_id FROM users u \n" +
            "INNER JOIN student s on u.id = s.user_id WHERE u.id = #{id}")
    GetStudentByIDResponse getStudentDetailById(@Param("id") Integer user_id);

    @Delete("DELETE FROM student_request WHERE user_id = #{user_id}")
    void removeStudentFromStudentRequest(Integer user_id);
}
