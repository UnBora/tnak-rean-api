package com.kshrd.tnakrean.model.classmaterials.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderCourseRequest {
    @NotBlank(message = "{validation.folder.notEmpty}")
    String folder_name;
    @Min(value = 0,message = "{validation.parentId.notNegative}")
    Integer parent_id;
}
