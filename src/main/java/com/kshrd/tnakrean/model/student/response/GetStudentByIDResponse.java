package com.kshrd.tnakrean.model.student.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStudentByIDResponse {
    @JsonIgnore
    Integer user_id;
    String name;
    String username;
    String email;
    String gender;
    @JsonIgnore
    Integer class_id;
    @JsonIgnore
    String password;
}
