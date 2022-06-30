package com.kshrd.tnakrean.model.user.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginRequest {
    @Size(min = 4, max = 20, message ="{validation.username.notEmpty}")
    private String username;
    @Size(min = 3, max = 16, message = "{validation.password.sizenotlesthen3}")
    private String password;
}
