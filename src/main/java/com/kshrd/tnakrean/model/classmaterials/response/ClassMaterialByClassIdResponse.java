package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kshrd.tnakrean.model.ClassMaterialType;
import com.kshrd.tnakrean.model.classmaterials.json.ClassMaterialContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassMaterialByClassIdResponse {
    private Integer class_id;
    private Integer class_material_id;
    private String title;
    private String description;
    private Integer created_by;
    private Integer classroom_id;
    private Integer class_materials_type_id;
    private String class_materials_type;
    private ClassMaterialContent classMaterialContent;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_date;
}
