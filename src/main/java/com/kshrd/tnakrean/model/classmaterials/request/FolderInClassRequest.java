package com.kshrd.tnakrean.model.classmaterials.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderInClassRequest {
    @NotBlank(message = "{validation.folder.notEmpty}")
    String folder_name;
    @Min(value = 0,message = "{validation.parentId.notNegative}")
    Integer parent_id;
    @Min(value = 1,message = "{validation.classroomId.notNegative}")
    Integer classroom_id;
    @Min(value = 1,message = "{validation.classId.notNegative}")
    Integer class_id;
//    @Min(value = 2,message = "Type ID should not less than 2")
//    Integer material_type_id;

}
