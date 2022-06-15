package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.user.request.UserRegisterRequest;
import com.kshrd.tnakrean.model.user.response.AppUserResponse;
import com.kshrd.tnakrean.model.user.response.RoleResponse;
import com.kshrd.tnakrean.repository.provider.UserProvider;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AppUserRepository {
    @Select("select * from users where username=#{username}")
    @Result(property = "role", column = "user_role_id", one = @One(select = "selectRoleById"))
    AppUserResponse loginByUserName(String username);

    @Select("SELECT * FROM user_role where id = ${id}")
    RoleResponse selectRoleById(@Param("id") int user_role_id);


    @Update("UPDATE users SET password = #{new_password} WHERE id=#{user_id}")
    void editPassword(@Param("new_password") String new_password,@Param("user_id") int user_id);

    @Select("select password from users WHERE id=#{user_id}")
    String getPassword(@Param("user_id") int user_id);

    @InsertProvider(type = UserProvider.class, method = "userRegister")
    void userRegister(@Param("userRegister") UserRegisterRequest userRegisterRequest);
}
