package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialsTypeRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialsTypeUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialsTypeResponse;

import java.util.List;

public interface ClassMaterialsTypeService {
    List<ClassMaterialsTypeResponse> getAllClassMaterialsType();

    ClassMaterialsTypeResponse getClassMaterialsTypeById(int id);

    boolean insertClassMaterialsType(ClassMaterialsTypeRequest classMaterialsTypeRequest);

    ClassMaterialsTypeResponse updateClassMaterialsType(ClassMaterialsTypeUpdateRequest classMaterialsTypeUpdateRequest);

    ClassMaterialsTypeResponse deleteById(int id);
}
