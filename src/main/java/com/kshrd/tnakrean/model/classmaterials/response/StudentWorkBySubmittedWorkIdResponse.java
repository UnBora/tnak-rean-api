package com.kshrd.tnakrean.model.classmaterials.response;

import com.kshrd.tnakrean.model.classmaterials.json.StudentWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentWorkBySubmittedWorkIdResponse {
    private Integer class_id;
    private String title;
    private Integer submitted_work_id;
    private Integer class_material_id;
    private Integer user_id;
    private String name;
    private StudentWork studentWork; // Json
}
