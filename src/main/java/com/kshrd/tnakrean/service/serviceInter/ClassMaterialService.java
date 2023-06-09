package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateContentRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateTitleDesRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;

import java.util.List;

public interface ClassMaterialService {

    ClassMaterialResponse getClassMaterial(int createdId);

    boolean insertCourse(ClassMaterialRequest classMaterialRequest, Integer user_id);

    ClassMaterialUpdateTitleDesRequest updateClassMaterial(ClassMaterialUpdateTitleDesRequest classMaterialUpdateTitleDesRequest);

    List<ClassMaterialResponse> getAllClassMaterial();

    ClassMaterialResponse deleteById(Integer id);

    List<ClassMaterialResponse> getClassMaterialByCreatedByAndMaterialType(Integer created_by, Integer class_materials_type_id);

    List<ClassMaterialByTeacherResponse> getAllClassMaterialByTeacherUserId(Integer user_id);

    List<ClassMaterialResponse> getClassMaterialByMaterialTypeId(Integer class_materials_type_id);

    List<ClassMaterialByTeacherIdAndClassIdResponse> getByClassIdAndTeacherId(Integer teacher_id, Integer class_id);

    ClassMaterialUpdateContentRequest updateContent(ClassMaterialUpdateContentRequest classMaterialUpdateContentRequest);

    List<ClassMaterialByClassIdResponse> getByClassId(Integer class_id);

    List<ClassMaterialByClassIdAndClassroomIdResponse> getByClassIdAndClassroomId(Integer class_id, Integer classroom_id);

    List<ClassMaterialByClassIdAndMaterialTypeResponse> getByMaterialTypeAndClassId(Integer class_materials_type_id, Integer class_id);

    List<ClassMaterialByStudentIdResponse> getByStudentId(Integer user_id);

    List<ClassMaterialByStudentIdClassIdAndClassroomIdResponse> getByUserClassClassroom(Integer user_id, Integer class_id, Integer classroom_id);

    boolean setMaterialToFolder(int folder_id, int material_id);

    List<ClassMaterialByTeacherResponse> getCourseMaterialByTFolderId(int folder_id,int user_id);

    List<ClassMaterialByClassIdAndClassroomIdResponse> getCourseMaterialByFolderIdInClass(Integer folder_id, Integer class_id,Integer classroom_id);

    List<CourseByStudentIdResponse> getCourseByStudentId(Integer user_id);

    Boolean deleteByCreatedByAndMaterialId(Integer material_id, Integer teacher_id);
}
