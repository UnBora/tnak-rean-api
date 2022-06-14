package com.kshrd.tnakrean.model.submittedWork.request;

import com.kshrd.tnakrean.model.submittedWork.json.StudentWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkUpdateStudentWorkRequest {
    private Integer id;
    private StudentWork studentWork; // Json
}
