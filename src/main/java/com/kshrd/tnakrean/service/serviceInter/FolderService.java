package com.kshrd.tnakrean.service.serviceInter;


import com.kshrd.tnakrean.model.classmaterials.request.FolderDetailRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import com.kshrd.tnakrean.model.classmaterials.response.FolderDetailResponse;
import com.kshrd.tnakrean.model.classmaterials.response.FolderResponse;

import java.util.List;


public interface FolderService {

    boolean createFolder(FolderRequest folderRequest);

    boolean createFolderDetail(Integer class_material_detail_id);

    FolderResponse getFolderById(int id);

    List<FolderResponse> getListFolderByClassId(int id, int classRoomId);

    List<FolderResponse> getListFolderByTeacherId(int teacherId);

    FolderResponse deleteByParentId(Integer parent_id);
}
