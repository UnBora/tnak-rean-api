package com.kshrd.tnakrean.controller;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.student.response.StudentResponse;
import com.kshrd.tnakrean.repository.StudentRepository;
import com.kshrd.tnakrean.service.serviceImplementation.StudentServiceImp;
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

    @PostMapping("/update-class-id")
    public ApiResponse<StudentResponse> updateClassID(@RequestBody Integer new_class_id) {

        System.out.println("user_id: " + AuthRestController.user_id);
        try {
            Integer user_id = AuthRestController.user_id;
            studentServiceImp.updateClassID(new_class_id, user_id);
//            if (new_class_id >4) {
//                return ApiResponse.<StudentResponse>setError("Class ID")
//                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
//            } else {
                return ApiResponse.<StudentResponse>updateSuccess("Class ID")
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage());
//            }

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
