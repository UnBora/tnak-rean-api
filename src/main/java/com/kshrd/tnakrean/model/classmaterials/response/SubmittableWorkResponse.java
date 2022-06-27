package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittableWorkResponse {
    @Min(value = 1 , message="{validation.studentId.notNegative}")
    Integer submittable_work_id;
    Integer class_materials_detail_id;
    Integer classroom_id;
    Integer class_id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date assigned_date;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date deadline;
    Float score;
}