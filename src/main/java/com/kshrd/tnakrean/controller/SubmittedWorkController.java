package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.SubmittableWork;
import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.*;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkByClassroomClassSubmittableResponse;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkByMaterialIdResponse;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkByStudentIdAndClassIdResponse;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkResponse;
import com.kshrd.tnakrean.repository.SubmittedWorkRepository;
import com.kshrd.tnakrean.service.serviceImplementation.SubmittedWorkImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
                return ApiResponse.<List<SubmittedWorkResponse>>notFound(SubmittedWorkResponse.class
                                .getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(submittedWorkResponse);
            }
            return ApiResponse.<List<SubmittedWorkResponse>>ok(SubmittedWorkResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(submittedWorkResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-id/{id}")
    ApiResponse<SubmittedWorkResponse> getById(@RequestParam @Min(value = 1) Integer id) {
        try {
            SubmittedWorkResponse submittedWorkResponse = submittedWorkImpl.getById(id);
            if (submittedWorkResponse == null) {
                return ApiResponse.<SubmittedWorkResponse>notFound(SubmittedWorkResponse.class
                                .getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
            return ApiResponse.<SubmittedWorkResponse>ok(SubmittedWorkResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(submittedWorkResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-studentId/{id}")
    ApiResponse<List<SubmittedWorkResponse>> getSubmittedByStudentId(@RequestParam @Min(value = 1) Integer studentId) throws IllegalStateException {
        try {
            List<SubmittedWorkResponse> submittedWorkResponses = submittedWorkImpl.getSubmittedByStudentId(studentId);
            if (submittedWorkResponses.isEmpty()) {
                return ApiResponse.<List<SubmittedWorkResponse>>notFound(SubmittedWorkResponse.class
                                .getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
            return ApiResponse.<List<SubmittedWorkResponse>>ok(SubmittedWorkResponse.class
                            .getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                    .setData(submittedWorkResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-studentId-and-classId/{student_id}/{class_id}")
    ApiResponse<List<SubmittedWorkByStudentIdAndClassIdResponse>> getByStudentIdAndClassId(
            @RequestParam @Min(value = 1) Integer student_id,
            @RequestParam @Min(value = 1) Integer class_id

    ) {
        List<SubmittedWorkByStudentIdAndClassIdResponse> submittedWorkResponses = submittedWorkImpl.getByStudentIdAndClassId(student_id, class_id);
        try {
            if (submittedWorkResponses.isEmpty()) {
                return ApiResponse.<List<SubmittedWorkByStudentIdAndClassIdResponse>>ok(SubmittedWorkResponse.class
                                .getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
            return ApiResponse.<List<SubmittedWorkByStudentIdAndClassIdResponse>>ok(SubmittedWorkResponse.class
                            .getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                    .setData(submittedWorkResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("/insert-student-work")
    ApiResponse<SubmittedWorkStudentWorkRequest> addSubmittedWork(
            @RequestBody @Valid SubmittedWorkStudentWorkRequest submittedWorkStudentWorkRequest
    ) {
        Integer userId = AuthRestController.user_id;
        System.out.println(userId);
        submittedWorkImpl.addSubmittedWork(submittedWorkStudentWorkRequest, userId);
        try {
            return ApiResponse.<SubmittedWorkStudentWorkRequest>ok(SubmittedWorkStudentWorkRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                    .setData(submittedWorkStudentWorkRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }

    }

    @PutMapping("/update-student-score")
    ApiResponse<SubmittedWorkStudentScoreRequest> insertScore(
            @RequestBody @Valid SubmittedWorkStudentScoreRequest submittedWorkStudentScoreRequest
    ) {
        SubmittedWorkStudentScoreRequest submittedWorkStudentScoreRequest1 = submittedWorkImpl.insertScore(submittedWorkStudentScoreRequest);
        try {
            if (submittedWorkStudentScoreRequest1 == null) {
                return ApiResponse.<SubmittedWorkStudentScoreRequest>notFound(SubmittedWorkStudentScoreRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage());
            }
            return ApiResponse.<SubmittedWorkStudentScoreRequest>ok(SubmittedWorkStudentScoreRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                    .setData(submittedWorkStudentScoreRequest1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("/update-student-work")
    ApiResponse<SubmittedWorkUpdateStudentWorkRequest> updateStudentWork(
            @RequestBody @Valid SubmittedWorkUpdateStudentWorkRequest submittedWorkUpdateStudentWorkRequest
    ) {
        SubmittedWorkUpdateStudentWorkRequest submittedWorkUpdateStudentWorkRequest1 = submittedWorkImpl.updateSubmittedWork(submittedWorkUpdateStudentWorkRequest);
        try {
            if (submittedWorkUpdateStudentWorkRequest1 == null) {
                return ApiResponse.<SubmittedWorkUpdateStudentWorkRequest>notFound(SubmittedWorkUpdateStudentWorkRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage());
            }
            return ApiResponse.<SubmittedWorkUpdateStudentWorkRequest>ok(SubmittedWorkUpdateStudentWorkRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                    .setData(submittedWorkUpdateStudentWorkRequest1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @DeleteMapping("/delete-by-Id/{id}")
    ApiResponse<Boolean> deleteSubmittedWorkId(@RequestParam Integer id) {
        SubmittedWorkResponse submittedWorkResponse = submittedWorkImpl.deleteSubmittedWorkId(id);
        try {
            if (submittedWorkResponse == null) {
                return ApiResponse.<Boolean>notFound(SubmittedWorkResponse.class.getSimpleName())
                        .setResponseMsg("Don't have that ID: " + id)
                        .setData(false);
            }
            return ApiResponse.<Boolean>ok(SubmittedWorkResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                    .setData(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }

    }

    @GetMapping("get-by-classMaterialId/{classMaterialId}")
    ApiResponse<List<SubmittedWorkByMaterialIdResponse>> getByClassMaterialId(@RequestParam @Min(value = 1) Integer class_material_id) {
        try {
            List<SubmittedWorkByMaterialIdResponse> submittedWorkResponses = submittedWorkImpl.getByClassMaterialId(class_material_id);
            if (submittedWorkResponses.isEmpty()) {
                return ApiResponse.<List<SubmittedWorkByMaterialIdResponse>>notFound(SubmittedWorkResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(submittedWorkResponses);
            }
            return ApiResponse.<List<SubmittedWorkByMaterialIdResponse>>ok(SubmittedWorkByMaterialIdResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(submittedWorkResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-classroom-class-submittable/{classroomId}/{classId}/{submittableId}")
    ApiResponse<List<SubmittedWorkByClassroomClassSubmittableResponse>> getByClassroomClassSubmittable(
            @RequestParam @Min(value = 1) Integer classroom_id,
            @RequestParam @Min(value = 1) Integer class_id,
            @RequestParam @Min(value = 1) Integer submittable_work_id) {
        try {
            List<SubmittedWorkByClassroomClassSubmittableResponse> submittedWorkByClassroomClassSubmittableResponses = submittedWorkImpl.getByClassroomClassSubmittable(classroom_id, class_id, submittable_work_id);
            if (submittedWorkByClassroomClassSubmittableResponses.isEmpty()) {
                return ApiResponse.<List<SubmittedWorkByClassroomClassSubmittableResponse>>notFound(SubmittedWorkByClassroomClassSubmittableResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(submittedWorkByClassroomClassSubmittableResponses);
            }
            return ApiResponse.<List<SubmittedWorkByClassroomClassSubmittableResponse>>ok(SubmittedWorkByClassroomClassSubmittableResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(submittedWorkByClassroomClassSubmittableResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }
}