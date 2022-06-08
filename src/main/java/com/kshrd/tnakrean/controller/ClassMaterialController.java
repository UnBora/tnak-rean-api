package com.kshrd.tnakrean.controller;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
import com.kshrd.tnakrean.model.classmaterials.response.NotificationResponse;
import com.kshrd.tnakrean.repository.ClassMaterialRepository;
import com.kshrd.tnakrean.service.serviceImplementation.ClassMaterialImpl;
import com.kshrd.tnakrean.service.serviceInter.ClassMaterialService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Builder
@RestController
@RequestMapping("/api/v1/classMaterial")
public class ClassMaterialController {

    final
    ClassMaterialRepository classMaterialRepository;
    final ClassMaterialService classMaterialService;

    public ClassMaterialController(ClassMaterialService classMaterialService, ClassMaterialRepository classMaterialRepository) {
        this.classMaterialService = classMaterialService;
        this.classMaterialRepository = classMaterialRepository;
    }

    @GetMapping("/getById")
    ApiResponse<List<ClassMaterialResponse>> getClassMaterial(int createdId) {
        List<ClassMaterialResponse> classMaterialResponses = classMaterialService.classMaterial(createdId);
        return ApiResponse.<List<ClassMaterialResponse>>ok().setPayload(classMaterialResponses).setSuccess(true);
    }

    @PostMapping("insert")
    ApiResponse<?> insertClassMaterial(
            @RequestBody ClassMaterialRequest classMaterialRequest
    ) {
        classMaterialService.insertClassMaterial(classMaterialRequest);
        return ApiResponse.<ClassMaterialRequest>ok().setPayload(classMaterialRequest).setSuccess(true);
    }

    @PutMapping("update")
    ApiResponse<?> updateClassMaterial(
            @RequestBody ClassMaterialUpdateRequest classMaterialUpdateRequest
    ) {
        ClassMaterialResponse response = classMaterialRepository.selectResponseAfterUpdate(classMaterialUpdateRequest.getId());
        classMaterialService.updateClassMaterial(classMaterialUpdateRequest);
        System.out.println("LLL" + response);
        return ApiResponse.<ClassMaterialResponse>ok().setPayload(response).setSuccess(true);
    }

}
