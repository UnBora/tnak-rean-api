package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.configuration.JsonTypeHandler;
import com.kshrd.tnakrean.model.ClassMaterialType;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassMaterialRepository {
    //get by id
    @Select("SELECT * FROM class_materials WHERE created_by = #{createdId}")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialTypeById"))
    List<ClassMaterialResponse> classMaterialRepo(int createdId);

//    @Select("SELECT * FROM class_materials_type WHERE id = #{id}")
//    ClassMaterialType getClassMaterialTypeById(int id);

    // insert
    @Insert("INSERT INTO class_materials(created_date,created_by,title,description,class_materials_type_id,content) " + "VALUES (#{classMaterial.created_date},#{classMaterial.created_by},#{classMaterial.title},#{classMaterial.description},#{classMaterial.class_materials_type_id},#{classMaterial.classMaterialContent,jdbcType=OTHER, typeHandler = com.kshrd.tnakrean.configuration.JsonTypeHandler})")

    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialType"))
    boolean insertClassMaterial(@Param("classMaterial") ClassMaterialRequest classMaterialRequest);

    @Select("SELECT * FROM class_materials ORDER BY id DESC LIMIT 1")
    @Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class)
    ClassMaterialResponse selectClass(Integer id);

    @Select("SELECT * FROM class_materials_type WHERE id = #{id}")
    ClassMaterialType getClassMaterialType(int id);


    // update
    @Update("UPDATE class_materials SET title = #{classMaterialUpdateRequest.title} ," + " description = #{classMaterialUpdateRequest.description} " + "WHERE id = #{classMaterialUpdateRequest.id} ")
    boolean updateClassMaterial(@Param("classMaterialUpdateRequest") ClassMaterialUpdateRequest classMaterialUpdateRequest);

    @Select("SELECT id, title ,description,created_by,class_materials_type_id, content,created_date FROM class_materials WHERE id=#{id}")
    @Results(@Result(property = "classMaterialContent", column = "content", typeHandler = JsonTypeHandler.class))
    @Result(property = "classMaterialType", column = "class_materials_type_id", one = @One(select = "getClassMaterialType"))
    ClassMaterialResponse selectResponseAfterUpdate(int id);
}
