package com.kshrd.tnakrean.model.classmaterials.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassMaterialUpdateRequest {
    @Min(value = 1 , message="{validation.id.notNegative}")
    private int id;
    @NotBlank(message = "{validation.title.notEmpty}")
    private String title;
    @NotBlank(message = "{validation.description.notEmpty}")
    private String description;
}
