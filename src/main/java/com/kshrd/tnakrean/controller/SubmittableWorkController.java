package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.SubmittableWork;
import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.submittableWork.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.submittableWork.request.SubmittableWorkUpdateRequest;
import com.kshrd.tnakrean.model.submittableWork.response.SubmittableWorkResponse;
import com.kshrd.tnakrean.service.serviceImplementation.SubmittableWorkServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/submittableWork")
public class SubmittableWorkController {
    final SubmittableWorkServiceImpl submittableWorkService;

    public SubmittableWorkController(SubmittableWorkServiceImpl submittableWorkService) {
        this.submittableWorkService = submittableWorkService;
    }

    @GetMapping("/get-all")
    ApiResponse<List<SubmittableWorkResponse>> getAll() {
        List<SubmittableWorkResponse> submittableWorkResponses = submittableWorkService.getAll();
        if (submittableWorkResponses.isEmpty()) {
            ApiResponse.<List<SubmittableWorkResponse>>ok(SubmittableWorkResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(submittableWorkResponses);
        }
        return ApiResponse.<List<SubmittableWorkResponse>>setError(SubmittableWorkResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(submittableWorkResponses);
    }

    @GetMapping("/get-by-id/{id}")
    ApiResponse<SubmittableWorkResponse> getById(int id) {
        SubmittableWorkResponse submittableWorkResponses = submittableWorkService.getById(id);
        if (submittableWorkResponses == null) {
            return ApiResponse.<SubmittableWorkResponse>ok(SubmittableWorkResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(null);
        }
        return ApiResponse.<SubmittableWorkResponse>ok(SubmittableWorkResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                .setData(submittableWorkResponses);
    }

    @PostMapping("/insert")
    ApiResponse<SubmittableWorkRequest> insertSubmittableWork(
            @RequestBody SubmittableWorkRequest submittableWorkRequest
    ) {
        submittableWorkService.insertSubmittableWork(submittableWorkRequest);
        return ApiResponse.<SubmittableWorkRequest>ok(SubmittableWorkRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                .setData(submittableWorkRequest);
    }

    @PutMapping("/update-deadline")
    ApiResponse<SubmittableWorkUpdateRequest> updateSubmittableWork(
            @RequestBody SubmittableWorkUpdateRequest submittableWorkUpdateRequest
    ) {
        submittableWorkService.updateSubmittableWork(submittableWorkUpdateRequest);
        return ApiResponse.<SubmittableWorkUpdateRequest>ok(SubmittableWorkUpdateRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(submittableWorkUpdateRequest);
    }

    @DeleteMapping("/delete-by-id/{id}")
    ApiResponse<?> delete(int id) {
        submittableWorkService.delete(id);
        return ApiResponse.ok("Submittable Work")
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage());
    }

    @GetMapping("/getSubmittableWorkByClassMaterialDetailType/{id}")
    ApiResponse<List<SubmittableWorkResponse>> getSubmittableWorkByClassMaterialDetailType(@RequestParam Integer classMaterialDetailTypeId) {
        List<SubmittableWorkResponse> submittableWorkResponses = submittableWorkService.getSubmittableWorkByClassMaterialDetailType(classMaterialDetailTypeId);
        if (submittableWorkResponses.isEmpty()) {
            ApiResponse.<List<SubmittableWorkResponse>>ok(SubmittableWorkResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(submittableWorkResponses);
        }
        return ApiResponse.<List<SubmittableWorkResponse>>setError(SubmittableWorkResponse.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                .setData(submittableWorkResponses);
    }
}