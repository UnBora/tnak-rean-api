package com.kshrd.tnakrean.model.classmaterials.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassResponse {
    Integer created_by;
    String classRoomName;
    String class_name;
}
