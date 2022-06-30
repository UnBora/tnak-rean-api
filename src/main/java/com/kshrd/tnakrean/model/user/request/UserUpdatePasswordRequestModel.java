package com.kshrd.tnakrean.model.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordRequestModel {
    @Size(min = 3, max = 16)
    String old_password, new_password;
}
