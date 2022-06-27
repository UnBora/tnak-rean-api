package com.kshrd.tnakrean.model.classmaterials.request;

import com.kshrd.tnakrean.model.classmaterials.json.StudentWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkUpdateStudentWorkRequest {
    @Min(value = 1 , message="{validation.id.notNegative}")
    private Integer submitted_work_id;
    @Valid
    private StudentWork studentWork; // Json
}
