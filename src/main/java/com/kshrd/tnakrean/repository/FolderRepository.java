package com.kshrd.tnakrean.repository;


import com.kshrd.tnakrean.model.classmaterials.request.FolderAssignClassRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderClassWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderCourseRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import com.kshrd.tnakrean.model.classmaterials.response.FolderByClassResponse;
import com.kshrd.tnakrean.model.classmaterials.response.FolderByTeacherResponse;
import com.kshrd.tnakrean.model.classmaterials.response.FolderDetailResponse;
import com.kshrd.tnakrean.model.classmaterials.response.FolderResponse;
import com.kshrd.tnakrean.repository.provider.FolderProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FolderRepository {

    @Select("SELECT EXISTS(SELECT class_id FROM class_material_folder WHERE class_id = #{class_id})")
    Boolean findClassIdInCMF(Integer class_id);
    @Select("SELECT EXISTS(SELECT folder_id FROM class_material_folder WHERE folder_id = #{folder_id})")
    Boolean findFolderIdInCMF(Integer folder_id);
    @Select("SELECT EXISTS(SELECT id FROM classroom WHERE id = #{classroom_id})")
    Boolean findClassroomId(Integer classroom_id);
    @Select("SELECT EXISTS(SELECT id FROM class WHERE id = #{class_id})")
    Boolean findClassId(Integer class_id);
    @Select("SELECT EXISTS(SELECT id FROM folder WHERE id = #{parent_id})")
    boolean findParentId(Integer parent_id);
    @Select("SELECT EXISTS(SELECT id FROM class_materials_type WHERE id = #{material_type_id})")
    boolean findMaterialTypeId(Integer material_type_id);
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

    // get Course Folder ByClassId
    @Select("SELECT created_by, folder_id, type ,folder_name, f.parent_id, class_id FROM folder f\n" +
            "JOIN class_materials_type cm ON f.material_type_id = cm.id\n" +
            "JOIN class_material_folder cmf ON f.id = cmf.folder_id\n" +
            "WHERE material_type_id = 1 AND classroom_id = #{classRoomId} AND class_id = #{classId}")
    List<FolderByClassResponse> getCourseFolderByClassId(int classId, int classRoomId);

    // get ClassWork Folder ByClassId
    @Select("SELECT created_by, folder_id, type ,folder_name, f.parent_id, class_id FROM folder f\n" +
            "JOIN class_materials_type cm ON f.material_type_id = cm.id\n" +
            "JOIN class_material_folder cmf ON f.id = cmf.folder_id \n" +
            "WHERE (material_type_id = 3 OR material_type_id = 4 OR material_type_id = 5 OR material_type_id = 2) AND classroom_id = #{classRoomId} AND class_id = #{classId}")
    List<FolderByClassResponse> getClassWorkFolderByClassId(Integer classId, Integer classRoomId);

    // get Classwork Folder By teacherUserId
    @Select("SELECT f.id, created_by, folder_name,f.parent_id FROM folder f \n" +
            "WHERE created_by = #{user_id} AND (material_type_id = 4 OR material_type_id = 3 OR material_type_id = 5 OR material_type_id = 2)")
    @Result(property = "folder_id", column = "id")
    List<FolderByTeacherResponse> getClassworkFolderByteacherUserId(int user_id);

    // get Course Folder ByTeacher
    @Select("SELECT f.id, created_by, folder_name,f.parent_id FROM folder f \n" +
            "WHERE created_by = #{user_id} AND material_type_id = 1 ")
    @Result(property = "folder_id", column = "id")
    List<FolderByTeacherResponse> getCourseFolderByTeacher(Integer user_id);

    // create ClassWorkFolder
    @Insert("INSERT INTO folder (folder_name, parent_id,created_by,material_type_id) " +
            "VALUES (#{folder.folder_name},#{folder.parent_id},#{userId},#{folder.material_type_id})")
    boolean createClassWorkFolder(@Param("folder") FolderClassWorkRequest folderClassWorkRequest, Integer userId);
    // create CourseWorkFolder
    @Insert("INSERT INTO folder (folder_name, parent_id,created_by,material_type_id) " +
            "VALUES (#{folder.folder_name},#{folder.parent_id},#{userId},1)")
    boolean createCourseWorkFolder(@Param("folder") FolderCourseRequest folderCourseRequest, Integer userId);

    // Assign Class
    @Insert("INSERT INTO class_material_folder (folder_id, classroom_id,class_id) " +
            "VALUES (#{folder_id} ,#{classroom_id} ,#{class_id})")
    boolean folderAssignClass(FolderAssignClassRequest folderAssignClassRequest);

    // create CourseFolderInClass
    @Select("INSERT INTO folder (folder_name,parent_id,created_by,material_type_id)\n" +
            "VALUES (#{folder_name},#{parent_id},#{userId},1) RETURNING id")
    Integer createCourseFolderForClass(String folder_name, int parent_id, Integer userId);

    @Select("INSERT INTO class_material_folder (folder_id, classroom_id,class_id) " +
            "VALUES (#{folderId} ,#{classroom_id} ,#{class_id})")
    Integer createCourseFolderInClass(Integer folderId, int classroom_id, int class_id);

    // create ClassWorkFolderInClass
    @Select("INSERT INTO folder (folder_name,parent_id,created_by,material_type_id)\n" +
            "VALUES (#{folder_name},#{parent_id},#{userId},2) RETURNING id")
    Integer createClassWorkFolderForClass(String folder_name, int parent_id, Integer userId);
    @Select("INSERT INTO class_material_folder (folder_id, classroom_id,class_id) " +
            "VALUES (#{folderId} ,#{classroom_id} ,#{class_id})")
    Integer createClassWorkFolderInClass(Integer folderId, int classroom_id, int class_id);
}
