package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.FolderDetailRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import com.kshrd.tnakrean.model.classmaterials.response.FolderDetailResponse;
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
    public boolean createFolderDetail(FolderDetailRequest folderDetailRequest) {
        System.out.println("in imp service" + folderDetailRequest);
        return folderRepository.createFolderDetail(folderDetailRequest);
    }

    @Override
    public FolderResponse getFolderById(int id) {
        return folderRepository.getFolderByClassId(id);
    }


    @Override
    public List<FolderResponse> getListFolderByClassId(int id, int classRoomId) {
        return folderRepository.getListFolderByClassId(id, classRoomId);
    }

    @Override
    public List<FolderResponse> getListFolderByTeacherId(int teacherId) {
        return folderRepository.getListFolderByTeacherId(teacherId);
    }
}
