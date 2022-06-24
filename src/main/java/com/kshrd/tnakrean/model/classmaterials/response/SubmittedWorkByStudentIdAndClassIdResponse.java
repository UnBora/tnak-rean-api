package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kshrd.tnakrean.model.classmaterials.json.StudentResult;
import com.kshrd.tnakrean.model.classmaterials.json.StudentWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkByStudentIdAndClassIdResponse {
    private Integer student_id;
    private Integer class_id;
    private Integer submitted_work_id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date assigned_date;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitted_date;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;

    private StudentWork studentWork; // Json
    private Float student_score;

    private Integer submittable_work_id;
    private Integer class_materials_detail_id;
    private Integer status;
}
