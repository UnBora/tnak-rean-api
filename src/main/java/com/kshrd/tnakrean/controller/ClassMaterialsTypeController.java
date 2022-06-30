package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialsTypeRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialsTypeUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialsTypeResponse;
import com.kshrd.tnakrean.model.classmaterials.response.CommentResponse;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittableWorkResponse;
import com.kshrd.tnakrean.repository.ClassMaterialsTypeRepository;
import com.kshrd.tnakrean.service.serviceImplementation.ClassMaterialsTypeImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("api/v1/classMaterialsType")
@SecurityRequirement(name = "bearerAuth")
public class ClassMaterialsTypeController {
    final ClassMaterialsTypeImpl classMaterialsTypeImpl;
    final ClassMaterialsTypeRepository classMaterialsTypeRepository;

    public ClassMaterialsTypeController(ClassMaterialsTypeImpl classMaterialsTypeImpl, ClassMaterialsTypeRepository classMaterialsTypeRepository) {
        this.classMaterialsTypeImpl = classMaterialsTypeImpl;
        this.classMaterialsTypeRepository = classMaterialsTypeRepository;
    }

    @GetMapping("/get-all")
    ApiResponse<List<ClassMaterialsTypeResponse>> getAllClassMaterialsType() {
        List<ClassMaterialsTypeResponse> classMaterialsTypeResponse = classMaterialsTypeImpl.getAllClassMaterialsType();
        if (classMaterialsTypeResponse.isEmpty()) {
            return ApiResponse.<List<ClassMaterialsTypeResponse>>notFound(ClassMaterialsTypeResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(classMaterialsTypeResponse);
        }
        return ApiResponse.<List<ClassMaterialsTypeResponse>>ok(ClassMaterialsTypeResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(classMaterialsTypeResponse);
    }

    @GetMapping("/get-by-id/{id}")
    ApiResponse<ClassMaterialsTypeResponse> getClassMaterialsTypeById(@RequestParam @Min(value = 1) Integer id) {
        ClassMaterialsTypeResponse classMaterialsTypeResponse = classMaterialsTypeImpl.getClassMaterialsTypeById(id);
        if (classMaterialsTypeResponse == null) {
            return ApiResponse.<ClassMaterialsTypeResponse>notFound(ClassMaterialsTypeResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
        }
        return ApiResponse.<ClassMaterialsTypeResponse>ok(ClassMaterialsTypeResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(classMaterialsTypeResponse);
    }

    @PostMapping("/insert")
    ApiResponse<ClassMaterialsTypeRequest> insertClassMaterialsType(
            @RequestBody @Valid ClassMaterialsTypeRequest classMaterialsTypeRequest
    ) {
        boolean checkType = classMaterialsTypeRepository.ifTypeExist(classMaterialsTypeRequest.getType());
        try {
            if (checkType == true) {
                return ApiResponse.<ClassMaterialsTypeRequest>notFound(ClassMaterialsTypeRequest.class.getSimpleName())
                        .setResponseMsg("Can't Insert! Because type: "+classMaterialsTypeRequest.getType()+ ". already exist");
            } else {
                classMaterialsTypeImpl.insertClassMaterialsType(classMaterialsTypeRequest);
                return ApiResponse.<ClassMaterialsTypeRequest>ok(ClassMaterialsTypeRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(classMaterialsTypeRequest);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("/update")
    ApiResponse<ClassMaterialsTypeUpdateRequest> updateClassMaterialsType(
            @RequestBody @Valid ClassMaterialsTypeUpdateRequest classMaterialsTypeUpdateRequest
    ) {
        try {
            ClassMaterialsTypeResponse classMaterialsTypeResponse = classMaterialsTypeImpl.updateClassMaterialsType(classMaterialsTypeUpdateRequest);
            if (classMaterialsTypeResponse == null) {
                return ApiResponse.<ClassMaterialsTypeUpdateRequest>notFound(ClassMaterialsTypeUpdateRequest.class.getSimpleName())
                        .setResponseMsg("Can't update! ID: " + classMaterialsTypeUpdateRequest.getId() + " doesn't exist")
                        .setData(null);
            }
            return ApiResponse.<ClassMaterialsTypeUpdateRequest>ok(ClassMaterialsTypeUpdateRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                    .setData(classMaterialsTypeUpdateRequest);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @DeleteMapping("/delete-by-id/{id}")
    ApiResponse<Boolean> deleteById(@RequestParam @Min(value = 1) Integer id) {
        boolean checkTypeID = classMaterialsTypeRepository.ifTypeIdExist(id);
        boolean checkTypeIdInMaterial = classMaterialsTypeRepository.findTypeIdInMaterial(id);
        try {
            if (checkTypeID == false) {
                return ApiResponse.<Boolean>notFound("ClassMaterialsType")
                        .setResponseMsg("Can't delete! MaterialTypeID: " +id+ " doesn't exist");
            } else if (checkTypeIdInMaterial == true) {
                return ApiResponse.<Boolean>notFound("ClassMaterialsType")
                        .setResponseMsg("Can't delete! MaterialTypeID: " +id+ " is still referenced from table class_material");
            } else {
            classMaterialsTypeImpl.deleteById(id);
                return ApiResponse.<Boolean>ok("ClassMaterialsType")
                        .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                        .setData(true);}
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
