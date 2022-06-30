package com.kshrd.tnakrean.model.classmaterials.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetClassRequest {
    @Min(value = 1, message = "{validation.id.notNegative}")
    Integer class_id;
    @NotBlank(message = "{validation.className.notEmpty}")
    String class_name;
}
