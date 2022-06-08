package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolderResponse {
    Integer id;
    String folder_name;
    List<FolderDetailResponse> folderDetailResponseList;
    Integer parent_id;
}
