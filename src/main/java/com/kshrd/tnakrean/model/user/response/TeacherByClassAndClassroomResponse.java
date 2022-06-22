package com.kshrd.tnakrean.model.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherByClassAndClassroomResponse {
    private Integer user_id;
    private Integer class_id;
    private Integer classroom_id;
    private String teacher_name;
    private String username;
    private String email;
    private String gender;
    private String status;

}
