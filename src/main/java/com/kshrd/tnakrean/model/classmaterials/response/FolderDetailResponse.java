package com.kshrd.tnakrean.model.classmaterials.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kshrd.tnakrean.model.ClassMaterialType;
import com.kshrd.tnakrean.model.classmaterials.json.ClassMaterialContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FolderDetailResponse {
    private Integer id;
    private String title;
    private Integer created_by;
    private String description;
    private String type;

}
