package com.kshrd.tnakrean.controller;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateContentRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateTitleDesRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;
import com.kshrd.tnakrean.repository.ClassMaterialRepository;
import com.kshrd.tnakrean.service.serviceImplementation.ClassMaterialImp;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

//@Builder
@RestController
@RequestMapping("/api/v1/classMaterial")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
public class ClassMaterialController {

    final
    ClassMaterialRepository classMaterialRepository;
    final ClassMaterialImp classMaterialServiceImp;

    public ClassMaterialController(ClassMaterialImp classMaterialService, ClassMaterialRepository classMaterialRepository) {
        this.classMaterialServiceImp = classMaterialService;
        this.classMaterialRepository = classMaterialRepository;
    }

    @GetMapping("/get-by-id")
    ApiResponse<ClassMaterialResponse> getById(@RequestParam @Min(value = 1) int class_material_id) {
        try {
            ClassMaterialResponse classMaterialResponses = classMaterialServiceImp.getClassMaterial(class_material_id);
            if (classMaterialResponses == null) {
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
        Boolean created = classMaterialRepository.checkCreatedBy(classMaterialRequest.getCreated_by());
        Boolean materialsTypeId = classMaterialRepository.checkMaterialsTypeId(classMaterialRequest.getClass_materials_type_id());
        try {
            if (created == false) {
                return ApiResponse.<ClassMaterialRequest>notFound(ClassMaterialRequest.class.getSimpleName())
                        .setResponseMsg("The Created_by_id: "+classMaterialRequest.getCreated_by()+" doesn't exit in the table");
            } else if (materialsTypeId == false) {
                return ApiResponse.<ClassMaterialRequest>notFound(ClassMaterialRequest.class.getSimpleName())
                        .setResponseMsg("The class_materials_type_id: "+classMaterialRequest.getClass_materials_type_id()+" doesn't exit in the table");
            } else {
                classMaterialRequest.setTitle(classMaterialRequest.getTitle().trim());
                classMaterialRequest.setDescription(classMaterialRequest.getDescription().trim());
                classMaterialServiceImp.insertClassMaterial(classMaterialRequest);
                return ApiResponse.<ClassMaterialRequest>ok(ClassMaterialRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(classMaterialRequest);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("update-title-and-description")
    ApiResponse<ClassMaterialUpdateTitleDesRequest> updateClassMaterial(
            @RequestBody @Valid ClassMaterialUpdateTitleDesRequest classMaterialUpdateTitleDesRequest
    ) {
        try {
            // ClassMaterialResponse response = classMaterialRepository.selectResponseAfterUpdate(classMaterialUpdateRequest.getId());
            ClassMaterialUpdateTitleDesRequest classMaterialUpdateTitleDesRequest1 = classMaterialServiceImp.updateClassMaterial(classMaterialUpdateTitleDesRequest);
            if (classMaterialUpdateTitleDesRequest1 == null) {
                return ApiResponse.<ClassMaterialUpdateTitleDesRequest>notFound(ClassMaterialUpdateTitleDesRequest.class.getSimpleName())
                        .setResponseMsg("Can't update! ID: " + classMaterialUpdateTitleDesRequest.getClass_material_id() + " doesn't exist");
            }
            classMaterialUpdateTitleDesRequest.setTitle(classMaterialUpdateTitleDesRequest.getTitle().trim());
            classMaterialUpdateTitleDesRequest.setDescription(classMaterialUpdateTitleDesRequest.getDescription().trim());
            classMaterialServiceImp.updateClassMaterial(classMaterialUpdateTitleDesRequest);
            return ApiResponse.<ClassMaterialUpdateTitleDesRequest>ok(ClassMaterialUpdateTitleDesRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                    .setData(classMaterialUpdateTitleDesRequest);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("update-content")
    ApiResponse<ClassMaterialUpdateContentRequest> updateContent(
            @RequestBody @Valid ClassMaterialUpdateContentRequest classMaterialUpdateContentRequest
    ) {
        try {
            // ClassMaterialResponse response = classMaterialRepository.selectResponseAfterUpdate(classMaterialUpdateContentRequest.getId());
            ClassMaterialUpdateContentRequest response = classMaterialServiceImp.updateContent(classMaterialUpdateContentRequest);
            if (response == null) {
                return ApiResponse.<ClassMaterialUpdateContentRequest>notFound(ClassMaterialUpdateContentRequest.class.getSimpleName())
                        .setResponseMsg("Can't update! ID: " + classMaterialUpdateContentRequest.getClass_material_id() + " doesn't exist");
            }
            return ApiResponse.<ClassMaterialUpdateContentRequest>ok(ClassMaterialUpdateContentRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                    .setData(response);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @DeleteMapping("/delete-by-id")
    ApiResponse<Boolean> deleteById(@RequestParam @Min(value = 1) Integer id) {
        try {
            boolean checkMaterialId = classMaterialRepository.findMaterialId(id);
            boolean checkMaterialIdInMaterialsDetail = classMaterialRepository.findMaterialIdInMaterialsDetail(id);

            if (checkMaterialId == false) {
                return ApiResponse.<Boolean>notFound("")
                        .setResponseMsg("Can't delete! classMaterialId: " + id + " doesn't exist");
            } else if (checkMaterialIdInMaterialsDetail == true ) {
                return ApiResponse.<Boolean>notFound("")
                        .setResponseMsg("Can't delete! classMaterialId: " + id + " is still referenced from table class_materials_detail");
            } else {
                classMaterialServiceImp.deleteById(id);
                return ApiResponse.<Boolean>ok("Class Materials")
                        .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                        .setData(true);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
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

    @GetMapping("/get-by-teacherId")
    ApiResponse<List<ClassMaterialByTeacherResponse>> getAllClassMaterialByTeacherUserId(){
        try {
            Integer user_id = AuthRestController.user_id;
            List<ClassMaterialByTeacherResponse> classMaterialResponses = classMaterialServiceImp.getAllClassMaterialByTeacherUserId(user_id);
            if (classMaterialResponses.isEmpty()) {
                return ApiResponse.<List<ClassMaterialByTeacherResponse>>notFound(ClassMaterialResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classMaterialResponses);
            }
            return ApiResponse.<List<ClassMaterialByTeacherResponse>>ok(ClassMaterialResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(classMaterialResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-teacherId-and-materialTypeId")
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

    @GetMapping("/get-by-materialTypeId")
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

    @GetMapping("/get-by-classId-and-teacherId")
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

//    @GetMapping("/get-by-classId")
//    ApiResponse<List<ClassMaterialByClassIdResponse>> getByClassId(@RequestParam @Min(value = 1) Integer class_id) {
//        try {
//            List<ClassMaterialByClassIdResponse> classMaterialByClassIdResponses = classMaterialServiceImp.getByClassId(class_id);
//            if (classMaterialByClassIdResponses.isEmpty()) {
//                return ApiResponse.<List<ClassMaterialByClassIdResponse>>notFound(ClassMaterialByClassIdResponse.class.getSimpleName())
//                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
//                        .setData(classMaterialByClassIdResponses);
//            }
//            return ApiResponse.<List<ClassMaterialByClassIdResponse>>ok(ClassMaterialByClassIdResponse.class.getSimpleName())
//                    .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
//                    .setData(classMaterialByClassIdResponses);
//        } catch (Exception e) {
//            return ApiResponse.setError(e.getMessage());
//        }
//    }

    @GetMapping("/get-by-classId-and-classroomId")
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
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-materialType-and-classId")
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

    @GetMapping("get-by-studentUserId")
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
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-studentUserId-classId-classroomId")
    ApiResponse<List<ClassMaterialByStudentIdClassIdAndClassroomIdResponse>> getByUserClassClassroom(
            @RequestParam @Min(value = 1) Integer student_user_id,
            @RequestParam @Min(value = 1) Integer class_id,
            @RequestParam @Min(value = 1) Integer classroom_id
    ) {
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
