package com.kshrd.tnakrean.repository;

import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialsTypeRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialsTypeUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialsTypeResponse;
import org.apache.ibatis.annotations.Delete;
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

    //
    @Insert("UPDATE class_materials_type SET type = #{type} WHERE id = #{id}")
    boolean updateClassMaterialsType(ClassMaterialsTypeUpdateRequest classMaterialsTypeUpdateRequest);

    //
    @Delete("DELETE FROM class_materials_type WHERE id = #{id}")
    boolean deleteById(int id);
}
