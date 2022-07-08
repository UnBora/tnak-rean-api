package com.kshrd.tnakrean.model.classmaterials.request;

import com.kshrd.tnakrean.model.classmaterials.json.StudentWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.security.PrivateKey;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkStudentScoreRequest {
    @Min(value = 1 , message="{validation.id.notNegative}")
    private Integer submitted_work_id;
    @Min (value = 0, message = "{validation.score.notEmpty}")
    @Max(value = 1000, message = "{validation.score.notEmpty}")
    private Float student_score;

}
