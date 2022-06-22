package com.kshrd.tnakrean.model.classmaterials.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittableWorkRequest {
    @Min(value = 1 , message="{validation.MaterialTypeId.notNegative}")
    Integer class_materials_detail_id;
    Date assigned_date;
    Date deadline;
}
