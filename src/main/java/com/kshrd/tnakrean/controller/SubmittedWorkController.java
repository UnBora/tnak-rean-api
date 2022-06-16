package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.submittedWork.request.*;
import com.kshrd.tnakrean.model.submittedWork.response.SubmittedWorkResponse;
import com.kshrd.tnakrean.repository.SubmittedWorkRepository;
import com.kshrd.tnakrean.service.serviceImplementation.SubmittedWorkImpl;
import org.springframework.web.bind.annotation.*;

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
        List<SubmittedWorkResponse> submittedWorkResponse = submittedWorkImpl.getAllSubmittedWork();
        if (submittedWorkResponse.isEmpty()) {
            return ApiResponse.<List<SubmittedWorkResponse>>setError(SubmittedWorkResponse.class
                            .getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(submittedWorkResponse);
        }
        return ApiResponse.<List<SubmittedWorkResponse>>ok(SubmittedWorkResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(submittedWorkResponse);
    }

    @GetMapping("get-by-studentId")
    ApiResponse<List<SubmittedWorkResponse>> getSubmittedByStudentId(int studentId) {
        List<SubmittedWorkResponse> submittedWorkResponses = submittedWorkImpl.getSubmittedByStudentId(studentId);
        if (submittedWorkResponses.isEmpty()) {
            return ApiResponse.<List<SubmittedWorkResponse>>setError(SubmittedWorkResponse.class
                            .getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
        }
        return ApiResponse.<List<SubmittedWorkResponse>>ok(SubmittedWorkResponse.class
                        .getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                .setData(submittedWorkResponses);
    }

    @PostMapping("/insert-student-work")
    ApiResponse<SubmittedWorkStudentWorkRequest> addSubmittedWork(
            @RequestBody SubmittedWorkStudentWorkRequest submittedWorkStudentWorkRequest
    ) {
        submittedWorkImpl.addSubmittedWork(submittedWorkStudentWorkRequest);
        return ApiResponse.<SubmittedWorkStudentWorkRequest>ok(SubmittedWorkStudentWorkRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                .setData(submittedWorkStudentWorkRequest);
    }

    @PostMapping("/insert-student-result")
    ApiResponse<SubmittedWorkStudentResultRequest> addStudentResult(
            @RequestBody SubmittedWorkStudentResultRequest submittedWorkStudentResultRequest
    ) {
        submittedWorkImpl.addStudentResult(submittedWorkStudentResultRequest);
        return ApiResponse.<SubmittedWorkStudentResultRequest>ok(SubmittedWorkStudentResultRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(submittedWorkStudentResultRequest);
    }

    @PutMapping("/update-student-work")
    ApiResponse<SubmittedWorkUpdateStudentWorkRequest> updateStudentWork(
            @RequestBody SubmittedWorkUpdateStudentWorkRequest submittedWorkUpdateStudentWorkRequest
    ) {
        submittedWorkImpl.updateSubmittedWork(submittedWorkUpdateStudentWorkRequest);
        return ApiResponse.<SubmittedWorkUpdateStudentWorkRequest>ok(SubmittedWorkUpdateStudentWorkRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(submittedWorkUpdateStudentWorkRequest);
    }

    @PutMapping("/update-student-result")
    ApiResponse<SubmittedWorkUpdateResultRequest> updateResult(
            @RequestBody SubmittedWorkUpdateResultRequest submittedWorkUpdateResultRequest
    ) {
        submittedWorkImpl.updateResult(submittedWorkUpdateResultRequest);
        return ApiResponse.<SubmittedWorkUpdateResultRequest>ok(SubmittedWorkUpdateResultRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(submittedWorkUpdateResultRequest);
    }

    @DeleteMapping("/delete-by-Id")
    ApiResponse<?> deleteSubmittedWorkId(int id) {
        submittedWorkImpl.deleteSubmittedWorkId(id);
        if (id == 0) {
            return ApiResponse.setError(SubmittedWorkResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.DELETE_ERROR.getMessage())
                    .setData("");
        }
        return ApiResponse.successDelete(SubmittedWorkResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                .setData("The id: " + id + " was deleted successfully");
    }

    @PutMapping("update-status")
    ApiResponse<SubmittedWorkUpdateStatusRequest> updateStatus(
            @RequestBody SubmittedWorkUpdateStatusRequest submittedWorkUpdateStatusRequest) {
        submittedWorkImpl.updateStatus(submittedWorkUpdateStatusRequest);
        return ApiResponse.<SubmittedWorkUpdateStatusRequest>ok(SubmittedWorkUpdateStatusRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(submittedWorkUpdateStatusRequest);
    }
}
