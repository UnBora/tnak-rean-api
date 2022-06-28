package com.kshrd.tnakrean.model.classmaterials.request;

import com.kshrd.tnakrean.model.classmaterials.json.ClassMaterialContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassMaterialUpdateContentRequest {
    @Min(value = 1 , message="{validation.id.notNegative}")
    private Integer class_material_id;
    @Valid
    private ClassMaterialContent classMaterialContent;
}
