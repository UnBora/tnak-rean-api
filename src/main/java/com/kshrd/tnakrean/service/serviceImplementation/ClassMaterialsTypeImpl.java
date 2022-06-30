package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialsTypeRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialsTypeUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialsTypeResponse;
import com.kshrd.tnakrean.repository.ClassMaterialsTypeRepository;
import com.kshrd.tnakrean.service.serviceInter.ClassMaterialsTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassMaterialsTypeImpl implements ClassMaterialsTypeService {
    final ClassMaterialsTypeRepository classMaterialsTypeRepository;

    public ClassMaterialsTypeImpl(ClassMaterialsTypeRepository classMaterialsTypeRepository) {
        this.classMaterialsTypeRepository = classMaterialsTypeRepository;
    }

    @Override
    public List<ClassMaterialsTypeResponse> getAllClassMaterialsType() {
        return classMaterialsTypeRepository.getAllClassMaterialsType();
    }

    @Override
    public ClassMaterialsTypeResponse getClassMaterialsTypeById(int id) {
        return classMaterialsTypeRepository.getClassMaterialsTypeById(id);
    }

    @Override
    public boolean insertClassMaterialsType(ClassMaterialsTypeRequest classMaterialsTypeRequest) {
        return classMaterialsTypeRepository.insertClassMaterialsType(classMaterialsTypeRequest);
    }

    @Override
    public ClassMaterialsTypeResponse updateClassMaterialsType(ClassMaterialsTypeUpdateRequest classMaterialsTypeUpdateRequest) {
        return classMaterialsTypeRepository.updateClassMaterialsType(classMaterialsTypeUpdateRequest);
    }

    @Override
    public ClassMaterialsTypeResponse deleteById(int id) {
        return classMaterialsTypeRepository.deleteById(id);
    }

}