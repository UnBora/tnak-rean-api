package com.kshrd.tnakrean.model.classmaterials.request;

import com.kshrd.tnakrean.model.classmaterials.json.StudentResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmittedWorkUpdateResultRequest {
    @Min(value = 1 , message="{validation.id.notNegative}")
    private Integer id;
    @Valid
    private StudentResult studentResult; // Json
}
