package com.kshrd.tnakrean.model.submittedWork.request;

import com.kshrd.tnakrean.model.submittedWork.json.StudentWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkStudentWorkRequest {
    private Integer student_id;
    private Integer submittable_work_id;
//    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime submitted_date;
    private StudentWork studentWork; // Json
}
