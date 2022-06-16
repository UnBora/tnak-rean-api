package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
import com.kshrd.tnakrean.repository.ClassMaterialRepository;
import com.kshrd.tnakrean.service.serviceInter.ClassMaterialService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassMaterialImp implements ClassMaterialService {
    final ClassMaterialRepository classMaterialRepository;

    public ClassMaterialImp(ClassMaterialRepository classMaterialRepository) {
        this.classMaterialRepository = classMaterialRepository;
    }

    @Override
    public List<ClassMaterialResponse> getClassMaterial(int createdId) {
        return classMaterialRepository.getClassMaterials(createdId);
    }

    @Override
    public boolean insertClassMaterial(ClassMaterialRequest classMaterialRequest) {
        return classMaterialRepository.insertClassMaterial(classMaterialRequest);
    }

    @Override
    public boolean updateClassMaterial(ClassMaterialUpdateRequest classMaterialUpdateRequest) {
        return classMaterialRepository.updateClassMaterial(classMaterialUpdateRequest);
    }

    @Override
    public List<ClassMaterialResponse> getAllClassMaterial() {
        return classMaterialRepository.getAllClassMaterial();
    }

    @Override
    public List<ClassMaterialResponse> getAllClassMaterialByCreatedBy(Integer created_id) {
        return classMaterialRepository.getAllClassMaterialByCreatedBy(created_id);
    }

    @Override
    public Boolean deleteById(Integer id) {
        return classMaterialRepository.deleteById(id);
    }

    @Override
    public List<ClassMaterialResponse> getClassMaterialByCreatedByAndMaterialType(Integer created_by, Integer class_materials_type_id) {
        return classMaterialRepository.getClassMaterialByCreatedByAndMaterialType(created_by, class_materials_type_id);
    }
}
