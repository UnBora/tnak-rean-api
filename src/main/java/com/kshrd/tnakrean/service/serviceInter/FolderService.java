package com.kshrd.tnakrean.service.serviceInter;


import com.kshrd.tnakrean.model.classmaterials.request.FolderDetailRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import com.kshrd.tnakrean.model.classmaterials.response.FolderDetailResponse;
import com.kshrd.tnakrean.model.classmaterials.response.FolderResponse;

import java.util.List;


public interface FolderService {

    boolean createFolder(FolderRequest folderRequest);

    boolean createFolderDetail(FolderDetailRequest folderDetailRequest);

    FolderResponse getFolderById(int id);

    List<FolderResponse> getListFolderByClassId(int id,int classRoomId);

}
