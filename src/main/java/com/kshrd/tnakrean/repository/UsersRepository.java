package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.user.response.AppUserResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersRepository {
    //    get old password
    @Select("select password from users WHERE id=#{user_id}")
    String getPassword(@Param("user_id") int user_id);

    //    get status
    @Select("select status from users WHERE id=#{user_id}")
    Integer getStatus(@Param("user_id") int user_id);

    //    Delete Account
    @Update("UPDATE users SET status = 0 WHERE id = #{userId}")
    @Result(property = "user_id", column = "id")
    void deleteAccount(@Param("userId") int userId);

    //    Deactivate Account
    @Update("UPDATE users SET status = 1 WHERE id = #{user_id}")
    @Result(property = "user_id", column = "id")
    void deactivateAccount(@Param("user_id") int user_id);

    //    Activate Account
    @Update("UPDATE users SET status = 2 WHERE id = #{user_id}")
    @Result(property = "user_id", column = "id")
    void activateAccount(@Param("user_id") int user_id);

    //    Update profile
    @Update("UPDATE users SET name=#{name}, username=#{username},email=#{email}, gender=#{gender} WHERE id = #{user_id}")
    void updateProfile(Integer user_id, String name, String username, String email, String gender);

    @Select("select exists (select email from users where email =#{email})")
    Boolean checkEmailExist(String email);

    //    Select name
    @Select("select username from users where id =#{id}")
    String selectName(Integer id);

    @Select("select email from users where id =#{id}")
    String selectEmail(Integer id);

    @Select("select exists (select username from users where username =#{username})")
    Boolean checkUserName(String username);

    @Select("select exists (select * from users where id =#{id})")
    Boolean checkUserById(Integer id);

    @Select("select * from users where id = #{id}")
    AppUserResponse getUserById(@Param("id") int userId);


}
