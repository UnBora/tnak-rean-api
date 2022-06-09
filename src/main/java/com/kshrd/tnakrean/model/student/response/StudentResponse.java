package com.kshrd.tnakrean.model.student.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    Integer id;
    Integer user_role_id;
    String name;
    String username;
    String email;
}
