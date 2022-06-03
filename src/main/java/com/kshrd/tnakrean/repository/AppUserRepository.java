package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.user.request.UserLoginRequest;
import com.kshrd.tnakrean.model.user.response.AppUserResponse;
import com.kshrd.tnakrean.model.user.response.RoleResponse;
import com.kshrd.tnakrean.repository.provider.RoleProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.userdetails.UserDetailsService;

@Mapper
public interface AppUserRepository {
    @Select("select * from users where username=#{username}")
    @Result(property = "role", column = "user_role_id", one = @One(select = "selectRoleById"))
    AppUserResponse loginByUserName(String username);

    @Select("SELECT * FROM user_role where id = ${id}")
    RoleResponse selectRoleById(@Param("id") int user_role_id);



    @Update("UPDATE users SET password = #{appUserResponse.password} WHERE id=#{appUserResponse.id}")
    void editPassword(AppUserResponse appUserResponse);


}
