package com.kshrd.tnakrean.repository.provider;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    public String userRegister() {
        return new SQL() {{
            INSERT_INTO("users");
            VALUES("name,username,password,user_role_id,email,gender", "#{userRegister.name},#{userRegister.username}" +
                    ",#{userRegister.password},#{userRegister.user_role_id},#{userRegister.email},#{userRegister.gender}");
        }}.toString();
    }

}
