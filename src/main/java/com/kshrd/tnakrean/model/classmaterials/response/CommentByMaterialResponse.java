package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentByMaterialResponse {
    private Integer class_material_id;
    private String name;
    private String img;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date comment_date;
}
