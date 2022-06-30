package com.kshrd.tnakrean.model.classmaterials.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kshrd.tnakrean.model.classmaterials.json.ClassMaterialContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassMaterialRequest {
    @NotBlank(message = "{validation.title.notEmpty}")
    private String title;
    @Min(value = 1 , message="{validation.createdBy.notNegative}")
    private Integer created_by;
    @NotBlank(message = "{validation.description.notEmpty}")
    private String description;
    @Min(value = 1 , message="{validation.MaterialTypeId.notNegative}")
    private Integer class_materials_type_id;
    @Valid
    private ClassMaterialContent classMaterialContent;
    @JsonIgnore
    private LocalDateTime created_date = LocalDateTime.now(ZoneOffset.of("+07:00"));
}
