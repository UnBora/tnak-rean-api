package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateContentRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;

import java.util.List;

public interface ClassMaterialService {

    ClassMaterialResponse getClassMaterial(int createdId);

    boolean insertClassMaterial(ClassMaterialRequest classMaterialRequest);

    ClassMaterialResponse updateClassMaterial(ClassMaterialUpdateRequest classMaterialUpdateRequest);

    List<ClassMaterialResponse> getAllClassMaterial();

    ClassMaterialResponse deleteById(Integer id);

    List<ClassMaterialResponse> getClassMaterialByCreatedByAndMaterialType(Integer created_by, Integer class_materials_type_id);

    List<ClassMaterialResponse> getAllClassMaterialByTeacherUserId(Integer user_id);

    List<ClassMaterialResponse> getClassMaterialByMaterialTypeId(Integer class_materials_type_id);

    List<ClassMaterialByTeacherIdAndClassIdResponse> getByClassIdAndTeacherId(Integer teacher_id, Integer class_id);

    Boolean updateContent(ClassMaterialUpdateContentRequest classMaterialUpdateContentRequest);

    List<ClassMaterialByClassIdResponse> getByClassId(Integer class_id);

    List<ClassMaterialByClassIdAndClassroomIdResponse> getByClassIdAndClassroomId(Integer class_id, Integer classroom_id);

    List<ClassMaterialByClassIdAndMaterialTypeResponse> getByMaterialTypeAndClassId(Integer class_materials_type_id, Integer class_id);

    List<ClassMaterialByStudentIdResponse> getByStudentId(Integer user_id);

    List<ClassMaterialByStudentIdClassIdAndClassroomIdResponse> getByUserClassClassroom(Integer user_id, Integer class_id, Integer classroom_id);
}
