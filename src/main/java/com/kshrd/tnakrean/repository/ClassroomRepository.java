package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.classmaterials.response.ClassroomResponse;
import com.kshrd.tnakrean.model.classmaterials.response.GetClassByTeacherIdResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassroomRepository {

    //    Get All Classroom
    @Select("Select * From classroom")
    List<ClassroomResponse> getAllClassroom();

    //    Get By ID
    @Select("Select * From classroom where id=#{id}")
    @Result(property = "classroom_id", column = "id")
    ClassroomResponse getClassroomByID(Integer id);

    //    Insert Classroom
    @Select("INSERT INTO classroom (class_id, created_by, des, name) VALUES (#{class_id},#{created_by},#{des},#{name})")
    void insertClassroom(@Param("class_id") Integer class_id, @Param("created_by") Integer created_by, @Param("des") String des, @Param("name") String name);

    //    Update Table
    @Update("UPDATE classroom SET des=#{des}, name=#{name}  WHERE created_by = #{created_by} And  id = #{classroom_id}")
    @Result(property = "classroom_id", column = "id")
    void updateClassroomDB(@Param("classroom_id") Integer classroom_id, @Param("created_by") Integer created_by, @Param("name") String name, @Param("des") String des);

    @Select("select exists (select * from classroom where id = #{id} And created_by = #{created_by})")
    Boolean checkIfClassExists( @Param("id") Integer id, @Param("created_by") Integer created_by);



//    Get Class by Teacher ID
    @Select("SELECT c.id as classroom_id, c2.id as class_id,u.username as teacher_name, c2.class_name as class_name From classroom c" +
            " inner join classroom_detail cd on c.id = cd.classroom_id" +
            " inner join class c2 on cd.class_id = c2.id inner join users u on c.created_by= u.id" +
            " where  c.created_by=#{user_id}")
    @Result(property = "classroom_id",column = "classroom_id")
    @Result(property = "class_id",column = "class_id")
    @Result(property = "teacher_name",column = "teacher_name")
    @Result(property = "class_name",column = "class_name")
    @Result(property = "user_id",column = "user_id")
    List<GetClassByTeacherIdResponse> getClassByTeacherId(@Param("c.id") Integer class_id, @Param("c2.id")Integer id, @Param("u.name") String teacherName, @Param("c2.class_name")String classname, @Param("user_id") Integer user_id);
}
