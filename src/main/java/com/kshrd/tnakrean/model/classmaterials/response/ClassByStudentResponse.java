package com.kshrd.tnakrean.model.classmaterials.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassByStudentResponse {
    Integer user_id;
    String class_id;
    String class_name;
}
