package com.kshrd.tnakrean.model.submittedWork.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kshrd.tnakrean.model.submittedWork.json.StudentResult;
import com.kshrd.tnakrean.model.submittedWork.json.StudentWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkResponse {
    private Integer id;
    private Integer student_id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitted_date;
    private Integer status;
    private StudentWork studentWork; // Json
    private StudentResult studentResult; // Json
    private Integer submittable_work_id;
}
