package com.kshrd.tnakrean.repository.provider;

import org.apache.ibatis.jdbc.SQL;

public class RoleProvider {
    public String findRoleById(int user_role_id) {
        return new SQL() {{
            SELECT("*");
            FROM("user_role");
            WHERE("id = #{user_role_id}");
        }}.toString();
    }
}
