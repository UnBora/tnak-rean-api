package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.SubmittableWork;
import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.*;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkByStudentIdAndClassIdResponse;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkResponse;
import com.kshrd.tnakrean.repository.SubmittedWorkRepository;
import com.kshrd.tnakrean.service.serviceImplementation.SubmittedWorkImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/submittedWork")
public class SubmittedWorkController {
    final SubmittedWorkImpl submittedWorkImpl;
    final SubmittedWorkRepository submittedWorkRepository;

    public SubmittedWorkController(SubmittedWorkImpl submittedWorkImpl, SubmittedWorkRepository submittedWorkRepository) {
        this.submittedWorkImpl = submittedWorkImpl;
        this.submittedWorkRepository = submittedWorkRepository;
    }

    @GetMapping("/get-all")
    ApiResponse<List<SubmittedWorkResponse>> getAllSubmittedWork() {
        try {
            List<SubmittedWorkResponse> submittedWorkResponse = submittedWorkImpl.getAllSubmittedWork();
            if (submittedWorkResponse.isEmpty()) {
                return ApiResponse.<List<SubmittedWorkResponse>>ok(SubmittedWorkResponse.class
                                .getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(submittedWorkResponse);
            }
            return ApiResponse.<List<SubmittedWorkResponse>>ok(SubmittedWorkResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(submittedWorkResponse);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }
    @GetMapping("/get-by-id/{id}")
    ApiResponse<List<SubmittedWorkResponse>> getById(@RequestParam Integer id) {
        try {
            List<SubmittedWorkResponse> submittedWorkResponse = submittedWorkImpl.getById(id);
            if (submittedWorkResponse.isEmpty()) {
                return ApiResponse.<List<SubmittedWorkResponse>>ok(SubmittedWorkResponse.class
                                .getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(submittedWorkResponse);
            }
            return ApiResponse.<List<SubmittedWorkResponse>>ok(SubmittedWorkResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(submittedWorkResponse);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-studentId/{id}")
    ApiResponse<List<SubmittedWorkResponse>> getSubmittedByStudentId(@RequestParam Integer studentId)  throws IllegalStateException {
        if (studentId <= 0) throw new IllegalStateException("studentId cannot be less than 1");
        try {
            List<SubmittedWorkResponse> submittedWorkResponses = submittedWorkImpl.getSubmittedByStudentId(studentId);
            if (submittedWorkResponses.isEmpty()) {
                return ApiResponse.<List<SubmittedWorkResponse>>ok(SubmittedWorkResponse.class
                                .getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
            return ApiResponse.<List<SubmittedWorkResponse>>ok(SubmittedWorkResponse.class
                            .getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                    .setData(submittedWorkResponses);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }
    @GetMapping("get-by-studentId-and-classId")
    ApiResponse<List<SubmittedWorkByStudentIdAndClassIdResponse>> getByStudentIdAndClassId(
            @RequestParam Integer student_id,
            @RequestParam Integer class_id

    ){
        List<SubmittedWorkByStudentIdAndClassIdResponse> submittedWorkResponses = submittedWorkImpl.getByStudentIdAndClassId(student_id,class_id);
        if (submittedWorkResponses.isEmpty()) {
            return ApiResponse.<List<SubmittedWorkByStudentIdAndClassIdResponse>>ok(SubmittedWorkResponse.class
                            .getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
        }
        return ApiResponse.<List<SubmittedWorkByStudentIdAndClassIdResponse>>ok(SubmittedWorkResponse.class
                        .getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                .setData(submittedWorkResponses);
    }

    @PostMapping("/insert-student-work")
    ApiResponse<SubmittedWorkStudentWorkRequest> addSubmittedWork(
            @RequestBody @Valid SubmittedWorkStudentWorkRequest submittedWorkStudentWorkRequest
    ) {
        submittedWorkImpl.addSubmittedWork(submittedWorkStudentWorkRequest);
        return ApiResponse.<SubmittedWorkStudentWorkRequest>ok(SubmittedWorkStudentWorkRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                .setData(submittedWorkStudentWorkRequest);
    }

    @PutMapping("/update-student-work")
    ApiResponse<SubmittedWorkUpdateStudentWorkRequest> updateStudentWork(
            @RequestBody @Valid SubmittedWorkUpdateStudentWorkRequest submittedWorkUpdateStudentWorkRequest
    ) {
        submittedWorkImpl.updateSubmittedWork(submittedWorkUpdateStudentWorkRequest);
        return ApiResponse.<SubmittedWorkUpdateStudentWorkRequest>ok(SubmittedWorkUpdateStudentWorkRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(submittedWorkUpdateStudentWorkRequest);
    }

    @PutMapping("/update-student-result")
    ApiResponse<SubmittedWorkUpdateResultRequest> updateResult(
            @RequestBody @Valid SubmittedWorkUpdateResultRequest submittedWorkUpdateResultRequest
    ) {
        submittedWorkImpl.updateResult(submittedWorkUpdateResultRequest);
        return ApiResponse.<SubmittedWorkUpdateResultRequest>ok(SubmittedWorkUpdateResultRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(submittedWorkUpdateResultRequest);
    }

    @DeleteMapping("/delete-by-Id/{id}")
    ApiResponse<?> deleteSubmittedWorkId(@RequestParam  Integer id) {
        submittedWorkImpl.deleteSubmittedWorkId(id);
        if (id == 0) {
            return ApiResponse.ok(SubmittedWorkResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.DELETE_ERROR.getMessage())
                    .setData(false);
        }
        return ApiResponse.successDelete(SubmittedWorkResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                .setData(true);
    }
    @DeleteMapping("/delete-by-studentId/{id}")
    ApiResponse<Boolean> deleteByStudentId(@RequestParam Integer student_id){
        submittedWorkImpl.deleteByStudentId(student_id);
        if (student_id == 0){
            return ApiResponse.<Boolean>ok("Submitted Work")
                    .setResponseMsg(BaseMessage.Error.DELETE_ERROR.getMessage())
                    .setData(false);
        }
        return ApiResponse.<Boolean>ok("Submitted Work")
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                .setData(true);
    }

    @PutMapping("update-status")
    ApiResponse<SubmittedWorkUpdateStatusRequest> updateStatus(
            @RequestBody @Valid SubmittedWorkUpdateStatusRequest submittedWorkUpdateStatusRequest) {
        submittedWorkImpl.updateStatus(submittedWorkUpdateStatusRequest);
        return ApiResponse.<SubmittedWorkUpdateStatusRequest>ok(SubmittedWorkUpdateStatusRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(submittedWorkUpdateStatusRequest);
    }
}
