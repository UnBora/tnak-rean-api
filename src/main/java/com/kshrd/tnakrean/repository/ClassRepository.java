package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.classmaterials.request.GetClassRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassRepository {

    //    Insert Class
    @Insert("INSERT INTO class (class_name)VALUES (#{class_name})")
    void insertClass(@Param("class_name") String class_name);

    //    Delete Class
    @Delete("DELETE  from class where id= #{class_id}")
    void deleteClass(@Param("class_id") Integer class_id);

    //    Update Class name
    @Update("UPDATE class SET class_name = #{class_name} WHERE id=#{id}")
    void updateClass(@Param("id") Integer id, @Param("class_name") String class_name);

    @Select("select exists (select * from class where id = #{id});")
    Boolean checkIfClassExists(Integer id);

    //    Get All Class
    @Select("Select * From class")
    @Result(property = ("class_id"), column = ("id"))
    List<GetClassRequest> getAllClass();

    //    Create User By Teacher's ID
    @Insert("INSERT INTO class (id, class_name) VALUES (#{id},#{class_name})")
    void creatClassByUserID(@Param("id") Integer id,@Param("class_name") String class_ame);
}
