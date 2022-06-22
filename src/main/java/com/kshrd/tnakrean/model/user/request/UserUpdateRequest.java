package com.kshrd.tnakrean.model.user.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @JsonIgnore
    Integer user_id;
    String name;
    String username;
    String gender;
}
