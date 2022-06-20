package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateContentRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;
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
    public Boolean deleteById(Integer id) {
        return classMaterialRepository.deleteById(id);
    }

    @Override
    public List<ClassMaterialResponse> getClassMaterialByCreatedByAndMaterialType(Integer created_by, Integer class_materials_type_id) {
        return classMaterialRepository.getClassMaterialByCreatedByAndMaterialType(created_by, class_materials_type_id);
    }

    @Override
    public List<ClassMaterialResponse> getAllClassMaterialByTeacherUserId(Integer user_id) {
        return classMaterialRepository.getAllClassMaterialByTeacherUserId(user_id);
    }

    @Override
    public List<ClassMaterialResponse> getClassMaterialByMaterialTypeId(Integer class_materials_type_id) {
        return classMaterialRepository.getClassMaterialByMaterialTypeId(class_materials_type_id);
    }

    @Override
    public List<ClassMaterialByTeacherIdAndClassIdResponse> getByClassIdAndTeacherId(Integer teacher_id, Integer class_id) {
        return classMaterialRepository.getByClassIdAndTeacherId(teacher_id,class_id);
    }

    @Override
    public Boolean updateContent(ClassMaterialUpdateContentRequest classMaterialUpdateContentRequest) {
        return classMaterialRepository.updateContent(classMaterialUpdateContentRequest);
    }

    @Override
    public List<ClassMaterialByClassIdResponse> getByClassId(Integer class_id) {
        return classMaterialRepository.getByClassId(class_id);
    }

    @Override
    public List<ClassMaterialByClassIdAndClassroomIdResponse> getByClassIdAndClassroomId(Integer class_id, Integer classroom_id) {
        return classMaterialRepository.getByClassIdAndClassroomId(class_id,classroom_id);
    }

    @Override
    public List<ClassMaterialByClassIdAndMaterialTypeResponse> getByMaterialTypeAndClassId(Integer class_materials_type_id, Integer class_id) {
        return classMaterialRepository.getByMaterialTypeAndClassId(class_id,class_materials_type_id);
    }

    @Override
    public List<ClassMaterialByStudentIdResponse> getByStudentId(Integer user_id) {
        return classMaterialRepository.getByStudentId(user_id);
    }
}
