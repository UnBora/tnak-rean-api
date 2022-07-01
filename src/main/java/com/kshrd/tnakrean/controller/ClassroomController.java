package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.GetClassRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassroomRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassroomUpdateResponse;
import com.kshrd.tnakrean.model.classmaterials.response.ClassroomResponse;
import com.kshrd.tnakrean.model.classmaterials.response.GetClassByTeacherIdResponse;

import com.kshrd.tnakrean.model.user.response.GetAllStudentResponse;
import com.kshrd.tnakrean.model.user.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.repository.ClassroomRepository;
import com.kshrd.tnakrean.service.serviceImplementation.ClassroomServiceImp;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/classroom")
@SecurityRequirement(name = "bearerAuth")
public class ClassroomController {

    final ClassroomServiceImp classroomServiceImp;
    final ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomController(ClassroomServiceImp classroomServiceImp, ClassroomRepository classroomRepository) {
        this.classroomServiceImp = classroomServiceImp;
        this.classroomRepository = classroomRepository;
    }

    @GetMapping("/get-all-classroom")
    public ApiResponse<List<ClassroomResponse>> getAllClassroom() {
        try {
            List<ClassroomResponse> classroomResponses = classroomServiceImp.getAllClassroom();
            if (classroomResponses.isEmpty()) {
                return ApiResponse.<List<ClassroomResponse>>ok(GetClassRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classroomResponses);
            } else {
                return ApiResponse.<List<ClassroomResponse>>ok(ClassroomResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classroomResponses);
            }

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-classroom-by-id")
    public ApiResponse<ClassroomResponse> getClassroomById(
            @RequestParam @Min(value = 1, message = "{validation.id.notNegative}") Integer classroomId) {
        try {
            Boolean classroomID = classroomRepository.checkClassroomByID(classroomId);
            if (classroomID == null) {
                return ApiResponse.<ClassroomResponse>badRequest(ClassroomResponse.class.getSimpleName())
                        .setResponseMsg("The Classroom ID cannot not null");
            } else if (classroomID.equals(false)) {
                return ApiResponse.<ClassroomResponse>badRequest(ClassroomResponse.class.getSimpleName())
                        .setResponseMsg("The Classroom ID:" + classroomId + " does not have!");
            } else {
                ClassroomResponse classroomResponse = classroomServiceImp.getClassroomByID(classroomId);
                return ApiResponse.<ClassroomResponse>ok(ClassroomResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                        .setData(classroomResponse);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("/insert-classroom")
    public ApiResponse<ClassroomRequest> insertClassroom(@RequestBody @Valid ClassroomRequest classroomRequest) {
        try {
            Integer createdby = AuthRestController.user_id;
            String dec = classroomRequest.getDes(), name = classroomRequest.getName();
            Boolean classroomName= classroomRepository.checkIfClassExistsDuplecateClassName(name.toUpperCase());

            if (!createdby.equals(0)) {
                if (classroomRequest == null) {
                    return ApiResponse.<ClassroomRequest>setError(ClassroomRequest.class.getSimpleName())
                            .setResponseMsg(BaseMessage.Error.INSERT_ERROR.getMessage());
                } else if (classroomName.equals(true)) {
                    return ApiResponse.<ClassroomRequest>badRequest(ClassroomRequest.class.getSimpleName())
                            .setResponseMsg("The classroom name already exists!");
                } else {
                    classroomServiceImp.insertClassroom( createdby, name.toUpperCase(), dec);
                    return ApiResponse.<ClassroomRequest>ok(ClassroomRequest.class.getSimpleName())
                            .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                            .setData(new ClassroomRequest( name.toUpperCase(), dec));
                }
            }else {
                return ApiResponse.<ClassroomRequest>unAuthorized(ClassroomRequest.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-classroom-by-teacher-id")
    public ApiResponse<List<GetClassByTeacherIdResponse>> getClassByTeacherId() {
        Integer userId = AuthRestController.user_id;
        GetClassByTeacherIdResponse obj = new GetClassByTeacherIdResponse();
        Integer classId = obj.getClass_id(), classroomId = obj.getClassroom_id();
        String teacher = obj.getTeacher_name(), className = obj.getClass_name();
        try {
            if (!userId.equals(0)) {
                List<GetClassByTeacherIdResponse> getClassByTeacherIdResponses = classroomServiceImp.getClassByTeacherId(classId, classroomId, teacher, className, userId);
                if (getClassByTeacherIdResponses.isEmpty()) {
                    return ApiResponse.<List<GetClassByTeacherIdResponse>>setError(GetStudentByClassIDResponse.class.getSimpleName())
                            .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                            .setData(getClassByTeacherIdResponses);
                } else {
                    return ApiResponse.<List<GetClassByTeacherIdResponse>>ok(GetClassByTeacherIdResponse.class.getSimpleName())
                            .setData(getClassByTeacherIdResponses);
                }
            } else {
                return ApiResponse.<List<GetClassByTeacherIdResponse>>unAuthorized(GetClassByTeacherIdResponse.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("/update-classroom")
    public ApiResponse<ClassroomUpdateResponse> updateClassroom(ClassroomUpdateResponse classroomUpdateResponse) {
        try {
            Boolean a = classroomRepository.checkIfClassExists(classroomUpdateResponse.getClassroom_id(), classroomUpdateResponse.getCreated_by());
            if (classroomUpdateResponse.equals(null)) {
                return ApiResponse.<ClassroomUpdateResponse>setError(ClassroomUpdateResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage());
            } else if (a.equals(false)) {
                return ApiResponse.<ClassroomUpdateResponse>setError(ClassroomUpdateResponse.class.getSimpleName())
                        .setResponseMsg("Your classroom ID and classID Not Matched!");
            } else {
                classroomServiceImp.updateClassroom(classroomUpdateResponse.getClassroom_id(), classroomUpdateResponse.getCreated_by(), classroomUpdateResponse.getName(), classroomUpdateResponse.getDes());
                return ApiResponse
                        .<ClassroomUpdateResponse>updateSuccess(ClassroomUpdateResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(new ClassroomUpdateResponse(classroomUpdateResponse.getClassroom_id(), classroomUpdateResponse.getCreated_by(), classroomUpdateResponse.getName(), classroomUpdateResponse.getDes()));
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }


    }
}
