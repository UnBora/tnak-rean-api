package com.kshrd.tnakrean.model.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponse {
    Integer user_role_id;
    @Size(min = 3, max = 20)
    @NotBlank(message = "{validation.description.notEmpty}")
    String name;
    @Size(min = 4, max = 20)
    String username;
    @Email
    String email;
    @Size(min = 1, max = 6)
    String gender;
}
