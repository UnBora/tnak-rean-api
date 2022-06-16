package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;

import java.util.List;

public interface ClassMaterialService {

    List<ClassMaterialResponse> getClassMaterial(int createdId);

    boolean insertClassMaterial(ClassMaterialRequest classMaterialRequest);

    boolean updateClassMaterial(ClassMaterialUpdateRequest classMaterialUpdateRequest);

    List<ClassMaterialResponse> getAllClassMaterial();

    List<ClassMaterialResponse> getAllClassMaterialByCreatedBy(Integer created_id);

    Boolean deleteById(Integer id);

    List<ClassMaterialResponse> getClassMaterialByCreatedByAndMaterialType(Integer created_by, Integer class_materials_type_id);
}
