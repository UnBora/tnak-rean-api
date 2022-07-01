package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.response.ScheduleResponse;
import com.kshrd.tnakrean.service.serviceImplementation.ScheduleServiceImp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule")
@CrossOrigin(origins = "*")
public class ScheduleController {
    final
    ScheduleServiceImp scheduleServiceImp;

    public ScheduleController(ScheduleServiceImp scheduleServiceImp) {
        this.scheduleServiceImp = scheduleServiceImp;
    }

    @GetMapping("/getScheduleByTeacherId/{id}")
    ApiResponse<List<ScheduleResponse>> getScheduleByTeacherId(@RequestParam("id") Integer id) {
        List<ScheduleResponse> responses = scheduleServiceImp.getScheduleByTeacherId(id);
        try {
            if (!responses.isEmpty()) {
                return ApiResponse.<List<ScheduleResponse>>ok(ScheduleResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                        .setData(responses);
            } else {
                return ApiResponse.<List<ScheduleResponse>>notFound(ScheduleResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/getScheduleByClassId/{id}")
    ApiResponse<List<ScheduleResponse>> getScheduleByClassId(@RequestParam("id") Integer id) {
        List<ScheduleResponse> responses = scheduleServiceImp.getScheduleByClassId(id);
        try {
            if (!responses.isEmpty()) {
                return ApiResponse.<List<ScheduleResponse>>ok(ScheduleResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                        .setData(responses);
            } else {
                return ApiResponse.<List<ScheduleResponse>>notFound(ScheduleResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }


}
