package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.ClassMaterialType;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateContentRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialByClassIdResponse;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialByTeacherIdAndClassIdResponse;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
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
    @Select("SELECT * FROM class_materials WHERE created_by = #{id}")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    List<ClassMaterialResponse> getClassMaterials(int id);

    // insert
    @Insert("INSERT INTO class_materials(created_date,created_by,title,description,class_materials_type_id,content) " +
            "VALUES (#{classMaterial.created_date},#{classMaterial.created_by},#{classMaterial.title},#{classMaterial.description},#{classMaterial.class_materials_type_id},#{classMaterial.classMaterialContent,jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler})")

    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    boolean insertClassMaterial(@Param("classMaterial") ClassMaterialRequest classMaterialRequest);

//    @Select("SELECT * FROM class_materials ORDER BY id DESC LIMIT 1")
//    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
//    ClassMaterialResponse selectClass(Integer id);

    // update
    @Update("UPDATE class_materials SET title = #{classMaterialUpdateRequest.title} ," + " description = #{classMaterialUpdateRequest.description} " +
            "WHERE id = #{classMaterialUpdateRequest.id} ")
    boolean updateClassMaterial(@Param("classMaterialUpdateRequest") ClassMaterialUpdateRequest classMaterialUpdateRequest);

    @Select("SELECT * FROM class_materials WHERE id=#{id}")
    @Results(@Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class))
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    ClassMaterialResponse selectResponseAfterUpdate(int id);

    // delete by id
    @Delete("DELETE FROM class_materials WHERE id = #{id}")
    Boolean deleteById(@Param("id") Integer id);

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
    @Select("SELECT * FROM class_materials WHERE created_by = #{user_id}")
    @Results(@Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class))
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    List<ClassMaterialResponse> getClassMaterialByMaterialTypeId(Integer class_materials_type_id);

    //Get All Class Material By UserId and Class Id
    @Select("SELECT c.class_id, m.* FROM class_materials m JOIN classroom c ON m.created_by = c.created_by " +
            "WHERE m.created_by = #{created_by} AND c.class_id = #{class_id}")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "class_material_id", column = "id")
    List<ClassMaterialByTeacherIdAndClassIdResponse> getByClassIdAndTeacherId(Integer created_by, Integer class_id);
    //
    @Update("UPDATE class_materials SET content = #{classMaterialContent, jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler} WHERE id = #{id}")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    Boolean updateContent(ClassMaterialUpdateContentRequest classMaterialUpdateContentRequest);

    // get By ClassId
    @Select("SELECT c.class_id, m.*, t.type\n" +
            "FROM class_materials m \n" +
            "JOIN classroom c ON m.created_by = c.created_by \n" +
            "JOIN class_materials_type t ON t.id = m.class_materials_type_id\n" +
            "WHERE c.class_id = #{class_id}")
    @Result(property = "class_material_id", column = "id")
    @Result(property = "class_materials_type", column = "type")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    List<ClassMaterialByClassIdResponse> getByClassId(@Param("class_id") Integer class_id);
}
