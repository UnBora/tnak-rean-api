package com.kshrd.tnakrean.model.user.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    @JsonIgnore
    Integer user_role_id=1;
    @Size(min = 3, max = 20)
    @NotBlank(message = "{validation.name.notEmpty}")
    String name;
    @Size(min = 3, max = 20, message = "{validation.username.size}")
    @NotBlank(message = "{validation.username.notEmpty}")
    @Pattern(regexp = "^[a-z0-9_^-]*$", message = "Cannot Contain Space")
    String username;
    @Email
    String email;
    @NotNull
    Integer classroomId;
    Integer classId;

    @Size(min = 3, max = 16, message = "{validation.password.sizenotlesthen3}")
    String password;
    @Size(min = 1, max = 7)
    String gender;
    String img;
}
