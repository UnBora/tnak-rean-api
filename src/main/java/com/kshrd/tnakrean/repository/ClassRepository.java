package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.classmaterials.request.GetClassRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassByUserTeacherIdResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassRepository {

    //    Insert Class
    @Insert("INSERT INTO class (class_name, image)VALUES (#{class_name}, #{image})")
    void insertClass(@Param("class_name") String class_name, String image);

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
    @Select("SELECT c.id,c.class_name, image, \n" +
            "(SELECT count(st.id) FROM student st \n" +
            "JOIN users u ON st.user_id = u.id\n" +
            "WHERE class_id = t.class_id AND status = 2) \n" +
            "FROM class c \n" +
            "JOIN teacher t ON c.id = t.class_id\n" +
            "WHERE user_id = #{user_id} AND classroom_id = #{classroom_id}")
    @Result(property = "classId",column = "id")
    @Result(property = "className",column = "class_name")
    @Result(property = "totalStudentInClass",column = "count")
    List<ClassByUserTeacherIdResponse> getByTeacherUserId(Integer user_id, Integer classroom_id);
}
