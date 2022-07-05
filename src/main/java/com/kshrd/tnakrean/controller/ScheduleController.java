package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.response.ScheduleResponse;
import com.kshrd.tnakrean.service.serviceImplementation.ScheduleServiceImp;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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

    @GetMapping("/get-Schedule-By-TeacherId")
    ApiResponse<List<ScheduleResponse>> getScheduleByTeacherId(@RequestParam("id") @Min(value = 1) Integer id) {
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

    @GetMapping("/get-schedule-by-teacherUserId-dayId-classroomId")
    ApiResponse<List<ScheduleResponse>> getScheduleByTeacherDayClassClassroom(
            @RequestParam @Min(value = 1) Integer classroomId,
            @RequestParam @Min(value = 1) Integer dayId) {
        Integer user_id = AuthRestController.user_id;
        List<ScheduleResponse> responses = scheduleServiceImp.getScheduleByTeacherDayClassClassroom(classroomId, dayId,user_id);
        try {
            if (user_id == 0){
                return ApiResponse.unAuthorized("unAuthorized");
            }
            else if (!responses.isEmpty()) {
                return ApiResponse.<List<ScheduleResponse>>ok(ScheduleResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                        .setData(responses);
            }
            return ApiResponse.<List<ScheduleResponse>>notFound(ScheduleResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-schedule-by-studentUserId")
    ApiResponse<List<ScheduleResponse>> getScheduleByStudentUserId() {
        Integer user_id = AuthRestController.user_id;
        List<ScheduleResponse> responses = scheduleServiceImp.getScheduleByStudentUserId(user_id);
        try {
            if (user_id == 0){
                return ApiResponse.unAuthorized("unAuthorized");
            }
            else if (!responses.isEmpty()) {
                return ApiResponse.<List<ScheduleResponse>>ok(ScheduleResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                        .setData(responses);
            }
            return ApiResponse.<List<ScheduleResponse>>notFound(ScheduleResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
