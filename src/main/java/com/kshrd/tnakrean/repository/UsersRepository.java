package com.kshrd.tnakrean.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersRepository {
    //    Delete Account
    @Update("UPDATE users SET status = 0 WHERE id = #{id}")
    void deleteAccount(@Param("id") Integer id);

    //    Deactivate Account
    @Update("UPDATE users SET status = 1 WHERE id = #{id}")
    void deactivateAccount(@Param("id") Integer id);

    //    Activate Account
    @Update("UPDATE users SET status = 2 WHERE id = #{id}")
    void activateAccount(Integer id);

    //    Update profile
    @Update("UPDATE users SET name=#{name}, username=#{username}, gender=#{gender} WHERE id = #{user_id}")
    void updateProfile(Integer user_id, String name, String username, String gender);
}
