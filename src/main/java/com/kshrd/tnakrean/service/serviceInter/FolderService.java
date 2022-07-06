package com.kshrd.tnakrean.service.serviceInter;


import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import com.kshrd.tnakrean.model.classmaterials.response.FolderByClassResponse;
import com.kshrd.tnakrean.model.classmaterials.response.FolderResponse;

import java.util.List;


public interface FolderService {

    boolean createFolder(FolderRequest folderRequest);

    boolean createFolderDetail(Integer class_material_detail_id);

    FolderResponse getFolderById(int id);

    List<FolderByClassResponse> getCourseFolderByClassId(int id, int classRoomId);

    List<FolderResponse> getListFolderByTeacherId(int teacherId);

    FolderResponse deleteByParentId(Integer parent_id);

    List<FolderResponse> getCourseFolderByTeacher(Integer user_id, Integer classroom_id);

    List<FolderByClassResponse> getClassWorkFolderByClassId(Integer classId, Integer classRoomId);
}
