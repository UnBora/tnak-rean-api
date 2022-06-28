package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassUpdateResponse {
    @Min(value = 1, message = "{validation.id.notNegative}")
    Integer id;
    @NotBlank(message = "{validation.className.notEmpty}")
    String classname;
}
