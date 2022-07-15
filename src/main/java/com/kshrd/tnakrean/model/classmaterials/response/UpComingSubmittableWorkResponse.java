package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kshrd.tnakrean.model.classmaterials.json.ClassMaterialContent;
import com.kshrd.tnakrean.model.classmaterials.json.StudentWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpComingSubmittableWorkResponse {
    String title;
    String description;
    ClassMaterialContent content;
    Integer score;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date deadline;
    Integer total_comment;
    Integer class_material_id;
    Integer class_materials_detail_id;
}
