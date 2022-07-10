package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kshrd.tnakrean.model.classmaterials.json.ClassMaterialContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassMaterialByTeacherIdAndClassIdResponse {
    private Integer class_id;
    private Integer created_by;
    private Integer material_id;
    private String title;
    private String description;
    private Integer total_comment;
}
