package com.kshrd.tnakrean.model.classmaterials.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderRequest {
    @NotBlank(message = "{validation.folder.notEmpty}")
    String folder_name;
    Integer parent_id;
}
