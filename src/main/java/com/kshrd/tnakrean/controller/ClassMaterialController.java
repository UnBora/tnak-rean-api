package com.kshrd.tnakrean.controller;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
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

    @GetMapping("/getById")
    ApiResponse<List<ClassMaterialResponse>> getClassMaterial(int id) {
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

    @PutMapping("update")
    ApiResponse<ClassMaterialResponse> updateClassMaterial(
            @RequestBody ClassMaterialUpdateRequest classMaterialUpdateRequest
    ) {
        ClassMaterialResponse response = classMaterialRepository.selectResponseAfterUpdate(classMaterialUpdateRequest.getId());
        classMaterialServiceImp.updateClassMaterial(classMaterialUpdateRequest);
        return ApiResponse.<ClassMaterialResponse>
                ok(ClassMaterialResponse.class.getSimpleName()).setData(response);
    }

}
