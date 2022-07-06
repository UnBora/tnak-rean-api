package com.kshrd.tnakrean.model.user.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStudentByClassIDResponse {
    Integer stu_user_id;
    String name;
    String email;
    String gender;
    Integer class_id;
}
