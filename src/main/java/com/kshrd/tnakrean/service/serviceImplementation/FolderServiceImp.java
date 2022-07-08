package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.FolderClassWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderCourseRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import com.kshrd.tnakrean.model.classmaterials.response.FolderByClassResponse;
import com.kshrd.tnakrean.model.classmaterials.response.FolderByTeacherResponse;
import com.kshrd.tnakrean.model.classmaterials.response.FolderResponse;
import com.kshrd.tnakrean.repository.FolderRepository;
import com.kshrd.tnakrean.service.serviceInter.FolderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderServiceImp implements FolderService {

    final
    FolderRepository folderRepository;

    public FolderServiceImp(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    @Override
    public boolean createFolder(FolderRequest folderRequest) {
        System.out.println(folderRequest);
        return folderRepository.createFolder(folderRequest);
    }

    @Override
    public boolean createFolderDetail(Integer class_material_detail_id) {
        return folderRepository.createFolderDetail(class_material_detail_id);
    }

    @Override
    public FolderResponse getFolderById(int id) {
        return folderRepository.getFolderByClassId(id);
    }


    @Override
    public List<FolderByClassResponse> getCourseFolderByClassId(int classId, int classRoomId) {
        return folderRepository.getCourseFolderByClassId(classId, classRoomId);
    }

    @Override
    public List<FolderByTeacherResponse> getClassworkFolderByteacherUserId(int user_id) {
        return folderRepository.getClassworkFolderByteacherUserId(user_id);
    }

    @Override
    public FolderResponse deleteByParentId(Integer parent_id) {
        return folderRepository.deleteByParentId(parent_id);
    }

    @Override
    public List<FolderByTeacherResponse> getCourseFolderByTeacher(Integer user_id) {
        return folderRepository.getCourseFolderByTeacher(user_id);
    }

    @Override
    public List<FolderByClassResponse> getClassWorkFolderByClassId(Integer classId, Integer classRoomId) {
        return folderRepository.getClassWorkFolderByClassId(classId,classRoomId);
    }

    @Override
    public boolean createClassWorkFolder(FolderClassWorkRequest folderClassWorkRequest, Integer userId) {
        return  folderRepository.createClassWorkFolder(folderClassWorkRequest,userId);
    }

    @Override
    public boolean createCourseWorkFolder(FolderCourseRequest folderCourseRequest, Integer userId) {
        return folderRepository.createCourseWorkFolder(folderCourseRequest,userId);
    }
}
