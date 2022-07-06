package com.kshrd.tnakrean.repository;


import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import com.kshrd.tnakrean.model.classmaterials.response.FolderByClassResponse;
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
    boolean createFolderDetail(@Param("folder") Integer folderDetailRequest);

    @Select("SELECT * FROM folder WHERE id = #{id}")
    @Result(property = "id", column = "id")
    @Result(property = "folderDetailResponseList", column = "id", many = @Many(
            select = "getFolderDetail"
    ))
    FolderResponse getFolderByClassId(int id);


    @Select("SELECT fd.folder_id, f.folder_name, f.parent_id, ct.type , class_id\n" +
            "FROM folder f \n" +
            "JOIN folder_detail fd ON f.id = fd.folder_id\n" +
            "JOIN class_materials_detail cd ON fd.class_materials_detail_id = cd.id\n" +
            "JOIN class_materials cm ON cd.class_material_id = cm.id\n" +
            "JOIN class_materials_type ct ON cm.class_materials_type_id = ct.id\n" +
            "WHERE class_materials_type_id = 1 AND classroom_id = #{classRoomId} AND class_id = #{classId}")
    List<FolderByClassResponse> getCourseFolderByClassId(int classId, int classRoomId);

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


    @Select("SELECT cm.id, cm.title, cm.content, cm.description, cm.created_by, cmt.type" +
            " from folder_detail fd " +
            " JOIN class_materials_detail cmd on fd.class_materials_detail_id = cmd.id" +
            " JOIN class_materials cm on cmd.class_material_id = cm.id" +
            " JOIN class_materials_type cmt on cmt.id = cm.class_materials_type_id"+
            " WHERE fd.folder_id = #{id}")
    List<FolderDetailResponse> getFolderDetail(int id);

    // delete
    @Select("DELETE FROM folder WHERE id = #{parent_id} RETURNING *")
    FolderResponse deleteByParentId(Integer parent_id);

    // get Course Folder ByTeacher
    @Select("SELECT  f.id, f.parent_id, f.folder_name  FROM folder f \n" +
            "JOIN folder_detail fd ON f.id = fd.folder_id\n" +
            "JOIN class_materials_detail cd ON fd.class_materials_detail_id = cd.id\n" +
            "JOIN class_materials cm ON cd.class_material_id = cm.id\n" +
            "JOIN class_materials_type mt ON cm.class_materials_type_id = mt.id\n" +
            "WHERE f.created_by = #{user_id} AND class_materials_type_id = 1 AND classroom_id = #{classroom_id}")
    List<FolderResponse> getCourseFolderByTeacher(Integer user_id, Integer classroom_id);

    // get ClassWork Folder ByClassId
    @Select("SELECT *\n" +
            "FROM folder f \n" +
            "JOIN folder_detail fd ON f.id = fd.folder_id\n" +
            "JOIN class_materials_detail cd ON fd.class_materials_detail_id = cd.id\n" +
            "JOIN class_materials cm ON cd.class_material_id = cm.id\n" +
            "JOIN class_materials_type ct ON cm.class_materials_type_id = ct.id\n" +
            "WHERE (class_materials_type_id = 2 OR class_materials_type_id = 3 OR class_materials_type_id = 4) " +
            "AND classroom_id = #{classRoomId} AND class_id = #{classId}")
    List<FolderByClassResponse> getClassWorkFolderByClassId(Integer classId, Integer classRoomId);
}
