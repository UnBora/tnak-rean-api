package com.kshrd.tnakrean.model.user.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestClassResponse {
    Integer student_request_id;
    Integer user_id;
    String name;
    String email;
    String gender;
    String img;
    Integer class_id;
    Integer classroom_id;
    Integer status;
}
