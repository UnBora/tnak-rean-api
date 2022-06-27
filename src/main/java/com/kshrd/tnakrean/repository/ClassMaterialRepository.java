package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.ClassMaterialType;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateContentRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateRequest;
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
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    List<ClassMaterialResponse> getAllClassMaterial();

    //get by id
    @Select("SELECT * FROM class_materials WHERE id = #{id}")
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
            "WHERE id = #{classMaterialUpdateRequest.id} returning *")
    ClassMaterialResponse updateClassMaterial(@Param("classMaterialUpdateRequest") ClassMaterialUpdateRequest classMaterialUpdateRequest);

    @Select("SELECT * FROM class_materials WHERE id=#{id}")
    @Results(@Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class))
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    ClassMaterialResponse selectResponseAfterUpdate(int id);

    // delete by id
    @Select("DELETE FROM class_materials WHERE id = #{id} returning *")
    ClassMaterialResponse deleteById(@Param("id") Integer id);

    // Get All Class Material By UserId and ClassMaterialTypeId
    @Select("SELECT * FROM class_materials WHERE created_by = #{created_by} AND class_materials_type_id = #{class_materials_type_id}")
    @Results(@Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class))
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    List<ClassMaterialResponse> getClassMaterialByCreatedByAndMaterialType(Integer created_by, Integer class_materials_type_id);

    // Get All Class Material By User Id
    @Select("SELECT * FROM class_materials WHERE created_by = #{user_id}")
    @Results(@Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class))
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    List<ClassMaterialResponse> getAllClassMaterialByTeacherUserId(@Param("user_id") Integer user_id);

    // Get All Class Material class_materials_type_id
    @Select("SELECT * FROM class_materials WHERE class_materials_type_id = #{class_materials_type_id}")
    @Results(@Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class))
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    List<ClassMaterialResponse> getClassMaterialByMaterialTypeId(Integer class_materials_type_id);

    //Get All Class Material By teacher id and Class Id
    @Select("SELECT d.classroom_id, d.class_id, t.type, m.* FROM class_materials m \n" +
            "JOIN class_materials_detail d ON m.id = d.class_material_id\n" +
            "JOIN class_materials_type t ON t.id = m.class_materials_type_id\n" +
            "WHERE class_id = #{class_id} AND created_by = #{teacher_id}")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "class_material_id", column = "id")
    @Result(property = "class_materials_type", column = "type")
    @Result(property = "teacher_id", column = "created_by")
    List<ClassMaterialByTeacherIdAndClassIdResponse> getByClassIdAndTeacherId(Integer teacher_id, Integer class_id);
    // update content
    @Update("UPDATE class_materials SET content = #{classMaterialContent, jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler} WHERE id = #{id}")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    Boolean updateContent(ClassMaterialUpdateContentRequest classMaterialUpdateContentRequest);

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
    @Select("SELECT m.* , t.type, d.class_id, d.classroom_id FROM class_materials_type t \n" +
            "JOIN class_materials m ON m.class_materials_type_id = t.id \n" +
            "JOIN class_materials_detail d ON d.class_material_id = m.id \n" +
            "WHERE class_id = #{class_id} AND classroom_id = #{classroom_id}")
    @Result(property = "class_material_id", column = "id")
    @Result(property = "class_materials_type", column = "type")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
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

    // get by student id
    @Select("SELECT m.*, s.user_id, s.class_id, s.classroom_id\n" +
            "FROM class_materials m \n" +
            "JOIN class_materials_detail d ON m.id = d.class_material_id\n" +
            "JOIN classroom_detail cd ON d.classroom_id = cd.classroom_id\n" +
            "JOIN student s ON cd.class_id = s.class_id \n" +
            "WHERE user_id = #{user_id}")
    @Result(property = "student_id", column = "user_id")
    @Result(property = "class_material_id", column = "id")
    @Result(property = "classMaterialType", column = "class_materials_type_id" , one = @One(select = "getClassMaterialTypeById"))
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    List<ClassMaterialByStudentIdResponse> getByStudentId(Integer user_id);

    // get by user_id class_id classroom_id
    @Select("SELECT m.*, s.user_id, s.class_id, s.classroom_id\n" +
            "FROM class_materials m \n" +
            "JOIN class_materials_detail d ON m.id = d.class_material_id\n" +
            "JOIN classroom_detail cd ON d.classroom_id = cd.classroom_id\n" +
            "JOIN student s ON cd.class_id = s.class_id \n" +
            "WHERE d.class_id = #{class_id} AND d.classroom_id = #{classroom_id} AND user_id = #{user_id}")
    @Result(property = "class_material_id", column = "id")
    @Result(property = "student_id", column = "user_id")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    List<ClassMaterialByStudentIdClassIdAndClassroomIdResponse> getByUserClassClassroom(Integer user_id, Integer class_id, Integer classroom_id);
}
