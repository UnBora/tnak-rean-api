package com.kshrd.tnakrean.model.submittedWork.request;

import com.kshrd.tnakrean.model.submittedWork.json.StudentResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkUpdateResultRequest {
    private Integer id;
    private StudentResult studentResult; // Json
}
