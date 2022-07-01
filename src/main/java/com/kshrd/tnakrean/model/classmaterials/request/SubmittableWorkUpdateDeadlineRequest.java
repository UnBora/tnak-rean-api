package com.kshrd.tnakrean.model.classmaterials.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittableWorkUpdateDeadlineRequest {
    @Min(value = 1 , message="{validation.id.notNegative}")
    Integer submittable_work_id;
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date deadline;
}
