package com.kshrd.tnakrean.model.teacher.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherStatusRequest {
    Integer user_id;
    Integer status;
}
