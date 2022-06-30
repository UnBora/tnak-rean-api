package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDeleteResponse {
    @Min(value = 1, message = "{validation.id.notNegative}")
    Integer class_id;
}
