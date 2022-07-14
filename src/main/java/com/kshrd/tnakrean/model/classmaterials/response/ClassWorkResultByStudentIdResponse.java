package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassWorkResultByStudentIdResponse {
    Integer class_id;
    Integer submittable_work_id;
    Integer class_material_id;
    Integer created_by;
    String title;
    String description;
    Integer student_logged_id;
}