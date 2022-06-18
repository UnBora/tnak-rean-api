package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialsTypeRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialsTypeUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialsTypeResponse;
import com.kshrd.tnakrean.repository.ClassMaterialsTypeRepository;
import com.kshrd.tnakrean.service.serviceImplementation.ClassMaterialsTypeImpl;
import org.springframework.web.bind.annotation.*;

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
            return ApiResponse.<List<ClassMaterialsTypeResponse>>setError(ClassMaterialsTypeResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(classMaterialsTypeResponse);
        }
        return ApiResponse.<List<ClassMaterialsTypeResponse>>ok(ClassMaterialsTypeResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(classMaterialsTypeResponse);
    }

    @GetMapping("/getById/{id}")
    ApiResponse<ClassMaterialsTypeResponse> getClassMaterialsTypeById(@RequestParam Integer id) {
        ClassMaterialsTypeResponse classMaterialsTypeResponse = classMaterialsTypeImpl.getClassMaterialsTypeById(id);
        if (classMaterialsTypeResponse == null) {
            return ApiResponse.<ClassMaterialsTypeResponse>setError(ClassMaterialsTypeResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(null);
        }
        return ApiResponse.<ClassMaterialsTypeResponse>ok(ClassMaterialsTypeResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(classMaterialsTypeResponse);
    }

    @PostMapping("/insert")
    ApiResponse<ClassMaterialsTypeRequest> insertClassMaterialsType(
            @RequestBody ClassMaterialsTypeRequest classMaterialsTypeRequest
    ) {
        classMaterialsTypeImpl.insertClassMaterialsType(classMaterialsTypeRequest);
        return ApiResponse.<ClassMaterialsTypeRequest>ok(ClassMaterialsTypeRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                .setData(classMaterialsTypeRequest);
    }

    @PutMapping("/update")
    ApiResponse<ClassMaterialsTypeUpdateRequest> updateClassMaterialsType(
            @RequestBody ClassMaterialsTypeUpdateRequest classMaterialsTypeUpdateRequest
    ) {
        classMaterialsTypeImpl.updateClassMaterialsType(classMaterialsTypeUpdateRequest);

        return ApiResponse.<ClassMaterialsTypeUpdateRequest>ok(ClassMaterialsTypeUpdateRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(classMaterialsTypeUpdateRequest);
    }

    @DeleteMapping("/deleteByid/{id}")
    ApiResponse<Boolean> deleteById(@RequestParam Integer id) {
        classMaterialsTypeImpl.deleteById(id);
        return ApiResponse.<Boolean>ok("Class Materials Type")
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                .setData(true);
    }
}
