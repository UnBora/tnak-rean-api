package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentByMaterialIdResponse {
    private Integer comment_id;
    private Integer class_material_id;
    private Integer class_id;
    private Integer classroom_id;
    private Integer student_id;
    private Integer class_materials_detail_id;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date comment_date;
}
