package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.classmaterials.response.ScheduleResponse;
import com.kshrd.tnakrean.service.serviceImplementation.TeacherServiceImp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    TeacherServiceImp teacherServiceImp;

    @GetMapping("/getScheduleByTeacherId")
    ApiResponse<ScheduleResponse> getScheduleByTeacherId(Integer id) {


        return null;
    }


}
