package com.kshrd.tnakrean.model.classroom.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomRequest {
    Integer class_id;
    Integer created_by;
    String des;
    String name;
}
