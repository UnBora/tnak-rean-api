package com.kshrd.tnakrean.model.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeactivateAccountRequest {
    Integer user_id;
}
