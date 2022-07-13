package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderByStudentIdResponse {
    Integer folder_id;
    String folder_name;
    Integer created_by;
    Integer parent_id;
    String type;
    Integer class_id;
    Integer material_type_id;
}
