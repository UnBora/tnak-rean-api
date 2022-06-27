package com.kshrd.tnakrean.model.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponse {
    private Integer user_role_id;
    private String name;
    private String username;
    private String email;
    private String gender;
    private String img;
}
