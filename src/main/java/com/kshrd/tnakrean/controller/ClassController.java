package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classModel.response.ClassDeleteResponse;
import com.kshrd.tnakrean.model.classModel.response.ClassInertResponse;
import com.kshrd.tnakrean.service.serviceImplementation.ClassServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/class")
public class ClassController {

    final ClassServiceImp classServiceImp;
    @Autowired
    public ClassController(ClassServiceImp classServiceImp) {
        this.classServiceImp = classServiceImp;
    }

    @PostMapping("/addClass")
    public ApiResponse<ClassInertResponse> insertClass(String className) {
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
            if (classId==null) {
                return ApiResponse.<ClassDeleteResponse>setError("student class")
                        .setResponseMsg(BaseMessage.Error.DELETE_ERROR.getMessage())
                        .setData(new ClassDeleteResponse(classId));
            } else {
                return ApiResponse.<ClassDeleteResponse>ok("student class")
                        .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                        .setData(new ClassDeleteResponse(classId));
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
