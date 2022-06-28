package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.GetClassRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassDeleteResponse;
import com.kshrd.tnakrean.model.classmaterials.response.ClassInertResponse;
import com.kshrd.tnakrean.model.classmaterials.response.ClassUpdateResponse;
import com.kshrd.tnakrean.model.user.response.GetAllStudentResponse;
import com.kshrd.tnakrean.repository.ClassRepository;
import com.kshrd.tnakrean.service.serviceImplementation.ClassServiceImp;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/class")
@SecurityRequirement(name = "bearerAuth")
public class ClassController {

    final ClassServiceImp classServiceImp;
    final ClassRepository classRepository;

    @Autowired
    public ClassController(ClassServiceImp classServiceImp, ClassRepository classRepository) {
        this.classServiceImp = classServiceImp;
        this.classRepository = classRepository;
    }

    @PostMapping("/addClass")
    public ApiResponse<ClassInertResponse> insertClass(@Valid String className) {
        try {
            classServiceImp.insertClass(className);
            if (className.isEmpty()) {
                return ApiResponse.<ClassInertResponse>setError("student class")
                        .setResponseMsg(BaseMessage.Error.INSERT_ERROR.getMessage())
                        .setData(new ClassInertResponse(className));
            } else {
                return ApiResponse.<ClassInertResponse>ok("student class")
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(new ClassInertResponse(className));
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @DeleteMapping("/deleteClass")
    public ApiResponse<ClassDeleteResponse> deleteClass(Integer classId) {
        try {
            classServiceImp.deleteClass(classId);
            if (classId == null) {
                return ApiResponse.<ClassDeleteResponse>setError("student class")
                        .setResponseMsg(BaseMessage.Error.DELETE_ERROR.getMessage())
                        .setData(new ClassDeleteResponse(classId));
            } else {
                return ApiResponse.<ClassDeleteResponse>ok("student class")
                        .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                        .setData(new ClassDeleteResponse(classId))
                        .setResponseMsg(classId + " had deleted");
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("/updateClass")
    public ApiResponse<ClassUpdateResponse> updateClassName(@RequestBody @Valid ClassUpdateResponse classUpdateResponse) {
        Boolean a = classRepository.checkIfClassExists(classUpdateResponse.getId());
        try {
            classServiceImp.UpdateClass(classUpdateResponse.getId(), classUpdateResponse.getClassname());
            if (classUpdateResponse.equals(null)) {
                return ApiResponse.<ClassUpdateResponse>setError(GetAllStudentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage());
            } else if (a.equals(false)) {
                return ApiResponse.<ClassUpdateResponse>setError(GetAllStudentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage());
            } else {
                String className = "Student ID: " + classUpdateResponse.getId() + " Class new name: " + classUpdateResponse.getClassname();
                return ApiResponse
                        .<ClassUpdateResponse>ok(className)
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(new ClassUpdateResponse(classUpdateResponse.getId(), classUpdateResponse.getClassname()));
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }


    }

    @GetMapping("/GetAllClass")
    public ApiResponse<List<GetClassRequest>> getAllStudent() {
        try {
            List<GetClassRequest> getClassRequests = classServiceImp.getAllClass();
            if (getClassRequests.isEmpty()) {
                return ApiResponse.<List<GetClassRequest>>ok(GetClassRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(getClassRequests);
            } else {
                return ApiResponse.<List<GetClassRequest>>ok("Get All Class")
                        .setData(getClassRequests);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
