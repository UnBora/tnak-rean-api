package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateContentRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateTitleDesRequest;
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
    public ClassMaterialResponse getClassMaterial(int id) {
        return classMaterialRepository.getClassMaterials(id);
    }

    @Override
    public boolean insertClassMaterial(ClassMaterialRequest classMaterialRequest) {
        return classMaterialRepository.insertClassMaterial(classMaterialRequest);
    }

    @Override
    public ClassMaterialUpdateTitleDesRequest updateClassMaterial(ClassMaterialUpdateTitleDesRequest classMaterialUpdateTitleDesRequest) {
        return classMaterialRepository.updateClassMaterial(classMaterialUpdateTitleDesRequest);
    }

    @Override
    public List<ClassMaterialResponse> getAllClassMaterial() {
        return classMaterialRepository.getAllClassMaterial();
    }

    @Override
    public ClassMaterialResponse deleteById(Integer id) {
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
    public ClassMaterialUpdateContentRequest updateContent(ClassMaterialUpdateContentRequest classMaterialUpdateContentRequest) {
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

    @Override
    public List<ClassMaterialByStudentIdClassIdAndClassroomIdResponse> getByUserClassClassroom(Integer user_id, Integer class_id, Integer classroom_id) {
        return classMaterialRepository.getByUserClassClassroom(user_id,class_id,classroom_id);
    }
}
