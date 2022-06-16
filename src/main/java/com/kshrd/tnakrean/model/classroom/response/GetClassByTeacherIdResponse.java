package com.kshrd.tnakrean.model.classroom.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetClassByTeacherIdResponse {
    Integer class_id;
    Integer classroom_id;
    String teacher_name;
    String class_name;
    @JsonIgnore
    Integer user_id;
}
