package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
import com.kshrd.tnakrean.model.classmaterials.response.ClassroomResponse;
import com.kshrd.tnakrean.model.user.request.TeacherRequest;
import com.kshrd.tnakrean.model.user.request.TeacherStatusRequest;
import com.kshrd.tnakrean.model.user.response.TeacherByClassAndClassroomResponse;
import com.kshrd.tnakrean.model.user.response.TeacherResponse;
import com.kshrd.tnakrean.service.serviceImplementation.TeacherImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/teacher")
public class TeacherController {
    final TeacherImpl teacherImpl;

    public TeacherController(TeacherImpl teacherImpl) {
        this.teacherImpl = teacherImpl;
    }

    @GetMapping("/get-all")
    ApiResponse<List<TeacherResponse>> getAllTeacher() {
        List<TeacherResponse> teacherResponses = teacherImpl.getAllTeacher();
        if (teacherResponses.isEmpty()) {
            return ApiResponse.<List<TeacherResponse>>ok(TeacherResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(teacherResponses);
        }
        return ApiResponse.<List<TeacherResponse>>ok(TeacherResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(teacherResponses);
    }
    @GetMapping("/get-by-classId-and-classroomId/{class_id}/{classroom_id}")
    ApiResponse<List<TeacherByClassAndClassroomResponse>> getByClassAndClassrooms(
            @RequestParam Integer class_id,
            @RequestParam Integer classroom_id
    ) {
        Integer user_id = AuthRestController.user_id;
        List<TeacherByClassAndClassroomResponse> teacherResponses = teacherImpl.getByClassAndClassrooms(user_id,class_id, classroom_id);
        if (teacherResponses.isEmpty()) {
            return ApiResponse.<List<TeacherByClassAndClassroomResponse>>ok(TeacherByClassAndClassroomResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(teacherResponses);
        }
        return ApiResponse.<List<TeacherByClassAndClassroomResponse>>ok(TeacherByClassAndClassroomResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(teacherResponses);
    }


    @GetMapping("/get-by-UserId/{id}")
    ApiResponse<TeacherResponse> getTeacherById(Integer user_id) {
        TeacherResponse teacherByIdResponse = teacherImpl.getTeacherById(user_id);
        if (teacherByIdResponse == null) {
            return ApiResponse.<TeacherResponse>ok(TeacherResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(null);
        }
        return ApiResponse.<TeacherResponse>ok(TeacherResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                .setData(teacherByIdResponse);
    }
    @DeleteMapping("/deleteAccount")
    public ApiResponse<TeacherRequest> teacherDeleteAccount() {
        Integer user_id = AuthRestController.user_id;

        teacherImpl.teacherDeleteAccount(user_id);
        return ApiResponse.<TeacherRequest>successDelete("Teacher")
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                .setData(new TeacherRequest(user_id));
    }

    @PutMapping("/deactivateAccount")
    public ApiResponse<TeacherRequest> deactivateTeacherAccount() {
        Integer user_id = AuthRestController.user_id;

        teacherImpl.deactivateTeacherAccount(user_id);
        return ApiResponse.<TeacherRequest>updateSuccess("Teacher")
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(new TeacherRequest(user_id));
    }
    @PutMapping("/activateAccount")
    public ApiResponse<TeacherRequest> activateTeacherAccount() {
        Integer user_id = AuthRestController.user_id;

        teacherImpl.activateTeacherAccount(user_id);
        return ApiResponse.<TeacherRequest>updateSuccess("Teacher")
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(new TeacherRequest(user_id));
    }
}
