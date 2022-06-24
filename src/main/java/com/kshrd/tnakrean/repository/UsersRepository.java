package com.kshrd.tnakrean.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersRepository {
//    get old password
    @Select("select password from users WHERE id=#{user_id}")
    String getPassword(@Param("user_id") int user_id);

    //    Delete Account
    @Update("UPDATE users SET status = 0 WHERE id = #{userId}")
    @Result(property = "user_id", column = "id")
    void deleteAccount( @Param("userId") int userId);

    //    Deactivate Account
    @Update("UPDATE users SET status = 1 WHERE id = #{user_id}")
    @Result(property = "user_id", column = "id")
    void deactivateAccount(@Param("user_id") int user_id);

    //    Activate Account
    @Update("UPDATE users SET status = 2 WHERE id = #{user_id}")
    @Result(property = "user_id", column = "id")
    void activateAccount(@Param("user_id") int user_id);

    //    Update profile
    @Update("UPDATE users SET name=#{name}, username=#{username}, gender=#{gender} WHERE id = #{user_id}")
    void updateProfile(Integer user_id, String name, String username, String gender);
}
