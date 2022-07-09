package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.user.request.TeacherRemoveStudentByIDResponse;
import com.kshrd.tnakrean.model.user.response.TeacherByClassAndClassroomResponse;
import com.kshrd.tnakrean.model.user.response.TeacherRemoveStudentByID;
import com.kshrd.tnakrean.model.user.response.TeacherResponse;
import com.kshrd.tnakrean.repository.TeacherRepository;
import com.kshrd.tnakrean.service.serviceImplementation.TeacherImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
public class TeacherController {
    final TeacherImpl teacherImpl;
    final TeacherRepository teacherRepository;

    public TeacherController(TeacherImpl teacherImpl, TeacherRepository teacherRepository) {
        this.teacherImpl = teacherImpl;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping("/get-all")
    ApiResponse<List<TeacherResponse>> getAllTeacher() {
        List<TeacherResponse> teacherResponses = teacherImpl.getAllTeacher();
        if (teacherResponses.isEmpty()) {
            return ApiResponse.<List<TeacherResponse>>notFound(TeacherResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(teacherResponses);
        }
        return ApiResponse.<List<TeacherResponse>>ok(TeacherResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(teacherResponses);
    }
    @GetMapping("/get-by-classId-and-classroomId")
    ApiResponse<List<TeacherByClassAndClassroomResponse>> getByClassAndClassrooms(
            @RequestParam @Min(value = 1) Integer class_id,
            @RequestParam @Min(value = 1) Integer classroom_id
    ) {
        Integer user_id = AuthRestController.user_id;
        List<TeacherByClassAndClassroomResponse> teacherResponses = teacherImpl.getByClassAndClassrooms(user_id,class_id, classroom_id);
        if (teacherResponses.isEmpty()) {
            return ApiResponse.<List<TeacherByClassAndClassroomResponse>>notFound(TeacherByClassAndClassroomResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(teacherResponses);
        }
        return ApiResponse.<List<TeacherByClassAndClassroomResponse>>ok(TeacherByClassAndClassroomResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(teacherResponses);
    }

    @GetMapping("/get-by-teacherUserId")
    ApiResponse<TeacherResponse> getTeacherById() {
        Integer user_id = AuthRestController.user_id;
        TeacherResponse teacherByIdResponse = teacherImpl.getTeacherById(user_id);
        if (user_id == 0) {
            return ApiResponse.<TeacherResponse>unAuthorized(TeacherResponse.class.getSimpleName())
                    .setResponseMsg("Unauthorized");
        }
        return ApiResponse.<TeacherResponse>ok(TeacherResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                .setData(teacherByIdResponse);
    }

    @DeleteMapping("/teacher-remove-student-by-id")
    ApiResponse<TeacherRemoveStudentByID> TeacherRemoveStudentByID(
            @RequestBody TeacherRemoveStudentByIDResponse teacherRemoveStudentByIDResponse) {
        try {
            Integer user_id = AuthRestController.user_id;
            if (!user_id.equals(0)) {
                Boolean checkId = teacherRepository.checkIfStudentExists(teacherRemoveStudentByIDResponse.getUser_id(), teacherRemoveStudentByIDResponse.getClassroom_id(), teacherRemoveStudentByIDResponse.getClass_id());
                String studentName= teacherRepository.getStudentName(teacherRemoveStudentByIDResponse.getUser_id());
                String className= teacherRepository.getClassName(teacherRemoveStudentByIDResponse.getClass_id());
                if (checkId.equals(false)) {
                    return ApiResponse.<TeacherRemoveStudentByID>notFound(TeacherRemoveStudentByID.class.getSimpleName())
                            .setResponseMsg("Your StudentID:"+teacherRemoveStudentByIDResponse.getUser_id()+" classID and ClassroomID not Matched!")
                            .setData(new TeacherRemoveStudentByID (teacherRemoveStudentByIDResponse.getUser_id(), studentName, className));
                } else {
                    teacherRepository.removeStudentById(teacherRemoveStudentByIDResponse.getUser_id(), teacherRemoveStudentByIDResponse.getClassroom_id(), teacherRemoveStudentByIDResponse.getClass_id());
                    return ApiResponse.<TeacherRemoveStudentByID>ok(TeacherRemoveStudentByID.class.getSimpleName())
                            .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                            .setData(new TeacherRemoveStudentByID (teacherRemoveStudentByIDResponse.getUser_id(), studentName, className));
                }
            } else {
                return ApiResponse.<TeacherRemoveStudentByID>unAuthorized(TeacherRemoveStudentByID.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

}
