package com.kshrd.tnakrean.controller;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.student.response.StudentResponse;
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
            if (studentResponses.equals(0)) {
                return ApiResponse.<StudentResponse>ok(StudentResponse.class.getSimpleName()).setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(studentResponses);
            } else {
                return ApiResponse.<StudentResponse>ok(StudentResponse.class.getSimpleName())
                        .setData(studentResponses);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("/update-class-id")
    public ApiResponse<StudentResponse> updateClassID(Integer id){

        try {
            StudentResponse studentResponse= studentServiceImp.updateClassID(id);
            if (studentResponse == null) {
                return ApiResponse.<StudentResponse>ok(StudentResponse.class.getSimpleName()).setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(studentResponse);
            } else {
                return ApiResponse.<StudentResponse>ok(StudentResponse.class.getSimpleName())
                        .setData(studentResponse);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
