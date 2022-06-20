package com.kshrd.tnakrean.model.classmaterials.request;

import com.kshrd.tnakrean.model.classmaterials.json.ClassMaterialContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassMaterialUpdateContentRequest {
    private Integer id;
    private ClassMaterialContent classMaterialContent;
}
