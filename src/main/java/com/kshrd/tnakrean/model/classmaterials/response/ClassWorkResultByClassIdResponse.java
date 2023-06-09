package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassWorkResultByClassIdResponse {
    Integer class_id;
    Integer submittable_work_id;
    Integer class_material_id;
    Integer created_by;
    String title;
    String description;
}