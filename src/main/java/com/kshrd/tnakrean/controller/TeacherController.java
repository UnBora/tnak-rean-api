package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.teacher.response.TeacherResponse;
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

    @GetMapping("/getAllTeacher")
    ApiResponse<List<TeacherResponse>> getAllTeacher(){
        List<TeacherResponse> teacherResponses = teacherImpl.getAllTeacher();
        if (teacherResponses.isEmpty()){
            return ApiResponse.<List<TeacherResponse>>ok(TeacherResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(teacherResponses);
        }
        return ApiResponse.<List<TeacherResponse>>ok(TeacherResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(teacherResponses);
    }

    @GetMapping("/getTeacherByUserId/{id}")
    ApiResponse<TeacherResponse> getTeacherById(Integer user_id){
        TeacherResponse teacherByIdResponse = teacherImpl.getTeacherById(user_id);
      if (teacherByIdResponse == null){
      return ApiResponse.<TeacherResponse>ok(TeacherResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(null);
        }
        return ApiResponse.<TeacherResponse>ok(TeacherResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                .setData(teacherByIdResponse);
    }

    @GetMapping("/getAllClassByTeacherId")
    ApiResponse<?> GetAllClassByTeacherId(){
        return null;
    }
    @GetMapping("/getAllCourseByTeacherId")
    ApiResponse<?> GetAllCourseByTeacherId(){
        return null;
    }

    @GetMapping("/getAllMaterialByCreatedById")
    ApiResponse<?> GetAllMaterialByTeacherId(){
        return null;
    }
}
