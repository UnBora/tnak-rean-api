package com.kshrd.tnakrean.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClassRepository {

//    Insert Class
    @Insert("INSERT INTO class (class_name)VALUES (#{class_name})")
    void insertClass(@Param("class_name") String class_name);

//    Delete Class
    @Delete("DELETE  from class where id= #{class_id}")
    void deleteClass(@Param("class_id") Integer class_id);
}
