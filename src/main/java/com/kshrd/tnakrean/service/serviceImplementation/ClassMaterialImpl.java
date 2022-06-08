package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
import com.kshrd.tnakrean.repository.ClassMaterialRepository;
import com.kshrd.tnakrean.service.serviceInter.ClassMaterialService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassMaterialImpl implements ClassMaterialService {
    final ClassMaterialRepository classMaterialRepository;

    public ClassMaterialImpl(ClassMaterialRepository classMaterialRepository) {
        this.classMaterialRepository = classMaterialRepository;
    }

    @Override
    public List<ClassMaterialResponse> classMaterial(int createdId) {
        return classMaterialRepository.classMaterialRepo(createdId);
    }

    @Override
    public boolean insertClassMaterial(ClassMaterialRequest classMaterialRequest) {
        ClassMaterialResponse response = classMaterialRepository.selectClass(classMaterialRequest.getId());
        return classMaterialRepository.insertClassMaterial(classMaterialRequest);
    }

    @Override
    public boolean updateClassMaterial(ClassMaterialUpdateRequest classMaterialUpdateRequest) {
        ClassMaterialResponse response = classMaterialRepository.selectResponseAfterUpdate(classMaterialUpdateRequest.getId());
        return classMaterialRepository.updateClassMaterial(classMaterialUpdateRequest);
    }
}
