package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.classroom.request.ClassroomRequest;
import com.kshrd.tnakrean.model.classroom.response.ClassroomResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClassroomRepository {

    //    Get All Classroom
    @Select("Select * From classroom")
    List<ClassroomResponse> getAllClassroom();

//    Get By ID
    @Select("Select * From classroom where id=#{id}")
    ClassroomResponse getClassroomByID(Integer id);

//    Insert Classroom
    @Select("INSERT INTO classroom (class_id, created_by, des, name) VALUES (#{class_id},#{created_by},#{des},#{name})")
    void insertClassroom(@Param("class_id") Integer class_id, @Param("created_by") Integer created_by,@Param("des") String des,@Param("name") String name);

    @Select("select exists (select * from classroom where class_id = #{class_id} And created_by= #{created_by} And des=#{des} And name=#{name})")
    Boolean checkIfClassExists(@Param("class_id") Integer class_id, @Param("created_by") Integer created_by,@Param("des") String des,@Param("name") String name);

    void messange( String b);
}
