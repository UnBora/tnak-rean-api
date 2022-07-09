package com.kshrd.tnakrean.model.classmaterials.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderAssignClassRequest {
    @Min(value = 1, message = "{validation.id.notNegative}")
    Integer folder_id;
    @Min(value = 1, message = "{validation.classId.notNegative}")
    Integer class_id;
    @Min(value = 1, message = "{validation.classroomId.notNegative}")
    Integer classroom_id;
}
