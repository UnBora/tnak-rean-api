package com.kshrd.tnakrean.model.classmaterials.request;

import com.kshrd.tnakrean.model.classmaterials.json.StudentWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkStudentWorkRequest {
    @Min(value = 1 , message="{validation.submittableWorkId.notNegative}")
    private Integer submittable_work_id;
//    @JsonFormat(pattern="yyyy-MM-dd")
    @Valid
    private StudentWork studentWork; // Json
}
