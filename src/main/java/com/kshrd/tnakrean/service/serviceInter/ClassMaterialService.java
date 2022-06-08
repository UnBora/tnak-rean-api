package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;

import java.util.List;

public interface ClassMaterialService {

    List<ClassMaterialResponse> classMaterial(int createdId);

    boolean insertClassMaterial(ClassMaterialRequest classMaterialRequest);

    boolean updateClassMaterial(ClassMaterialUpdateRequest classMaterialUpdateRequest);
}
