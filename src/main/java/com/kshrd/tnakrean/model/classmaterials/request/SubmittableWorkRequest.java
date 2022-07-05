package com.kshrd.tnakrean.model.classmaterials.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittableWorkRequest {
    @Min(value = 1 , message="{validation.MaterialTypeId.notNegative}")
    Integer class_materials_detail_id;
    @Min(value = 1 , message="{validation.classroomId.notNegative}")
    Integer classroom_id;
    @Min(value = 1 , message="{validation.classId.notNegative}")
    Integer class_id;
    @JsonIgnore
    LocalDateTime assigned_date = LocalDateTime.now(ZoneOffset.of("+07:00"));
    Date deadline;
    @Min(value = 0 , message="{validation.score.notEmpty}") @Max(value = 1000 , message="{validation.score.notEmpty}")
    Float score;
}