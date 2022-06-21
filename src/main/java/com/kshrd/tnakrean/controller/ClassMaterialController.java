package com.kshrd.tnakrean.controller;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateContentRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;
import com.kshrd.tnakrean.repository.ClassMaterialRepository;
import com.kshrd.tnakrean.service.serviceImplementation.ClassMaterialImp;

import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Builder
@RestController
@RequestMapping("/api/v1/classMaterial")
public class ClassMaterialController {

    final
    ClassMaterialRepository classMaterialRepository;
    final ClassMaterialImp classMaterialServiceImp;

    public ClassMaterialController(ClassMaterialImp classMaterialService, ClassMaterialRepository classMaterialRepository) {
        this.classMaterialServiceImp = classMaterialService;
        this.classMaterialRepository = classMaterialRepository;
    }

    @GetMapping("/get-by-id/{id}")
    ApiResponse<List<ClassMaterialResponse>> getClassMaterial(@RequestParam int id) {
        try {
            List<ClassMaterialResponse> classMaterialResponses = classMaterialServiceImp.getClassMaterial(id);
            return ApiResponse.<List<ClassMaterialResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                    .setData(classMaterialResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("insert")
    ApiResponse<ClassMaterialRequest> insertClassMaterial(
            @RequestBody ClassMaterialRequest classMaterialRequest
    ) {
        classMaterialServiceImp.insertClassMaterial(classMaterialRequest);
        return ApiResponse.<ClassMaterialRequest>ok(ClassMaterialRequest.class.getSimpleName())
                .setData(classMaterialRequest);
    }

    @PutMapping("update-title-and-describtion")
    ApiResponse<ClassMaterialResponse> updateClassMaterial(
            @RequestBody ClassMaterialUpdateRequest classMaterialUpdateRequest
    ) {
        ClassMaterialResponse response = classMaterialRepository.selectResponseAfterUpdate(classMaterialUpdateRequest.getId());
        classMaterialServiceImp.updateClassMaterial(classMaterialUpdateRequest);
        return ApiResponse.<ClassMaterialResponse>ok(ClassMaterialResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(response);
    }

    @PutMapping("update-content")
    ApiResponse<ClassMaterialResponse> updateContent(
            @RequestBody ClassMaterialUpdateContentRequest classMaterialUpdateContentRequest
    ) {
        ClassMaterialResponse response = classMaterialRepository.selectResponseAfterUpdate(classMaterialUpdateContentRequest.getId());
        classMaterialServiceImp.updateContent(classMaterialUpdateContentRequest);
        return ApiResponse.<ClassMaterialResponse>ok(ClassMaterialResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(response);
    }

    @DeleteMapping("/delete-by-id/{id}")
    ApiResponse<Boolean> deleteById(Integer id) {
        classMaterialServiceImp.deleteById(id);
        return ApiResponse.<Boolean>ok("Class Materials")
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                .setData(true);
    }
    @DeleteMapping("/delete-by-TeacherId/{id}")
    ApiResponse<Boolean> deleteByTeacherId(Integer id) {
        classMaterialServiceImp.deleteById(id);
        return ApiResponse.<Boolean>ok("Class Materials")
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                .setData(true);
    }

    @GetMapping("/get-all")
    ApiResponse<List<ClassMaterialResponse>> getAllClassMaterial() {
        try {
            List<ClassMaterialResponse> classMaterialResponses = classMaterialServiceImp.getAllClassMaterial();
            if (classMaterialResponses.isEmpty()) {
                return ApiResponse.<List<ClassMaterialResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                        .setData(classMaterialResponses);
            }
            return ApiResponse.<List<ClassMaterialResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(classMaterialResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-teacheId/{id}")
    ApiResponse<List<ClassMaterialResponse>> getAllClassMaterialByTeacherUserId(
            @RequestParam Integer teacher_id) {
        try {
            List<ClassMaterialResponse> classMaterialResponses = classMaterialServiceImp.getAllClassMaterialByTeacherUserId(teacher_id);
            if (classMaterialResponses.isEmpty()) {
                return ApiResponse.<List<ClassMaterialResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classMaterialResponses);
            }
            return ApiResponse.<List<ClassMaterialResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                    .setData(classMaterialResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-teacherId-and-materialTypeId/{teacher_id}/{class_materials_type_id}")
    ApiResponse<List<ClassMaterialResponse>> getClassMaterialByTeacherUserIdAndMaterialType(
            @RequestParam Integer teacher_id,
            @RequestParam Integer class_materials_type_id
    ) throws IllegalStateException {
        if (teacher_id <= 0 && class_materials_type_id <= 0)
            throw new IllegalStateException("teacher_id and class_materials_type_id cannot be less than 1");
        if (teacher_id <= 0) throw new IllegalStateException("teacher_id cannot be less than 1");
        if (class_materials_type_id <= 0)
            throw new IllegalStateException("class_materials_type_id cannot be less than 1");
        try {
            List<ClassMaterialResponse> classMaterialResponses = classMaterialServiceImp.getClassMaterialByCreatedByAndMaterialType(teacher_id, class_materials_type_id);
            if (classMaterialResponses.isEmpty()) {
                return ApiResponse.<List<ClassMaterialResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classMaterialResponses);
            }
            return ApiResponse.<List<ClassMaterialResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                    .setData(classMaterialResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-materialTypeId/{id}")
    ApiResponse<List<ClassMaterialResponse>> getClassMaterialByMaterialTypeId(
            @RequestParam Integer class_materials_type_id
    ) throws IllegalStateException {
        if (class_materials_type_id <= 0)
            throw new IllegalStateException("class_materials_type_id cannot be less than 1");
        try {
            List<ClassMaterialResponse> classMaterialResponses = classMaterialServiceImp.getClassMaterialByMaterialTypeId(class_materials_type_id);
            if (classMaterialResponses.isEmpty()) {
                return ApiResponse.<List<ClassMaterialResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classMaterialResponses);
            }
            return ApiResponse.<List<ClassMaterialResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                    .setData(classMaterialResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }

    }
    @GetMapping("/get-by-classId-and-teacherId/{class_id}/{teacher_id}")
    ApiResponse<List<ClassMaterialByTeacherIdAndClassIdResponse>> getByClassIdAndTeacherId(
            @RequestParam Integer teacher_id,
            @RequestParam Integer class_id
    ) throws IllegalStateException {
        if (teacher_id <= 0 && class_id <= 0)
            throw new IllegalStateException("teacher_id and class_id cannot be less than 1");
        if (teacher_id <= 0) throw new IllegalStateException("teacher_id cannot be less than 1");
        if (class_id <= 0)
            throw new IllegalStateException("class_id cannot be less than 1");
        try {
            List<ClassMaterialByTeacherIdAndClassIdResponse> classMaterialResponses = classMaterialServiceImp.getByClassIdAndTeacherId(teacher_id, class_id);
            if (classMaterialResponses.isEmpty()) {
                return ApiResponse.<List<ClassMaterialByTeacherIdAndClassIdResponse>>ok(ClassMaterialByTeacherIdAndClassIdResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classMaterialResponses);
            }
            return ApiResponse.<List<ClassMaterialByTeacherIdAndClassIdResponse>>ok(ClassMaterialByTeacherIdAndClassIdResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                    .setData(classMaterialResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }
    @GetMapping("/get-by-classId{id}")
    ApiResponse<List<ClassMaterialByClassIdResponse>> getByClassId(@RequestParam Integer class_id){
        List<ClassMaterialByClassIdResponse> classMaterialByClassIdResponses = classMaterialServiceImp.getByClassId(class_id);
        if (classMaterialByClassIdResponses.isEmpty()) {
            return ApiResponse.<List<ClassMaterialByClassIdResponse>>ok(ClassMaterialByClassIdResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(classMaterialByClassIdResponses);
        }
        return ApiResponse.<List<ClassMaterialByClassIdResponse>>ok(ClassMaterialByClassIdResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                .setData(classMaterialByClassIdResponses);
    }
    @GetMapping("/get-by-classId-and-classroomId/{class_id}/{classroom_id}")
    ApiResponse<List<ClassMaterialByClassIdAndClassroomIdResponse>> getByClassIdAndClassroomId(
            @RequestParam Integer class_id,
            @RequestParam Integer classroom_id
    ) {
        List<ClassMaterialByClassIdAndClassroomIdResponse> classMaterialByClassIdAndClassroomIdResponses = classMaterialServiceImp.getByClassIdAndClassroomId(class_id,classroom_id);
        if (classMaterialByClassIdAndClassroomIdResponses.isEmpty()) {
            return ApiResponse.<List<ClassMaterialByClassIdAndClassroomIdResponse>>ok(ClassMaterialByClassIdAndClassroomIdResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(classMaterialByClassIdAndClassroomIdResponses);
        }
        return ApiResponse.<List<ClassMaterialByClassIdAndClassroomIdResponse>>ok(ClassMaterialByClassIdAndClassroomIdResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                .setData(classMaterialByClassIdAndClassroomIdResponses);
    }
    @GetMapping("/get-by-materialType-and-classId/{class_id}/{class_materials_type_id}")
    ApiResponse<List<ClassMaterialByClassIdAndMaterialTypeResponse>> getByMaterialTypeAndClassId(
            @RequestParam Integer class_materials_type_id,
            @RequestParam Integer class_id
    ){
        List<ClassMaterialByClassIdAndMaterialTypeResponse> classMaterialResponses = classMaterialServiceImp.getByMaterialTypeAndClassId(class_materials_type_id,class_id);
        if (classMaterialResponses.isEmpty()) {
            return ApiResponse.<List<ClassMaterialByClassIdAndMaterialTypeResponse>>ok(ClassMaterialByClassIdAndMaterialTypeResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(classMaterialResponses);
        }
        return ApiResponse.<List<ClassMaterialByClassIdAndMaterialTypeResponse>>ok(ClassMaterialByClassIdAndMaterialTypeResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                .setData(classMaterialResponses);
    }
    @GetMapping("get-by-studentId/{user_id}")
    ApiResponse<List<ClassMaterialByStudentIdResponse>> getByStudentId(@RequestParam Integer user_id){
        List<ClassMaterialByStudentIdResponse> classMaterialResponses = classMaterialServiceImp.getByStudentId(user_id);
        if (classMaterialResponses.isEmpty()) {
            return ApiResponse.<List<ClassMaterialByStudentIdResponse>>ok(ClassMaterialByStudentIdResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(classMaterialResponses);
        }
        return ApiResponse.<List<ClassMaterialByStudentIdResponse>>ok(ClassMaterialByStudentIdResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                .setData(classMaterialResponses);
    }
}
