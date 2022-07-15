package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseByStudentIdResponse {
    private Integer class_id;
    private Integer class_material_id;
    private Integer class_materials_detail_id;
    private String title;
    private String description;
    private Integer total_comment;
    private Integer created_by;
}
