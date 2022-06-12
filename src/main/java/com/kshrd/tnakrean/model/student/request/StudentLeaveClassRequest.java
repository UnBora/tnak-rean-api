package com.kshrd.tnakrean.model.student.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentLeaveClassRequest {
    Integer user_id;
    Integer classroom_id;
    Integer class_id;
}
