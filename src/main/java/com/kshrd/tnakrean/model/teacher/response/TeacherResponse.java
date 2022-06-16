package com.kshrd.tnakrean.model.teacher.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponse {
    Integer user_id;
    String name;
    String username;
    String email;
    String gender;
    String status;
}
