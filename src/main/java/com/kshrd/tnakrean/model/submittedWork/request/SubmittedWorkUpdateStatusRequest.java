package com.kshrd.tnakrean.model.submittedWork.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkUpdateStatusRequest {
    private Integer id;
    private Integer status;
}
