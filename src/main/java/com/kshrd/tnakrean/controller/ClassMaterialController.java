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

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    ApiResponse<ClassMaterialResponse> getById(@RequestParam @Min(value = 1) int class_material_id) {
        try {
            ClassMaterialResponse classMaterialResponses = classMaterialServiceImp.getClassMaterial(class_material_id);
            if (classMaterialResponses == null){
                return ApiResponse.<ClassMaterialResponse>notFound(ClassMaterialResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
            return ApiResponse.<ClassMaterialResponse>ok(ClassMaterialResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                    .setData(classMaterialResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("insert")
    ApiResponse<ClassMaterialRequest> insertClassMaterial(
            @RequestBody @Valid ClassMaterialRequest classMaterialRequest
    ) {
        try {
            classMaterialServiceImp.insertClassMaterial(classMaterialRequest);
            return ApiResponse.<ClassMaterialRequest>ok(ClassMaterialRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                    .setData(classMaterialRequest);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
    @PutMapping("update-title-and-description")
    ApiResponse<ClassMaterialResponse> updateClassMaterial(
            @RequestBody @Valid ClassMaterialUpdateRequest classMaterialUpdateRequest
    ) {
        try {
        ClassMaterialResponse response = classMaterialRepository.selectResponseAfterUpdate(classMaterialUpdateRequest.getId());
        classMaterialServiceImp.updateClassMaterial(classMaterialUpdateRequest);
        if (response == null) {
            return ApiResponse.<ClassMaterialResponse>notFound(ClassMaterialResponse.class.getSimpleName())
                    .setResponseMsg("Can't update! ID: "+classMaterialUpdateRequest.getId()+" doesn't exist");
        }
        return ApiResponse.<ClassMaterialResponse>ok(ClassMaterialResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(response);
    } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
    @PutMapping("update-content")
    ApiResponse<ClassMaterialResponse> updateContent(
            @RequestBody @Valid ClassMaterialUpdateContentRequest classMaterialUpdateContentRequest
    ) {
        try {
            ClassMaterialResponse response = classMaterialRepository.selectResponseAfterUpdate(classMaterialUpdateContentRequest.getId());
            classMaterialServiceImp.updateContent(classMaterialUpdateContentRequest);
            if (response == null) {
                return ApiResponse.<ClassMaterialResponse>notFound(ClassMaterialResponse.class.getSimpleName())
                        .setResponseMsg("Can't update! ID: " + classMaterialUpdateContentRequest.getId() + " doesn't exist");
            }
            return ApiResponse.<ClassMaterialResponse>ok(ClassMaterialResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                    .setData(response);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @DeleteMapping("/delete-by-id/{id}")
    ApiResponse<Boolean> deleteById(@RequestParam Integer id) {
        try {
            ClassMaterialResponse response = classMaterialServiceImp.deleteById(id);
            if (response == null) {
                return ApiResponse.<Boolean>notFound("Class Materials")
                        .setResponseMsg("Can't delete! ID: " + id + " doesn't exist")
                        .setData(null);
            }
            return ApiResponse.<Boolean>ok("Class Materials")
                    .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                    .setData(true);
        } catch (Exception e) {
            return ApiResponse.<Boolean>badRequest("")
                    .setResponseMsg("Can't delete! Because of violates foreign key constraint");
        }
    }

    @GetMapping("/get-all")
    ApiResponse<List<ClassMaterialResponse>> getAllClassMaterial() {
        try {
            List<ClassMaterialResponse> classMaterialResponses = classMaterialServiceImp.getAllClassMaterial();
            if (classMaterialResponses.isEmpty()) {
                return ApiResponse.<List<ClassMaterialResponse>>notFound(ClassMaterialResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classMaterialResponses);
            }
            return ApiResponse.<List<ClassMaterialResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(classMaterialResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-teacheId/{teacher_id}")
    ApiResponse<List<ClassMaterialResponse>> getAllClassMaterialByTeacherUserId(
            @RequestParam @Min(value = 1) Integer teacher_id) {
        try {
            List<ClassMaterialResponse> classMaterialResponses = classMaterialServiceImp.getAllClassMaterialByTeacherUserId(teacher_id);
            if (classMaterialResponses.isEmpty()) {
                return ApiResponse.<List<ClassMaterialResponse>>notFound(ClassMaterialResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classMaterialResponses);
            }
            return ApiResponse.<List<ClassMaterialResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(classMaterialResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-teacherId-and-materialTypeId/{teacher_id}/{class_materials_type_id}")
    ApiResponse<List<ClassMaterialResponse>> getClassMaterialByTeacherUserIdAndMaterialType(
            @RequestParam @Min(value = 1) Integer teacher_id,
            @RequestParam @Min(value = 1) Integer class_materials_type_id
    ) {
        try {
            List<ClassMaterialResponse> classMaterialResponses = classMaterialServiceImp.getClassMaterialByCreatedByAndMaterialType(teacher_id, class_materials_type_id);
            if (classMaterialResponses.isEmpty()) {
                return ApiResponse.<List<ClassMaterialResponse>>notFound(ClassMaterialResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classMaterialResponses);
            }
            return ApiResponse.<List<ClassMaterialResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(classMaterialResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-materialTypeId/{id}")
    ApiResponse<List<ClassMaterialResponse>> getClassMaterialByMaterialTypeId(
            @RequestParam @Min(value = 1) Integer class_materials_type_id
    ) {
        try {
            List<ClassMaterialResponse> classMaterialResponses = classMaterialServiceImp.getClassMaterialByMaterialTypeId(class_materials_type_id);
            if (classMaterialResponses.isEmpty()) {
                return ApiResponse.<List<ClassMaterialResponse>>notFound(ClassMaterialResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classMaterialResponses);
            }
            return ApiResponse.<List<ClassMaterialResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(classMaterialResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-classId-and-teacherId/{class_id}/{teacher_id}")
    ApiResponse<List<ClassMaterialByTeacherIdAndClassIdResponse>> getByClassIdAndTeacherId(
            @RequestParam @Min(value = 1) Integer teacher_id,
            @RequestParam @Min(value = 1) Integer class_id
    ) {
        try {
            List<ClassMaterialByTeacherIdAndClassIdResponse> classMaterialResponses = classMaterialServiceImp.getByClassIdAndTeacherId(teacher_id, class_id);
            if (classMaterialResponses.isEmpty()) {
                return ApiResponse.<List<ClassMaterialByTeacherIdAndClassIdResponse>>notFound(ClassMaterialByTeacherIdAndClassIdResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classMaterialResponses);
            }
            return ApiResponse.<List<ClassMaterialByTeacherIdAndClassIdResponse>>ok(ClassMaterialByTeacherIdAndClassIdResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(classMaterialResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-classId{class_id}")
    ApiResponse<List<ClassMaterialByClassIdResponse>> getByClassId(@RequestParam @Min(value = 1) Integer class_id) {
        try {
        List<ClassMaterialByClassIdResponse> classMaterialByClassIdResponses = classMaterialServiceImp.getByClassId(class_id);
        if (classMaterialByClassIdResponses.isEmpty()) {
            return ApiResponse.<List<ClassMaterialByClassIdResponse>>notFound(ClassMaterialByClassIdResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(classMaterialByClassIdResponses);
        }
        return ApiResponse.<List<ClassMaterialByClassIdResponse>>ok(ClassMaterialByClassIdResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                .setData(classMaterialByClassIdResponses);
    }catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-classId-and-classroomId/{class_id}/{classroom_id}")
    ApiResponse<List<ClassMaterialByClassIdAndClassroomIdResponse>> getByClassIdAndClassroomId(
            @RequestParam @Min(value = 1) Integer class_id,
            @RequestParam @Min(value = 1) Integer classroom_id
    ) {
        try {
        List<ClassMaterialByClassIdAndClassroomIdResponse> classMaterialByClassIdAndClassroomIdResponses = classMaterialServiceImp.getByClassIdAndClassroomId(class_id, classroom_id);
        if (classMaterialByClassIdAndClassroomIdResponses.isEmpty()) {
            return ApiResponse.<List<ClassMaterialByClassIdAndClassroomIdResponse>>notFound(ClassMaterialByClassIdAndClassroomIdResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(classMaterialByClassIdAndClassroomIdResponses);
        }
        return ApiResponse.<List<ClassMaterialByClassIdAndClassroomIdResponse>>ok(ClassMaterialByClassIdAndClassroomIdResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(classMaterialByClassIdAndClassroomIdResponses);
    }catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-materialType-and-classId/{class_id}/{class_materials_type_id}")
    ApiResponse<List<ClassMaterialByClassIdAndMaterialTypeResponse>> getByMaterialTypeAndClassId(
            @RequestParam @Min(value = 1) Integer class_materials_type_id,
            @RequestParam @Min(value = 1) Integer class_id
    ) {
        try {
        List<ClassMaterialByClassIdAndMaterialTypeResponse> classMaterialResponses = classMaterialServiceImp.getByMaterialTypeAndClassId(class_materials_type_id, class_id);
        if (classMaterialResponses.isEmpty()) {
            return ApiResponse.<List<ClassMaterialByClassIdAndMaterialTypeResponse>>notFound(ClassMaterialByClassIdAndMaterialTypeResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(classMaterialResponses);
        }
        return ApiResponse.<List<ClassMaterialByClassIdAndMaterialTypeResponse>>ok(ClassMaterialByClassIdAndMaterialTypeResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(classMaterialResponses);
    } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-studentId/{student_id}")
    ApiResponse<List<ClassMaterialByStudentIdResponse>> getByStudentId(@RequestParam @Min(value = 1) Integer student_user_id) throws IllegalStateException {
        try {
        List<ClassMaterialByStudentIdResponse> classMaterialResponses = classMaterialServiceImp.getByStudentId(student_user_id);
        if (classMaterialResponses.isEmpty()) {
            return ApiResponse.<List<ClassMaterialByStudentIdResponse>>notFound(ClassMaterialByStudentIdResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(classMaterialResponses);
        }
        return ApiResponse.<List<ClassMaterialByStudentIdResponse>>ok(ClassMaterialByStudentIdResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                .setData(classMaterialResponses);
    }catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-studentId-classId-classroomId/{student_user_id}/{class_id}/{classroom_id}")
    ApiResponse<List<ClassMaterialByStudentIdClassIdAndClassroomIdResponse>> getByUserClassClassroom(
            @RequestParam @Min(value = 1) Integer student_user_id,
            @RequestParam @Min(value = 1) Integer class_id,
            @RequestParam @Min(value = 1) Integer classroom_id
    ){
            try {
                List<ClassMaterialByStudentIdClassIdAndClassroomIdResponse> classMaterialResponses = classMaterialServiceImp.getByUserClassClassroom(student_user_id, class_id, classroom_id);
                if (classMaterialResponses.isEmpty()) {
                    return ApiResponse.<List<ClassMaterialByStudentIdClassIdAndClassroomIdResponse>>notFound(ClassMaterialResponse.class.getSimpleName())
                            .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                            .setData(classMaterialResponses);
                }
                return ApiResponse.<List<ClassMaterialByStudentIdClassIdAndClassroomIdResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                        .setData(classMaterialResponses);
            } catch (Exception e) {
                return ApiResponse.setError(e.getMessage());
            }
    }
}
