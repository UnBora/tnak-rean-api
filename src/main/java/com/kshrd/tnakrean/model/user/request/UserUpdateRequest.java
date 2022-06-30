package com.kshrd.tnakrean.model.user.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @JsonIgnore
    Integer user_id;
    @NotBlank(message = "{validation.name.notEmpty}")
    String name;
    @NotBlank(message = "{validation.username.notEmpty}")
    String username;
    @NotBlank(message = "{validation.gender.notEmpty}")
    String gender;
    String img;
    @Email
    String email;
}
