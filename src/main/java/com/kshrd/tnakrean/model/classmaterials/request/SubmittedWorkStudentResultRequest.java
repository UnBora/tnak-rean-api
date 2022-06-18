package com.kshrd.tnakrean.model.classmaterials.request;

import com.kshrd.tnakrean.model.classmaterials.json.StudentResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkStudentResultRequest {
    private Integer student_id;
    private Integer submittable_work_id;
    //  private Date submitted_date;
    private StudentResult studentResult; // Json
}
