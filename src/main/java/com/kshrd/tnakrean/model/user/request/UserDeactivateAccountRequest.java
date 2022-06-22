package com.kshrd.tnakrean.model.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeactivateAccountRequest {
    @Min(value = 1, message = "{validation.userId.notEmpty}")
    Integer user_id;
}
