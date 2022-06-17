package com.kshrd.tnakrean.controller;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.user.response.TeacherResponse;
import com.kshrd.tnakrean.service.serviceImplementation.TeacherServiceImp;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/teacher")
public class TeacherRestController {
    final TeacherServiceImp teacherServiceImp;


    public TeacherRestController(TeacherServiceImp teacherServiceImp) {
        this.teacherServiceImp = teacherServiceImp;
    }

    @GetMapping("/getTeacherById")
    ApiResponse<TeacherResponse> getTeacherById(Integer id) {
        TeacherResponse teacherResponse = teacherServiceImp.getTeacherById(id);
        try {
            if (teacherResponse != null) {
                return ApiResponse.<TeacherResponse>ok(TeacherResponse.class.getSimpleName())
                        .setData(teacherResponse)
                        .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage());
            } else {
                return ApiResponse.<TeacherResponse>notFound(TeacherResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }



    
}
