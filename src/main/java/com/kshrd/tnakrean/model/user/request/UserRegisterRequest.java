package com.kshrd.tnakrean.model.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    Integer user_role_id;
    @Size(min = 3, max = 20)
    @NotBlank(message = "{validation.name.notEmpty}")
    String name;
    @Size(min = 3, max = 20, message = "{validation.username.size}")
    @NotBlank(message = "{validation.username.notEmpty}")
    @Pattern(regexp = "^[a-z0-9_^-]*$", message = "Cannot Contain Space")
    String username;
    @Email
    String email;

    @Size(min = 3, max = 16, message = "{validation.password.sizenotlesthen3}")
    String password;
    @Size(min = 1, max = 7)
    String gender;
    String img;
}
