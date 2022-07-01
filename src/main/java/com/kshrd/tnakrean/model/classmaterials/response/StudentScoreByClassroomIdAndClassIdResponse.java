package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentScoreByClassroomIdAndClassIdResponse {
    private Integer classroom_id;
    private Integer class_id;
    private Integer submitted_work_id;
    private String title;
    private Integer student_id;
    private String student_name;
    private String gender;
    private Float student_score;
    private Integer class_material_id;
    private Integer submittable_work_id;
    private Integer class_materials_detail_id;
}
