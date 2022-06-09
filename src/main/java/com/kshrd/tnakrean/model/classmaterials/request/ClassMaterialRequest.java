package com.kshrd.tnakrean.model.classmaterials.request;

import com.kshrd.tnakrean.model.ClassMaterialType;
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
    private Integer class_materials_type_id;
    private ClassMaterialContent classMaterialContent;
    private Integer id;
    private String title;
    private Integer created_by;
    private String description;
    private Date created_date;
}
