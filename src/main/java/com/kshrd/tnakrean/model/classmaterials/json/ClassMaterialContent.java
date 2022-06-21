package com.kshrd.tnakrean.model.classmaterials.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassMaterialContent {
    @Min(value = 1 , message="{validation.id.notNegative}")
    int id;
    @NotBlank(message = "{validation.body.notEmpty}")
    String body;
}
