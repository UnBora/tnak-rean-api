package com.kshrd.tnakrean.model.classmaterials.request;

import com.kshrd.tnakrean.model.classmaterials.json.StudentWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkStudentScoreRequest {
    @Min(value = 1 , message="{validation.id.notNegative}")
    private Integer id;
    @Min(value = 1 , message="{validation.studentId.notNegative}")
    private Integer student_id;
    @Min (message = "{validation.score.notEmpty}", value = 0)
    private Float student_score;

}
