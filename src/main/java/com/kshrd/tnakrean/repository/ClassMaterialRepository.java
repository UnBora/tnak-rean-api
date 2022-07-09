package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.ClassMaterialType;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateContentRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateTitleDesRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassMaterialRepository {
    @Select("SELECT * FROM class_materials_type WHERE id = #{id}")
    ClassMaterialType getClassMaterialTypeById(int id);
    // get all
    @Select("SELECT * FROM class_materials")
    @Result(property = "class_material_id",column = "id")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    List<ClassMaterialResponse> getAllClassMaterial();

    //get by id
    @Select("SELECT * FROM class_materials WHERE id = #{id}")
    @Result(property = "class_material_id",column = "id")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    ClassMaterialResponse getClassMaterials(int id);

    // insert
    @Insert("INSERT INTO class_materials(created_date,created_by,title,description,class_materials_type_id,content) " +
            "VALUES (#{classMaterial.created_date},#{classMaterial.created_by},#{classMaterial.title},#{classMaterial.description},#{classMaterial.class_materials_type_id},#{classMaterial.classMaterialContent,jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler})")

    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    boolean insertClassMaterial(@Param("classMaterial") ClassMaterialRequest classMaterialRequest);

//    @Select("SELECT * FROM class_materials ORDER BY id DESC LIMIT 1")
//    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
//    ClassMaterialResponse selectClass(Integer id);

    // update title and description
    @Select("UPDATE class_materials SET title = #{classMaterialUpdateRequest.title} ," + " description = #{classMaterialUpdateRequest.description} " +
            "WHERE id = #{classMaterialUpdateRequest.class_material_id} returning *")
    @Result(property = "class_material_id",column = "id")
    ClassMaterialUpdateTitleDesRequest updateClassMaterial(@Param("classMaterialUpdateRequest") ClassMaterialUpdateTitleDesRequest classMaterialUpdateTitleDesRequest);

    @Select("SELECT * FROM class_materials WHERE id=#{id}")

    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "class_material_id",column = "id")
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    ClassMaterialResponse selectResponseAfterUpdate(int id);

    // delete by id
    @Select("DELETE FROM class_materials WHERE id = #{id} returning *")
    ClassMaterialResponse deleteById(@Param("id") Integer id);

    // Get All Class Material By teacherUserId and ClassMaterialTypeId
    @Select("SELECT * FROM class_materials WHERE created_by = #{created_by} AND class_materials_type_id = #{class_materials_type_id}")
    @Result(property = "class_material_id",column = "id")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    List<ClassMaterialResponse> getClassMaterialByCreatedByAndMaterialType(Integer created_by, Integer class_materials_type_id);

    // Get All Class Material By teacher User Id
    @Select("SELECT class_id, material_id, title,description,f.created_by, " +
            " (SELECT count(*) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = mf.material_id) " +
            "FROM folder f\n" +
            "JOIN class_materials_type cm ON f.material_type_id = cm.id\n" +
            "JOIN class_material_folder cmf ON f.id = cmf.folder_id \n" +
            "JOIN material_folder mf ON f.id = mf.folder_id\n" +
            "JOIN class_materials clm ON mf.material_id = clm.id " +
            "WHERE f.created_by = #{user_id} AND class_materials_type_id = 1")
    @Result(property = "total_comment", column = "count")
    List<ClassMaterialByTeacherResponse> getAllClassMaterialByTeacherUserId(@Param("user_id") Integer user_id);

    // Get All Class Material class_materials_type_id
    @Select("SELECT * FROM class_materials WHERE class_materials_type_id = #{class_materials_type_id}")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    @Result(property = "class_material_id",column = "id")
    List<ClassMaterialResponse> getClassMaterialByMaterialTypeId(Integer class_materials_type_id);

    //Get All Class Material Course By teacher id and Class Id
    @Select("SELECT class_id, material_id, title,description,clm.created_by ,\n" +
            "(SELECT count(*) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = clm.id)\n" +
            "FROM folder f\n" +
            "JOIN class_materials_type cm ON f.material_type_id = cm.id\n" +
            "JOIN class_material_folder cmf ON f.id = cmf.folder_id \n" +
            "JOIN material_folder mf ON f.id = mf.folder_id\n" +
            "JOIN class_materials clm ON mf.material_id = clm.id\n" +
            "WHERE class_materials_type_id = 1 AND clm.created_by = #{teacher_id} AND class_id = #{class_id}")
    @Result(property = "total_comment", column = "count")
    List<ClassMaterialByTeacherIdAndClassIdResponse> getByClassIdAndTeacherId(Integer teacher_id, Integer class_id);
    // update content
    @Select("UPDATE class_materials SET content = #{classMaterialContent, jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler} WHERE id = #{class_material_id} returning *")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "class_material_id", column = "id")
    ClassMaterialUpdateContentRequest updateContent(ClassMaterialUpdateContentRequest classMaterialUpdateContentRequest);

    // get By ClassId
    @Select("SELECT d.classroom_id, d.class_id, t.type, m.* FROM class_materials m \n" +
            "JOIN class_materials_detail d ON m.id = d.class_material_id\n" +
            "JOIN class_materials_type t ON t.id = m.class_materials_type_id\n" +
            "WHERE class_id = #{class_id}")
    @Result(property = "class_material_id", column = "id")
    @Result(property = "class_materials_type", column = "type")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    List<ClassMaterialByClassIdResponse> getByClassId(@Param("class_id") Integer class_id);

    // get By ClassId And ClassroomId
    @Select("SELECT class_id, material_id, title,description,clm.created_by , (SELECT count(*) FROM comment c \n" +
            "JOIN class_materials_detail s ON c.class_materials_detail_id = s.id \n" +
            "WHERE class_material_id = mf.material_id)\n" +
            "FROM folder f\n" +
            "JOIN class_materials_type cm ON f.material_type_id = cm.id\n" +
            "JOIN class_material_folder cmf ON f.id = cmf.folder_id \n" +
            "JOIN material_folder mf ON f.id = mf.folder_id\n" +
            "JOIN class_materials clm ON mf.material_id = clm.id " +
            "WHERE class_materials_type_id = 1 AND classroom_id = #{classroom_id} AND class_id = #{class_id} ")
    @Result(property = "total_comment", column = "count")
    List<ClassMaterialByClassIdAndClassroomIdResponse> getByClassIdAndClassroomId(Integer class_id, Integer classroom_id);

    // get By MaterialType And ClassId
    @Select("SELECT d.classroom_id, d.class_id, t.type, m.* FROM class_materials m \n" +
            "JOIN class_materials_detail d ON m.id = d.class_material_id\n" +
            "JOIN class_materials_type t ON t.id = m.class_materials_type_id\n" +
            "WHERE class_materials_type_id = #{class_materials_type_id} AND class_id = #{class_id}")
    @Result(property = "class_material_id", column = "id")
    @Result(property = "class_materials_type", column = "type")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    List<ClassMaterialByClassIdAndMaterialTypeResponse> getByMaterialTypeAndClassId(Integer class_id, Integer class_materials_type_id);

    // get by (student)user id
    @Select("SELECT m.*, s.user_id, s.class_id, s.classroom_id\n" +
            "FROM class_materials m \n" +
            "JOIN class_materials_detail d ON m.id = d.class_material_id\n" +
            "JOIN classroom_detail cd ON d.classroom_id = cd.classroom_id\n" +
            "JOIN student s ON cd.class_id = s.class_id \n" +
            "WHERE user_id = #{user_id}")
    @Result(property = "student_user_id", column = "user_id")
    @Result(property = "class_material_id", column = "id")
    @Result(property = "classMaterialType", column = "class_materials_type_id" , one = @One(select = "getClassMaterialTypeById"))
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    List<ClassMaterialByStudentIdResponse> getByStudentId(Integer user_id);

    // get by (student)user_id class_id classroom_id
    @Select("SELECT m.*, s.user_id, s.class_id, s.classroom_id\n" +
            "FROM class_materials m \n" +
            "JOIN class_materials_detail d ON m.id = d.class_material_id\n" +
            "JOIN classroom_detail cd ON d.classroom_id = cd.classroom_id\n" +
            "JOIN student s ON cd.class_id = s.class_id \n" +
            "WHERE d.class_id = #{class_id} AND d.classroom_id = #{classroom_id} AND user_id = #{user_id}")
    @Result(property = "class_material_id", column = "id")
    @Result(property = "student_user_id", column = "user_id")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    List<ClassMaterialByStudentIdClassIdAndClassroomIdResponse> getByUserClassClassroom(Integer user_id, Integer class_id, Integer classroom_id);

    @Select("SELECT EXISTS(SELECT id FROM users WHERE id = #{created_by})")
    Boolean checkCreatedBy(Integer created_by);
    @Select("SELECT EXISTS(SELECT id FROM class_materials_type WHERE id = #{class_materials_type_id})")
    Boolean checkMaterialsTypeId(Integer class_materials_type_id);

    @Select("SELECT EXISTS(SELECT * FROM class_materials WHERE id = #{id})")
    Boolean checkMaterialsId(Integer id);

    @Select("SELECT EXISTS(SELECT id FROM class_materials WHERE id = #{id})")
    boolean findMaterialId(Integer id);

    @Select("SELECT EXISTS(SELECT class_material_id FROM class_materials_detail WHERE class_material_id = #{id})")
    boolean findMaterialIdInMaterialsDetail(Integer id);
}
