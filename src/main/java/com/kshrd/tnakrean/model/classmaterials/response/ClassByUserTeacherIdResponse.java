package com.kshrd.tnakrean.model.classmaterials.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassByUserTeacherIdResponse {
    Integer classId;
    String className;
    Integer totalStudentInClass;

}
