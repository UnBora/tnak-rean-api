package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.classmaterials.request.GetClassRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassByUserTeacherIdResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassRepository {

    //    Insert Class
    @Insert("INSERT INTO class (class_name)VALUES (#{class_name})")
    void insertClass(@Param("class_name") String class_name);

    //    Delete Class
    @Delete("DELETE  from class where id= #{class_id}")
    boolean deleteClass(@Param("class_id") Integer class_id);

    //    Update Class name
    @Update("UPDATE class SET class_name = #{class_name} WHERE id=#{id}")
    void updateClass(@Param("id") Integer id, @Param("class_name") String class_name);

    @Select("select exists (select * from class where id = #{id})")
    Boolean checkIfClassExists(Integer id);

    //check name ex
    @Select("select exists (select * from class where class_name =#{className})")
    Boolean checkIfClassExistsDuplecateClassName(String className);

    //    Get All Class
    @Select("Select * From class")
    @Result(property = ("class_id"), column = ("id"))
    List<GetClassRequest> getAllClass();

    //    Create User By Teacher's ID
    @Insert("INSERT INTO class (id, class_name) VALUES (#{id},#{class_name})")
    void creatClassByUserID(@Param("id") Integer id,@Param("class_name") String class_ame);

    @Select("select exists (select * from classroom_detail where id = #{id})")
    Boolean checkIfClassRoomDetailExists(Integer id);

    // get By TeacherUserId
    @Select("SELECT d.class_id, c.class_name, d.classroom_id ,r.name ,\n" +
            "(SELECT count(dc.id) \n" +
            "FROM classroom_detail dc\n" +
            "JOIN student sc\n" +
            "ON dc.class_id = sc.class_id AND dc.classroom_id = sc.classroom_id \n" +
            "JOIN users u ON u.id = sc.user_id " +
            "WHERE sc.classroom_id = r.id AND sc.class_id = c.id AND status = 2) \n" +
            "FROM classroom_detail d \n" +
            "JOIN class c ON d.class_id = c.id\n" +
            "JOIN classroom r ON d.classroom_id = r.id\n" +
            "WHERE created_by = #{user_id}")
    @Result(property = "classId",column = "class_id")
    @Result(property = "className",column = "class_name")
    @Result(property = "classroomId",column = "classroom_id")
    @Result(property = "classroomName",column = "name")
    @Result(property = "totalStudentInClass",column = "count")
    List<ClassByUserTeacherIdResponse> getByTeacherUserId(Integer user_id);
}
