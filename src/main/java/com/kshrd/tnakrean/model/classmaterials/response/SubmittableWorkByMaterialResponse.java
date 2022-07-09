package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittableWorkByMaterialResponse {
    Integer class_id;
    Integer material_id;
    Integer submittable_work_id;
    String title;
    String description;
    Float score;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date assigned_date;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date deadline;
    Integer total_comment;
}