package com.kshrd.tnakrean.model.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRemoveStudentByID {
    Integer student_id;
    String student_name;
    String class_name;
}
