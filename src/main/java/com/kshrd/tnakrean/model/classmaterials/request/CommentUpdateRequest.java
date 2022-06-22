package com.kshrd.tnakrean.model.classmaterials.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateRequest {
    @Min(value = 1, message = "{validation.id.notNegative}")
    private Integer id;
    private Integer student_id;
    private Integer class_materials_detail_id;
    private String comment;
    private Date comment_date;
}
