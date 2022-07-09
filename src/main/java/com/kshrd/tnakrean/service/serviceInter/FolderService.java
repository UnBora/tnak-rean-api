package com.kshrd.tnakrean.service.serviceInter;


import com.kshrd.tnakrean.model.classmaterials.request.FolderAssignClassRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderClassWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderCourseRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import com.kshrd.tnakrean.model.classmaterials.response.FolderByClassResponse;
import com.kshrd.tnakrean.model.classmaterials.response.FolderByTeacherResponse;
import com.kshrd.tnakrean.model.classmaterials.response.FolderResponse;

import java.util.List;


public interface FolderService {

    boolean createFolder(FolderRequest folderRequest);

    boolean createFolderDetail(Integer class_material_detail_id);

    FolderResponse getFolderById(int id);

    List<FolderByClassResponse> getCourseFolderByClassId(int id, int classRoomId);
    List<FolderByTeacherResponse> getClassworkFolderByteacherUserId(int user_id);

    FolderResponse deleteByParentId(Integer parent_id);

    List<FolderByTeacherResponse> getCourseFolderByTeacher(Integer user_id);

    List<FolderByClassResponse> getClassWorkFolderByClassId(Integer classId, Integer classRoomId);

    boolean createClassWorkFolder(FolderClassWorkRequest folderClassWorkRequest, Integer userId);

    boolean createCourseWorkFolder(FolderCourseRequest folderCourseRequest, Integer userId);

    boolean folderAssignClass(FolderAssignClassRequest folderAssignClassRequest);
}
