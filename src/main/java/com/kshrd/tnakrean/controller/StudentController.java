package com.kshrd.tnakrean.controller;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.user.request.UserUpdateRequest;
import com.kshrd.tnakrean.model.user.request.StudentLeaveClassRequest;
import com.kshrd.tnakrean.model.user.request.UserActivateAccountRequest;
import com.kshrd.tnakrean.model.user.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.model.user.response.GetStudentByIDResponse;
import com.kshrd.tnakrean.model.user.response.GetAllStudentResponse;
import com.kshrd.tnakrean.model.user.response.StudentRequestClassResponse;
import com.kshrd.tnakrean.repository.StudentRepository;
import com.kshrd.tnakrean.service.serviceImplementation.StudentServiceImp;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@SecurityRequirement(name = "bearerAuth")
public class StudentController {

    final StudentRepository studentRepository;
    final StudentServiceImp studentServiceImp;

    @Autowired
    public StudentController(StudentRepository studentRepository, StudentServiceImp studentServiceImp) {
        this.studentRepository = studentRepository;
        this.studentServiceImp = studentServiceImp;
    }

    @GetMapping("/get-all-student")
    public ApiResponse<List<GetAllStudentResponse>> getAllStudentFromDB() {
        try {
            List<GetAllStudentResponse> getAllStudentResponse = studentServiceImp.getAllStudent();
            if (getAllStudentResponse.isEmpty()) {
                return ApiResponse.<List<GetAllStudentResponse>>ok(GetAllStudentResponse.class.getSimpleName()).setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(getAllStudentResponse);
            } else {
                return ApiResponse.<List<GetAllStudentResponse>>ok(GetAllStudentResponse.class.getSimpleName())
                        .setData(getAllStudentResponse);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-id")
    public ApiResponse<GetStudentByIDResponse> getAllStudentFromDBById() {
        try {
            Integer user_id =AuthRestController.user_id;
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


    @DeleteMapping("/delete-account")
    public ApiResponse<UserActivateAccountRequest> deleteUser() {
        Integer user_id = AuthRestController.user_id;

        studentServiceImp.studentDeleteAccount(user_id);
        return ApiResponse.<UserActivateAccountRequest>successDelete("student class")
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                .setData(new UserActivateAccountRequest(user_id));

    }

    @PutMapping("/deactivate-account")
    public ApiResponse<UserActivateAccountRequest> deactivateAccount() {
        Integer user_id = AuthRestController.user_id;

        studentServiceImp.studentDeactivateAccount(user_id);
        return ApiResponse.<UserActivateAccountRequest>updateSuccess(UserActivateAccountRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(new UserActivateAccountRequest(user_id));

    }

    @PutMapping("/activate-account")
    public ApiResponse<UserActivateAccountRequest> activateAccount() {
        Integer user_id = AuthRestController.user_id;

        studentServiceImp.studentActivateAccount(user_id);
        return ApiResponse.<UserActivateAccountRequest>updateSuccess(UserActivateAccountRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(new UserActivateAccountRequest(user_id));
    }

    @GetMapping("/get-student-by-class-and-classroom-id")
    public ApiResponse<List<GetStudentByClassIDResponse>> getStudentByClassID(Integer id, Integer classroom_id) {
        try {
            List<GetStudentByClassIDResponse> getStudentByClassIDResponses = studentServiceImp.selectStudentByClassID(id,classroom_id);
            if (getStudentByClassIDResponses.isEmpty()) {
                return ApiResponse.<List<GetStudentByClassIDResponse>>setError(GetStudentByClassIDResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(getStudentByClassIDResponses);
            } else {
                return ApiResponse.<List<GetStudentByClassIDResponse>>ok(GetStudentByClassIDResponse.class.getSimpleName())
                        .setData(getStudentByClassIDResponses);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("leave-class")
    public ApiResponse<StudentLeaveClassRequest> studentLeaveClass(int classroomId, int classId) {
        try {
            Integer user_id = AuthRestController.user_id;
            studentServiceImp.studentLeaveClassService(user_id, classroomId, classId);
            if (user_id == 0 || classroomId == 0 || classId == 0) {
                return ApiResponse.<StudentLeaveClassRequest>setError("student class")
                        .setResponseMsg(BaseMessage.Error.INSERT_ERROR.getMessage())
                        .setData(new StudentLeaveClassRequest(user_id, classroomId, classId));
            } else {
                return ApiResponse.<StudentLeaveClassRequest>ok("student class")
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(new StudentLeaveClassRequest(user_id, classroomId, classId));
            }

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("insert-student")
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

    @PutMapping("update-profile")
    public ApiResponse<UserUpdateRequest> studentUpdateProfile(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        try {
            Integer user_id = AuthRestController.user_id;
            studentServiceImp.updateprofileByID(user_id, userUpdateRequest.getName(), userUpdateRequest.getUsername(),userUpdateRequest.getImg(), userUpdateRequest.getGender());
                return ApiResponse.<UserUpdateRequest>ok(UserUpdateRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(new UserUpdateRequest(user_id, userUpdateRequest.getName(), userUpdateRequest.getUsername(),userUpdateRequest.getEmail(),userUpdateRequest.getImg(), userUpdateRequest.getGender()));

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
    @GetMapping("get-student-request")
    public ApiResponse<List<StudentRequestClassResponse>> getRequestClass(
            @RequestParam @Min(value = 1) Integer classroom_id,
            @RequestParam @Min(value = 1) Integer class_id
    ){  List<StudentRequestClassResponse> studentRequestClassResponse = studentServiceImp.getRequestClass(classroom_id,class_id);
        try {
            if (studentRequestClassResponse.isEmpty()) {
                return ApiResponse.<List<StudentRequestClassResponse>>notFound(StudentRequestClassResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
            return ApiResponse.<List<StudentRequestClassResponse>>ok(StudentRequestClassResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(studentRequestClassResponse);
        } catch (Exception e){
            return ApiResponse.setError(e.getMessage());
        }
    }
}
