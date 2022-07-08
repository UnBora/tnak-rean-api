package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.user.request.UserRegisterRequest;
import com.kshrd.tnakrean.model.user.response.AppUserResponse;
import com.kshrd.tnakrean.repository.provider.UserProvider;
import org.apache.ibatis.annotations.*;

import java.util.Set;

@Mapper
public interface AppUserRepository {
    @Select("select * from users where username=#{username}")
    @Result(property = "role", column = "user_role_id", one = @One(select = "selectRoleById"))
    AppUserResponse loginByUserName(String username);

    @Select("SELECT role FROM user_role where id = ${id}")
    Set<String> selectRoleById(@Param("id") int user_role_id);


    @Update("UPDATE users SET password = #{new_password} WHERE id=#{user_id}")
    void editPassword(@Param("new_password") String new_password,@Param("user_id") int user_id);

    @Select("select password from users WHERE id=#{user_id}")
    String getPassword(@Param("user_id") int user_id);

    @InsertProvider(type = UserProvider.class, method = "userRegister")
    void userRegister(@Param("userRegister") UserRegisterRequest userRegisterRequest);

    @Select("select exists (select * from users where email =#{email})")
    Boolean checkEmailExist(String email);

    @Select("select exists (select * from users where username =#{username})")
    Boolean checkUserName(String username);

    @Select("SELECT MAX(id) AS last_id FROM users")
    Integer lastUserId();

    @Insert("INSERT INTO student (user_id,classroom_id,class_id) VALUES(#{userId},#{classroomId},#{classId})")
    void studenRegistrationAndRequese(Integer userId, Integer classroomId, Integer classId);


    @Insert("INSERT INTO student_request (user_id) VALUES (#{user_id})")
    void studentRegisterAddtoStudentRequest(@Param("user_id") Integer user_id);
}
