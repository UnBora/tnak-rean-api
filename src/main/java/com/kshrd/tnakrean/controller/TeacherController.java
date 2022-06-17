package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.teacher.request.TeacherRequest;
import com.kshrd.tnakrean.model.teacher.request.TeacherStatusRequest;
import com.kshrd.tnakrean.model.teacher.response.TeacherResponse;
import com.kshrd.tnakrean.service.serviceImplementation.TeacherImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/teacher")
public class TeacherController {
    final TeacherImpl teacherImpl;

    public TeacherController(TeacherImpl teacherImpl) {
        this.teacherImpl = teacherImpl;
    }

    @GetMapping("/getAllTeacher")
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

    @GetMapping("/getTeacherByUserId/{id}")
    ApiResponse<TeacherResponse> getTeacherById(@Param("user_id") Integer user_id) {
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

    @PutMapping("/teacherUpdateStatus")
    ApiResponse<TeacherStatusRequest> teacherStatus(
            @RequestBody TeacherStatusRequest teacherStatusRequest
    ) {
        teacherImpl.teacherStatus(teacherStatusRequest);
        return ApiResponse.<TeacherStatusRequest>ok(TeacherStatusRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(teacherStatusRequest);
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
