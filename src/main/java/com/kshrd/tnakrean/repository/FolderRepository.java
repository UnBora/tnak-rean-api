package com.kshrd.tnakrean.repository;


import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.classmaterials.request.FolderDetailRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import com.kshrd.tnakrean.model.classmaterials.response.FolderDetailResponse;
import com.kshrd.tnakrean.model.classmaterials.response.FolderResponse;
import com.kshrd.tnakrean.repository.provider.FolderProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FolderRepository {
    @InsertProvider(type = FolderProvider.class, method = "createFolder")
    boolean createFolder(@Param("folder") FolderRequest folderRequest);

    @InsertProvider(type = FolderProvider.class, method = "createFolderDetail")
    boolean createFolderDetail(@Param("folder") FolderDetailRequest folderDetailRequest);

    @Select("SELECT * FROM folder WHERE id = #{id}")
    @Result(property = "id", column = "id")
    @Result(property = "folderDetailResponseList", column = "id", many = @Many(
            select = "getFolderDetail"
    ))
    FolderResponse getFolderByClassId(int id);


    @Select("SELECT f.folder_name,f.id ,parent_id FROM folder f " +
            "INNER JOIN folder_detail fd on f.id = fd.folder_id INNER JOIN class_materials_detail" +
            " cmd on fd.class_materials_detail_id = cmd.id WHERE cmd.class_id = #{classId} AND cmd.classroom_id = #{classRoomId}")
    @Result(property = "content", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "id", column = "id")
    @Result(property = "folderDetailResponseList", column = "id", many = @Many(
            select = "getFolderDetail"
    ))
    List<FolderResponse> getListFolderByClassId(int classId, int classRoomId);

    @Select("SELECT content ,folder_id FROM folder_detail WHERE folder_id = #{id}")
    @Result(property = "content", column = "content", typeHandler = JsonTypeHandler.class)
    List<FolderDetailResponse> getFolderDetail(int id);


}
