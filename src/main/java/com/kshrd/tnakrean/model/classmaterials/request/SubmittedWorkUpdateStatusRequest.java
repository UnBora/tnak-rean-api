package com.kshrd.tnakrean.model.classmaterials.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkUpdateStatusRequest {
    @Min(value = 1 , message="{validation.id.notNegative}")
    private Integer id;
    @Min(value = 1 , message="{validation.status.notNegative}")
    private Integer status;
}
