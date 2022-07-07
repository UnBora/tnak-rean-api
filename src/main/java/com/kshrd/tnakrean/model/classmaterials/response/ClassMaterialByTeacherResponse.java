package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassMaterialByTeacherResponse {
    private Integer class_id;
    private Integer material_id;
    private String title;
    private String description;
    private Integer total_comment;
    private Integer created_by;
}
