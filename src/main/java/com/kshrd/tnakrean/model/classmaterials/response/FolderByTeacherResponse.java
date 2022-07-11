package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderByTeacherResponse {
    Integer folder_id;
    String folder_name;
    Integer parent_id;
    Integer created_by;
}
