package com.kshrd.tnakrean.controller;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.user.request.StudentInsertRequest;
import com.kshrd.tnakrean.model.user.request.UserUpdateRequest;
import com.kshrd.tnakrean.model.user.request.StudentLeaveClassRequest;
import com.kshrd.tnakrean.model.user.request.UserActivateAccountRequest;
import com.kshrd.tnakrean.model.user.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.model.user.response.GetStudentByIDResponse;
import com.kshrd.tnakrean.model.user.response.GetAllStudentResponse;
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
            Integer user_id = AuthRestController.user_id;
            GetStudentByIDResponse getStudentByIDResponses = studentServiceImp.getStudentById(user_id);
            if (!user_id.equals(0)) {
                if (getStudentByIDResponses == null) {
                    return ApiResponse.<GetStudentByIDResponse>setError(GetStudentByIDResponse.class.getSimpleName())
                            .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
                } else {
                    return ApiResponse.<GetStudentByIDResponse>ok(GetStudentByIDResponse.class.getSimpleName())
                            .setData(getStudentByIDResponses);
                }
            } else {
                return ApiResponse.<GetStudentByIDResponse>unAuthorized(GetStudentByIDResponse.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-student-by-class-and-classroom-id")
    public ApiResponse<List<GetStudentByClassIDResponse>> getStudentByClassID(
            @Min(value = 1, message = "{validation.classId.notNegative}") Integer id,
            @Min(value = 1, message = "{validation.classroomId.notNegative}") Integer classroom_id) {
        try {
            List<GetStudentByClassIDResponse> getStudentByClassIDResponses = studentServiceImp.selectStudentByClassID(id, classroom_id);
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
    public ApiResponse<StudentLeaveClassRequest> studentLeaveClass(
            @Min(value = 1, message = "{validation.classroomId.notNegative}") Integer classroomId,
            @Min(value = 1, message = "{validation.classId.notNegative}") Integer classId) {
        try {
            Integer user_id = AuthRestController.user_id;
            if (!user_id.equals(0)) {
                Boolean checkId=studentRepository.checkIfClassExists(user_id,classroomId,classId);
                if (checkId.equals(false)) {
                    return ApiResponse.<StudentLeaveClassRequest>notFound(StudentLeaveClassRequest.class.getSimpleName())
                            .setResponseMsg("ClassID and ClassroomID not Matched")
                            .setData(new StudentLeaveClassRequest(user_id, classroomId, classId));
                } else {
                    studentServiceImp.studentLeaveClassService(user_id, classroomId, classId);
                    return ApiResponse.<StudentLeaveClassRequest>ok(StudentLeaveClassRequest.class.getSimpleName())
                            .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                            .setData(new StudentLeaveClassRequest(user_id, classroomId, classId));
                }
            } else {
                return ApiResponse.<StudentLeaveClassRequest>unAuthorized(StudentLeaveClassRequest.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("accept-student")
    public ApiResponse<StudentInsertRequest> insertStudentToTableStudent(
            @Min(value = 1, message = "{validation.id.notNegative}") Integer user_id,
            @Min(value = 1, message = "{validation.classroomId.notNegative}") Integer classroomId,
            @Min(value = 1, message = "{validation.classId.notNegative}") Integer classId) {
        try {
            if (user_id == 0 || classroomId == 0 || classId == 0) {
                return ApiResponse.<StudentInsertRequest>setError("student class")
                        .setResponseMsg(BaseMessage.Error.INSERT_ERROR.getMessage())
                        .setData(new StudentInsertRequest(user_id, classroomId, classId));
            } else {
                studentServiceImp.insertStudent(user_id, classroomId, classId);
                return ApiResponse.<StudentInsertRequest>ok("student class")
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(new StudentInsertRequest(user_id, classroomId, classId));
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }


}
