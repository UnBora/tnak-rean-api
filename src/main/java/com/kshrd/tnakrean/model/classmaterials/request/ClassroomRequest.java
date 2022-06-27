package com.kshrd.tnakrean.model.classmaterials.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomRequest {
    @Min(value = 1, message = "{validation.id.notNegative}")
    Integer class_id;
    @Min(value = 1, message = "{validation.id.notNegative}")
    Integer created_by;
    @NotBlank(message = "{validation.className.notEmpty}")
    String name;
    @NotBlank(message = "{validation.description.notEmpty}")
    String des;
}
