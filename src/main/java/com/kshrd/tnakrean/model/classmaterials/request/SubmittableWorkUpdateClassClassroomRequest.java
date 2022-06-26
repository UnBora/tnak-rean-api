package com.kshrd.tnakrean.model.classmaterials.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittableWorkUpdateClassClassroomRequest {
    @Min(value = 1 , message="{validation.id.notNegative}")
    Integer submittable_work_id;
    @Min(value = 1 , message="{validation.MaterialTypeId.notNegative}")
    Integer class_materials_detail_id;
    @Min(value = 1 , message="{validation.classroomId.notNegative}")
    Integer classroom_id;
    @Min(value = 1 , message="{validation.classId.notNegative}")
    Integer class_id;
}