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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("api/v1/classMaterialsType")
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
        try {
            classMaterialsTypeImpl.insertClassMaterialsType(classMaterialsTypeRequest);
            return ApiResponse.<ClassMaterialsTypeRequest>ok(ClassMaterialsTypeRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                    .setData(classMaterialsTypeRequest);
        } catch (Exception e) {
            return ApiResponse.<ClassMaterialsTypeRequest>badRequest(ClassMaterialsTypeRequest.class.getSimpleName())
                    .setResponseMsg("Can't insert! Because type:"+classMaterialsTypeRequest.getType()+" already exist");
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
        try {
            ClassMaterialsTypeResponse classMaterialsTypeResponse = classMaterialsTypeImpl.deleteById(id);
            if (classMaterialsTypeResponse == null) {
                return ApiResponse.<Boolean>notFound("")
                        .setResponseMsg("Can't delete! ID: " +id+ " doesn't exist")
                        .setData(null);
            }
                return ApiResponse.<Boolean>ok("")
                        .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                        .setData(true);
        } catch (Exception e) {
            return ApiResponse.<Boolean>badRequest("")
                    .setResponseMsg("Can't delete! Because of violates foreign key constraint");
        }
    }
}
