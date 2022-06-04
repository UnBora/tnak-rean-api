package com.kshrd.tnakrean.model.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    Integer user_role_id;
    String name;
    String username;
    String email;
    String password;
    String gender;
}
