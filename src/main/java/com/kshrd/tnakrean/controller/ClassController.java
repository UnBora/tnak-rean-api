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
import javax.validation.constraints.Min;
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

    @PostMapping("/create-class")
    public ApiResponse<ClassInertResponse> insertClass(@RequestBody @Valid ClassInertResponse classInertResponse) {

        Boolean classNameCheck=classRepository.checkIfClassExistsDuplecateClassName(classInertResponse.getClassName().toUpperCase());
        System.out.println(classInertResponse.getClassName().toUpperCase());
        try {
            if (classInertResponse.equals(null)) {
                return ApiResponse.<ClassInertResponse>setError(ClassInertResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.INSERT_ERROR.getMessage())
                        .setData(new ClassInertResponse(classInertResponse.getClassName()));
            } else if(classNameCheck.equals(true)){
                return ApiResponse.<ClassInertResponse>duplicateEntry(ClassInertResponse.class.getSimpleName())
                        .setResponseMsg("The class name already exists!")
                        .setData(new ClassInertResponse(classInertResponse.getClassName()));
            }else {
                classServiceImp.insertClass(classInertResponse.getClassName().toUpperCase());
                return ApiResponse.<ClassInertResponse>ok(ClassInertResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(new ClassInertResponse(classInertResponse.getClassName()));
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
    @DeleteMapping("/delete-class")
    public ApiResponse<ClassDeleteResponse> deleteClass(
            @RequestParam @Min(value = 1, message = "{validation.classId.notNegative}") Integer classId)
    {
        try {
            Boolean checkClassId=classRepository.checkIfClassExists(classId);
            if (classId == null) {
                return ApiResponse.<ClassDeleteResponse>setError(ClassDeleteResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.DELETE_ERROR.getMessage())
                        .setData(new ClassDeleteResponse(classId));
            } else if(checkClassId.equals(false)){
                return ApiResponse.<ClassDeleteResponse>notFound(ClassDeleteResponse.class.getSimpleName())
                        .setResponseMsg("The Class ID:"+classId+" does not have!")
                        .setData(new ClassDeleteResponse(classId));
            } else{

                try{
                    boolean del = classServiceImp.deleteClass(classId);
                    if (del) {
                        return ApiResponse.<ClassDeleteResponse>ok(ClassDeleteResponse.class.getSimpleName())
                                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                                .setData(new ClassDeleteResponse(classId));
                    }
                }catch (Exception e) {
                    return ApiResponse.<ClassDeleteResponse>ok(ClassDeleteResponse.class.getSimpleName())
                            .setResponseMsg(BaseMessage.Error.DELETE_ERROR.getMessage()+" because of primary key")
                            .setData(new ClassDeleteResponse(classId));
                }
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
        return null;
    }
    @PutMapping("/update-class")
    public ApiResponse<ClassUpdateResponse> updateClassName(@RequestBody @Valid ClassUpdateResponse classUpdateResponse) {
        Boolean a = classRepository.checkIfClassExists(classUpdateResponse.getId());
        Boolean nameCheck=classRepository.checkIfClassExistsDuplecateClassName(classUpdateResponse.getClassname().toUpperCase());
        try {
            if (classUpdateResponse.equals(null)) {
                return ApiResponse.<ClassUpdateResponse>setError(GetAllStudentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage());
            } else if (a.equals(false)) {
                return ApiResponse.<ClassUpdateResponse>notFound(GetAllStudentResponse.class.getSimpleName())
                        .setResponseMsg("The Class ID:"+classUpdateResponse.getId()+" does not have!");
            } else if(nameCheck.equals(true)){
                return ApiResponse.<ClassUpdateResponse>duplicateEntry(GetAllStudentResponse.class.getSimpleName())
                        .setResponseMsg("The class name already exists!");
            } else {
                classServiceImp.UpdateClass(classUpdateResponse.getId(), classUpdateResponse.getClassname().toUpperCase());
                String className = "Student ID: " + classUpdateResponse.getId() + " Class new name: " + classUpdateResponse.getClassname();
                return ApiResponse
                        .<ClassUpdateResponse>ok(className)
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(new ClassUpdateResponse(classUpdateResponse.getId(), classUpdateResponse.getClassname().toUpperCase()));
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-all-class")
    public ApiResponse<List<GetClassRequest>> getAllStudent() {
        try {
            List<GetClassRequest> getClassRequests = classServiceImp.getAllClass();
            if (getClassRequests.isEmpty()) {
                return ApiResponse.<List<GetClassRequest>>ok(GetClassRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(getClassRequests);
            } else {
                return ApiResponse.<List<GetClassRequest>>ok(GetClassRequest.class.getSimpleName())
                        .setData(getClassRequests);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
