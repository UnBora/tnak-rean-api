package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialsTypeRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialsTypeUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialsTypeResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassMaterialsTypeRepository {
    //
    @Select("SELECT * FROM class_materials_type")
    List<ClassMaterialsTypeResponse> getAllClassMaterialsType();

    //
    @Select("SELECT * FROM class_materials_type WHERE id = #{id}")
    ClassMaterialsTypeResponse getClassMaterialsTypeById(int id);

    //
    @Insert("INSERT INTO class_materials_type (type) VALUES (#{type})")
    boolean insertClassMaterialsType(ClassMaterialsTypeRequest classMaterialsTypeRequest);

    // update
    @Select("UPDATE class_materials_type SET type = #{type} WHERE id = #{id} Returning *")
    ClassMaterialsTypeResponse updateClassMaterialsType(ClassMaterialsTypeUpdateRequest classMaterialsTypeUpdateRequest);

    // DELETE
    @Select("DELETE FROM class_materials_type WHERE id = #{id} Returning *")
    ClassMaterialsTypeResponse deleteById(int id);

    @Select("SELECT EXISTS(SELECT type FROM class_materials_type WHERE type = #{type})")
    boolean ifTypeExistByType(String type);
    @Select("SELECT EXISTS(SELECT id FROM class_materials_type WHERE id = #{id})")
    boolean ifTypeIdExist(Integer id);

    @Select("SELECT EXISTS(SELECT class_materials_type_id FROM class_materials WHERE class_materials_type_id = #{id})")
    boolean findTypeIdInMaterial(Integer id);
}
