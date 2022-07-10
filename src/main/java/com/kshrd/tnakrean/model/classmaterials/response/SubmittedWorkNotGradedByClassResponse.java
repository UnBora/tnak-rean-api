package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkNotGradedByClassResponse {
    private Integer class_material_id;
    private String class_id;
    private Integer created_by;
    private String title;
    private Integer submitted_work_id;
    private Integer submittable_work_id;
    private Integer user_id;
    private String name;
    private Integer status;
}
