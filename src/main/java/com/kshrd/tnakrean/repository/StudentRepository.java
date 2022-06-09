package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.student.response.StudentResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentRepository {

    @Select("Select * from users where user_role_id= #{user_role_id}")
    List<StudentResponse> getStudentFromDB(@Param("user_role_id") Integer id);
}
