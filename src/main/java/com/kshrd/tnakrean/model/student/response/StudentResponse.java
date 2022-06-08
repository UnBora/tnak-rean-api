package com.kshrd.tnakrean.model.student.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    Integer id;
    Integer classroom_id;
    Integer users_id;
    Integer class_id;
}
