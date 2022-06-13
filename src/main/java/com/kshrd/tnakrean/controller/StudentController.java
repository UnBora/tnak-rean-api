package com.kshrd.tnakrean.controller;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.student.request.StudentLeaveClassRequest;
import com.kshrd.tnakrean.model.student.request.StudentRequest;
import com.kshrd.tnakrean.model.student.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.model.student.response.GetStudentByIDResponse;
import com.kshrd.tnakrean.model.student.response.GetAllStudentResponse;
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

    @GetMapping("/getAllStudent")
    public ApiResponse<List<GetAllStudentResponse>> getAllStudentFromDB() {
        try {
            List<GetAllStudentResponse> getAllStudentResponse = studentServiceImp.getAllStudent();
            if (getAllStudentResponse.isEmpty()) {
                return ApiResponse.<List<GetAllStudentResponse>>ok(GetAllStudentResponse.class.getSimpleName()).setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(getAllStudentResponse);
            } else {
                return ApiResponse.<List<GetAllStudentResponse>>ok("Get All Student")
                        .setData(getAllStudentResponse);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/getById")
    public ApiResponse<GetStudentByIDResponse> getAllStudentFromDBById(Integer user_id) {
        try {
            GetStudentByIDResponse getStudentByIDResponses = studentServiceImp.getStudentById(user_id);
            if (getStudentByIDResponses == null) {
                return ApiResponse.<GetStudentByIDResponse>setError(GetAllStudentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            } else {
                return ApiResponse.<GetStudentByIDResponse>ok("Student ID: " + user_id)
                        .setData(getStudentByIDResponses);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }


    @DeleteMapping("/deleteAccount")
    public ApiResponse<StudentRequest> deleteUser() {
        Integer user_id = AuthRestController.user_id;

        studentServiceImp.studentDeleteAccount(user_id);
        return ApiResponse.<StudentRequest>successDelete("student class")
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                .setData(new StudentRequest(user_id));

    }

    @PutMapping("/deactivateAccount")
    public ApiResponse<StudentRequest> deactivateAccount() {
        Integer user_id = AuthRestController.user_id;

        studentServiceImp.studentDeactivateAccount(user_id);
        return ApiResponse.<StudentRequest>updateSuccess("student class")
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(new StudentRequest(user_id));

    }

    @PutMapping("/activateAccount")
    public ApiResponse<StudentRequest> activateAccount() {
        Integer user_id = AuthRestController.user_id;

        studentServiceImp.studentActivateAccount(user_id);
        return ApiResponse.<StudentRequest>updateSuccess("student class")
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(new StudentRequest(user_id));
    }

    @GetMapping("/getByClass-id")
    public ApiResponse<List<GetStudentByClassIDResponse>> getStudentByClassID(Integer id) {
        try {
            List<GetStudentByClassIDResponse> getStudentByClassIDResponses = studentServiceImp.selectStudentByClassID(id);
            if (getStudentByClassIDResponses.isEmpty()) {
                return ApiResponse.<List<GetStudentByClassIDResponse>>setError(GetStudentByClassIDResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(getStudentByClassIDResponses);
            } else {
                return ApiResponse.<List<GetStudentByClassIDResponse>>ok("Get All Student")
                        .setData(getStudentByClassIDResponses);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("leaveClass")
    public ApiResponse<StudentLeaveClassRequest> studentLeaveClass(int classroomId, int classId) {
        try {
            Integer user_id = AuthRestController.user_id;
            studentServiceImp.studentLeaveClassService(user_id, classroomId, classId);
            if (user_id == 0 || classroomId == 0 || classId == 0) {
                return ApiResponse.<StudentLeaveClassRequest>setError("student class")
                        .setResponseMsg(BaseMessage.Error.INSERT_ERROR.getMessage())
                        .setData(new StudentLeaveClassRequest(user_id, classroomId, classId))
                        .setMetadata("some data is null");
            } else {
                return ApiResponse.<StudentLeaveClassRequest>ok("student class")
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(new StudentLeaveClassRequest(user_id, classroomId, classId));
            }

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("insertStudent")
    public ApiResponse<StudentLeaveClassRequest> insertStudentToTableStudent(Integer user_id, int classroomId, int classId) {
        try {
            studentServiceImp.insertStudent(user_id, classroomId, classId);
            if (user_id == 0 || classroomId == 0 || classId == 0) {
                return ApiResponse.<StudentLeaveClassRequest>setError("student class")
                        .setResponseMsg(BaseMessage.Error.INSERT_ERROR.getMessage())
                        .setData(new StudentLeaveClassRequest(user_id, classroomId, classId));
            } else {
                return ApiResponse.<StudentLeaveClassRequest>ok("student class")
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(new StudentLeaveClassRequest(user_id, classroomId, classId));
            }

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

}
