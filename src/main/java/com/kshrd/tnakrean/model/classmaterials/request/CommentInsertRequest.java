package com.kshrd.tnakrean.model.classmaterials.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentInsertRequest {
    @Min(value = 1, message = "{validation.MaterialDetailId.notNegative}")
    private Integer class_materials_detail_id;
    @NotBlank(message = "{validation.comment.notEmpty}")
    private String comment;
    @JsonIgnore
    private LocalDateTime comment_date= LocalDateTime.now(ZoneOffset.of("+07:00"));
}
