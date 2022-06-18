package com.kshrd.tnakrean.model.classmaterials.request;

import com.kshrd.tnakrean.model.classmaterials.json.ClassMaterialContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassMaterialRequest {
    private String title;
    private Integer created_by;
    private String description;
    private Integer class_materials_type_id;
    private ClassMaterialContent classMaterialContent;
    private Date created_date;
}
