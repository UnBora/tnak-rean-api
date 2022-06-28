package com.kshrd.tnakrean.model.classmaterials.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateRequest {
    @Min(value = 1, message = "{validation.id.notNegative}")
    private Integer comment_id;
    @Min(value = 1, message = "{validation.studentId.notNegative}")
    private Integer student_id;
    @Min(value = 1, message = "{validation.MaterialDetailId.notNegative}")
    private Integer class_materials_detail_id;
    @NotBlank(message = "{validation.comment.notEmpty}")
    private String comment;
    private Date comment_date;
}
