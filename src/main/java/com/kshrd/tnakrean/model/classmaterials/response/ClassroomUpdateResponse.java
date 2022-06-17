package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomUpdateResponse {
    Integer id;
    Integer class_id;
    Integer create_by;
    String des;
    String name;
}
