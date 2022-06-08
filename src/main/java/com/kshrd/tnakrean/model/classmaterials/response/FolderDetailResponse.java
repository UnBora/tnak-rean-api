package com.kshrd.tnakrean.model.classmaterials.response;

import com.kshrd.tnakrean.model.classmaterials.json.FolderContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FolderDetailResponse {
    Integer folder_id;
    FolderContent content;
    Integer class_materials_detail_id;
}
