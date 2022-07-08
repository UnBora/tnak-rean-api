package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kshrd.tnakrean.model.classmaterials.json.StudentWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkByClassResponse {
    private Integer class_material_id;
    private String title;
    private Integer submitted_work_id;
    private Integer submittable_work_id;
    private Integer user_id;
    private String name;
    private String gender;
    private Float student_score;
    private Integer status;
    private String ui_status;
}
