package com.kshrd.tnakrean.controller;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.student.request.StudentRequest;
import com.kshrd.tnakrean.model.student.response.StudentResponse;
import com.kshrd.tnakrean.model.user.response.AppUserResponse;
import com.kshrd.tnakrean.repository.StudentRepository;
import com.kshrd.tnakrean.service.serviceImplementation.StudentServiceImp;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    final StudentRepository studentRepository;
    final StudentServiceImp studentServiceImp;

    @Autowired
    public StudentController(StudentRepository studentRepository, StudentServiceImp studentServiceImp) {
        this.studentRepository = studentRepository;
        this.studentServiceImp = studentServiceImp;
    }


    @GetMapping("/get-all")
    public ApiResponse<List<StudentResponse>> getAllStudentFromDB(Integer id) {
        try {
            List<StudentResponse> studentResponses = studentServiceImp.getStudent(id);
            if (studentResponses.isEmpty()) {
                return ApiResponse.<List<StudentResponse>>ok(StudentResponse.class.getSimpleName()).setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(studentResponses);
            } else {
                return ApiResponse.<List<StudentResponse>>ok(StudentResponse.class.getSimpleName())
                        .setData(studentResponses);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-id")
    public ApiResponse<StudentResponse> getAllStudentFromDBById(Integer id) {
        try {
            StudentResponse studentResponses = studentServiceImp.getSudentById(id);
            if (studentResponses == null) {
                return ApiResponse.<StudentResponse>ok(StudentResponse.class.getSimpleName()).setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(null);
            } else {
                return ApiResponse.<StudentResponse>ok(StudentResponse.class.getSimpleName())
                        .setData(studentResponses);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("/update-class-id")
    public ApiResponse<StudentResponse> updateClassID(Integer new_class_id) {

        try {
            Integer user_id = AuthRestController.user_id;
            studentServiceImp.updateClassID(new_class_id, user_id);
            if (new_class_id == null) {
                return ApiResponse.<StudentResponse>setError("Class ID")
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(new StudentResponse(user_id, "kk", "jjjfnld", "@@@"));
            } else {
                return ApiResponse.<StudentResponse>updateSuccess("Class ID")
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(new StudentResponse(user_id, "hello", "jjjfnld", "@@@"))
                        .setMetadata("Update Class ID");
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ApiResponse<StudentRequest> deleteUser() {
        Integer user_id = AuthRestController.user_id;

        studentServiceImp.studentDeleteAccount(user_id);
        return ApiResponse.<StudentRequest>successDelete("student class")
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                .setData(new StudentRequest(user_id))
                .setMetadata("This route just change status to 0");

    }

    @PutMapping("/deactivate-account")
    public ApiResponse<StudentRequest> deactivateAccount() {
        Integer user_id = AuthRestController.user_id;

        studentServiceImp.studentDeactivateAccount(user_id);
        return ApiResponse.<StudentRequest>updateSuccess("student class")
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(new StudentRequest(user_id))
                .setMetadata("This route we user to deactivate account (status: 1)!");

    }
}
