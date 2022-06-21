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

    @Select("SELECT f.folder_name,f.id ,f.parent_id FROM class_materials cm " +
            "INNER JOIN class_materials_detail cmd on cm.id = cmd.class_material_id " +
            "INNER JOIN folder_detail fd on cmd.id = fd.class_materials_detail_id " +
            "INNER JOIN folder f on fd.folder_id = f.id WHERE cm.created_by = #{teacherId}")
    @Result(property = "content", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "id", column = "id")
    @Result(property = "folderDetailResponseList", column = "id", many = @Many(
            select = "getFolderDetail"
    ))
    List<FolderResponse> getListFolderByTeacherId(int teacherId);

    @Select("SELECT content ,folder_id FROM folder_detail WHERE folder_id = #{id}")
    @Result(property = "content", column = "content", typeHandler = JsonTypeHandler.class)
    List<FolderDetailResponse> getFolderDetail(int id);


}
