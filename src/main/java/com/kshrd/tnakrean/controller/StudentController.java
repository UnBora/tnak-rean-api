package com.kshrd.tnakrean.controller;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.response.ClassResponse;
import com.kshrd.tnakrean.model.user.request.StudentInsertRequest;
import com.kshrd.tnakrean.model.user.request.StudentLeaveClassRequest;
import com.kshrd.tnakrean.model.user.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.model.user.response.GetStudentByIDResponse;
import com.kshrd.tnakrean.model.user.response.GetAllStudentResponse;
import com.kshrd.tnakrean.model.user.response.StudentRequestClassResponse;
import com.kshrd.tnakrean.repository.OneSignalPushNotificationRepository;
import com.kshrd.tnakrean.repository.StudentRepository;
import com.kshrd.tnakrean.service.serviceImplementation.StudentServiceImp;
import com.kshrd.tnakrean.service.serviceInter.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
public class StudentController {
    final StudentRepository studentRepository;
    final StudentServiceImp studentServiceImp;
    final EmailService emailService;
    final OneSignalPushNotificationRepository repository;

    @Autowired
    public StudentController(StudentRepository studentRepository, StudentServiceImp studentServiceImp, EmailService emailService, OneSignalPushNotificationRepository repository) {
        this.studentRepository = studentRepository;
        this.studentServiceImp = studentServiceImp;
        this.emailService = emailService;
        this.repository = repository;
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

    @GetMapping("/get-student-by-classId")
    public ApiResponse<List<GetStudentByClassIDResponse>> getStudentByClassID(
            @RequestParam @Min(value = 1, message = "{validation.classId.notNegative}") Integer class_id){
        try {
            Boolean checkClassIDAcdClassroomID = studentRepository.checkIfStudentclassIDClassroomIDExists(class_id);
            if (checkClassIDAcdClassroomID.equals(false)) {
                return ApiResponse.<List<GetStudentByClassIDResponse>>notFound(GetStudentByClassIDResponse.class.getSimpleName())
                        .setResponseMsg("Your classID:" + class_id + " not found!");
            } else {
                List<GetStudentByClassIDResponse> getStudentByClassIDResponses = studentServiceImp.selectStudentByClassID(class_id);
                return ApiResponse.<List<GetStudentByClassIDResponse>>ok(GetStudentByClassIDResponse.class.getSimpleName())
                        .setData(getStudentByClassIDResponses);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
    @PutMapping("leave-class")
    public ApiResponse<StudentLeaveClassRequest> studentLeaveClass(
            @RequestParam @Min(value = 1, message = "{validation.classroomId.notNegative}") Integer classroomId,
            @RequestParam @Min(value = 1, message = "{validation.classId.notNegative}") Integer classId) {
        try {
            Integer user_id = AuthRestController.user_id;
            if (!user_id.equals(0)) {
                Boolean checkId = studentRepository.checkIfStudentExists(user_id, classroomId, classId);
                if (checkId.equals(false)) {
                    return ApiResponse.<StudentLeaveClassRequest>notFound(StudentLeaveClassRequest.class.getSimpleName())
                            .setResponseMsg("Your classID and ClassroomID not Matched!")
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
            @RequestParam @Min(value = 1, message = "{validation.id.notNegative}") Integer user_id) {
        GetStudentByIDResponse student = studentRepository.getStudentDetailById(user_id);
        System.out.println(student);
        try {
            if (student == null) {
                return ApiResponse.<StudentInsertRequest>notFound(StudentInsertRequest.class.getSimpleName())
                        .setResponseMsg("User ID: " + user_id + " Doesn't have in Table User!");
            } else {
                Integer roleID = studentRepository.checkUserRole(user_id);
                if (roleID.equals(2)) {
                    return ApiResponse.<StudentInsertRequest>badRequest(StudentInsertRequest.class.getSimpleName())
                            .setResponseMsg("Can not request to join the class, This user is teacher role");
                } else if (student.getStatus() != -1) {
                    return ApiResponse.<StudentInsertRequest>badRequest(StudentInsertRequest.class.getSimpleName())
                            .setResponseMsg("This student is already accepted");
                } else {
                    studentServiceImp.insertStudent(user_id);
                    ClassResponse classResponse = repository.getClassById(student.getClass_id(), student.getClassRoom_id());
                    System.out.println(student.getEmail());
                    emailService.send("You Have Been Accepted", "You have been accepted into " + classResponse.getClass_name() + " of " + classResponse.getClassRoomName(), student.getEmail());
                    return ApiResponse.<StudentInsertRequest>ok(StudentInsertRequest.class.getSimpleName())
                            .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage());
                }
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-student-request-by-classId")
    public ApiResponse<List<StudentRequestClassResponse>> getRequestClass(
            @RequestParam @Min(value = 1) Integer classroom_id,
            @RequestParam @Min(value = 1) Integer class_id
    ) {
        List<StudentRequestClassResponse> studentRequestClassResponse = studentServiceImp.getRequestClass(classroom_id, class_id);
        try {
            if (studentRequestClassResponse.isEmpty()) {
                return ApiResponse.<List<StudentRequestClassResponse>>notFound(StudentRequestClassResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
            return ApiResponse.<List<StudentRequestClassResponse>>ok(StudentRequestClassResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(studentRequestClassResponse);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
