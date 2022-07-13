package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkResultByStudentIdResponse {
    private Integer class_material_id;
    private String title;
    private Integer class_id;
    private Integer submitted_work_id;
    private Integer submittable_work_id;
    private Integer user_id;
    private String name;
    private String gender;
    private Float student_score;
    private Integer submitted_work_status;
    private String ui_status;
    private Integer login_student_id;
}
