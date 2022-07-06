package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import com.kshrd.tnakrean.model.classmaterials.response.FolderByClassResponse;
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
    public List<FolderResponse> getListFolderByTeacherId(int teacherId) {
        return folderRepository.getListFolderByTeacherId(teacherId);
    }

    @Override
    public FolderResponse deleteByParentId(Integer parent_id) {
        return folderRepository.deleteByParentId(parent_id);
    }

    @Override
    public List<FolderResponse> getCourseFolderByTeacher(Integer user_id,Integer classroom_id) {
        return folderRepository.getCourseFolderByTeacher(user_id, classroom_id);
    }
}
