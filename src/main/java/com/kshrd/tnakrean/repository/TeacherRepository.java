package com.kshrd.tnakrean.repository;


import com.kshrd.tnakrean.model.user.response.TeacherResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeacherRepository {
    @Select("SELECT name,email,gender,img FROM users WHERE id = #{id} AND user_role_id = 1")
    TeacherResponse getTeacherById(Integer id);
}
