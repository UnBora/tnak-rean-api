package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateClassClassroomRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateDeadlineRequest;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittableWorkResponse;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkResponse;
import com.kshrd.tnakrean.model.classmaterials.response.UpComingSubmittableWorkResponse;
import com.kshrd.tnakrean.repository.SubmittableWorkRepository;
import com.kshrd.tnakrean.service.serviceImplementation.SubmittableWorkServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("api/v1/submittableWork")
@SecurityRequirement(name = "bearerAuth")
public class SubmittableWorkController {
    final SubmittableWorkServiceImpl submittableWorkService;
    final SubmittableWorkRepository submittableWorkRepository;

    public SubmittableWorkController(SubmittableWorkServiceImpl submittableWorkService, SubmittableWorkRepository submittableWorkRepository) {
        this.submittableWorkService = submittableWorkService;
        this.submittableWorkRepository = submittableWorkRepository;
    }

    @GetMapping("/get-all")
    ApiResponse<List<SubmittableWorkResponse>> getAll() {
        try {
            List<SubmittableWorkResponse> submittableWorkResponses = submittableWorkService.getAll();
            if (submittableWorkResponses.isEmpty()) {
                ApiResponse.<List<SubmittableWorkResponse>>notFound(SubmittableWorkResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(submittableWorkResponses);
            }
            return ApiResponse.<List<SubmittableWorkResponse>>setError(SubmittableWorkResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(submittableWorkResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-id/{id}")
    ApiResponse<SubmittableWorkResponse> getById(@RequestParam @Min(value = 1) Integer id) {
        try {
            SubmittableWorkResponse submittableWorkResponses = submittableWorkService.getById(id);
            if (submittableWorkResponses == null) {
                return ApiResponse.<SubmittableWorkResponse>notFound(SubmittableWorkResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(null);
            }
            return ApiResponse.<SubmittableWorkResponse>ok(SubmittableWorkResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                    .setData(submittableWorkResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("/insert")
    ApiResponse<SubmittableWorkRequest> insertSubmittableWork(
            @RequestBody @Valid SubmittableWorkRequest submittableWorkRequest
    ) {
        boolean checkClassMaterialsDetailId = submittableWorkRepository.findClassMaterialsDetailId(submittableWorkRequest.getClass_materials_detail_id());
        boolean checkClassroomId = submittableWorkRepository.findClassroomId(submittableWorkRequest.getClassroom_id());
        boolean checkClassId = submittableWorkRepository.findClassId(submittableWorkRequest.getClass_id());
        try {
            if (checkClassMaterialsDetailId == false) {
                return ApiResponse.<SubmittableWorkRequest>notFound(SubmittableWorkRequest.class.getSimpleName())
                        .setResponseMsg("The Class_materials_detail_id: " + submittableWorkRequest.getClass_materials_detail_id() + " doesn't exist in the table");
            } else if (checkClassroomId == false) {
                return ApiResponse.<SubmittableWorkRequest>notFound(SubmittableWorkRequest.class.getSimpleName())
                        .setResponseMsg("The ClassroomId: " + submittableWorkRequest.getClassroom_id() + " doesn't exist in the table");
            } else if (checkClassId == false) {
                return ApiResponse.<SubmittableWorkRequest>notFound(SubmittableWorkRequest.class.getSimpleName())
                        .setResponseMsg("The ClassId: " + submittableWorkRequest.getClass_id() + " doesn't exist in the table");
            } else {
                submittableWorkService.insertSubmittableWork(submittableWorkRequest);
                return ApiResponse.<SubmittableWorkRequest>ok(SubmittableWorkRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(submittableWorkRequest);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("/update-deadline")
    ApiResponse<SubmittableWorkUpdateDeadlineRequest> updateSubmittableWork(
            @RequestBody @Valid SubmittableWorkUpdateDeadlineRequest submittableWorkUpdateDeadlineRequest
    ) {
        try {
            SubmittedWorkResponse submittedWorkResponse = submittableWorkService.updateSubmittableWork(submittableWorkUpdateDeadlineRequest);
            if (submittedWorkResponse == null) {
                return ApiResponse.<SubmittableWorkUpdateDeadlineRequest>notFound(SubmittableWorkUpdateDeadlineRequest.class.getSimpleName())
                        .setResponseMsg("Can't update! ID: " + submittableWorkUpdateDeadlineRequest.getSubmittable_work_id() + " doesn't exist");
            }
            return ApiResponse.<SubmittableWorkUpdateDeadlineRequest>ok(SubmittableWorkUpdateDeadlineRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                    .setData(submittableWorkUpdateDeadlineRequest);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @DeleteMapping("/delete-by-id/{id}")
    ApiResponse<Boolean> delete(@RequestParam Integer submittable_work_id) {
        try {
            SubmittableWorkResponse submittableWorkResponse = submittableWorkService.delete(submittable_work_id);
            if (submittableWorkResponse == null) {
                return ApiResponse.<Boolean>notFound("Submittable Work")
                        .setResponseMsg("Can't delete! ID: " + submittable_work_id + " doesn't exist")
                        .setData(null);
            }
            return ApiResponse.<Boolean>ok("Submittable Work")
                    .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                    .setData(true);
        } catch (Exception e) {
            return ApiResponse.<Boolean>badRequest("")
                    .setResponseMsg("Can't delete! Because of violates foreign key constraint");
        }
    }

    @GetMapping("/get-by-classMaterialDetailType/{id}")
    ApiResponse<List<SubmittableWorkResponse>> getSubmittableWorkByClassMaterialDetailType
            (@RequestParam @Min(value = 1) Integer classMaterialDetailTypeId) {
        try {
            List<SubmittableWorkResponse> submittableWorkResponses = submittableWorkService.getSubmittableWorkByClassMaterialDetailType(classMaterialDetailTypeId);
            if (submittableWorkResponses.isEmpty()) {
                return ApiResponse.<List<SubmittableWorkResponse>>notFound(SubmittableWorkResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(submittableWorkResponses);
            }
            return ApiResponse.<List<SubmittableWorkResponse>>ok(SubmittableWorkResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(submittableWorkResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("getUpComingWorkByStudentId")
    ApiResponse<List<UpComingSubmittableWorkResponse>> getUpComingSubmittableWorkByStudentId(
            @RequestParam @Min(value = 1) Integer studentId,
            @RequestParam @Min(value = 1) Integer classId,
            @RequestParam @Min(value = 1) Integer classRoomId) {
        try {
            List<UpComingSubmittableWorkResponse> responseList = submittableWorkService.getUpComingSubmittableWorkByStudentId(studentId, classId, classRoomId);
            System.out.println(responseList);
            if (!responseList.isEmpty()) {
                return ApiResponse.<List<UpComingSubmittableWorkResponse>>
                        ok(SubmittableWorkResponse.class
                        .getSimpleName()).setData(responseList).setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage());
            } else {
                return ApiResponse.notFound(SubmittableWorkResponse.class.getSimpleName());
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-classroomId-and-classId/{classroom_id}/{class_id}")
    ApiResponse<List<SubmittableWorkResponse>> getByClassIdAndClassId(
            @RequestParam @Min(value = 1) Integer classroom_id,
            @RequestParam @Min(value = 1) Integer class_id
    ) {
        try {
            List<SubmittableWorkResponse> submittableWorkResponses = submittableWorkService.getByClassIdAndClassId(classroom_id, class_id);
            if (submittableWorkResponses.isEmpty()) {
                return ApiResponse.<List<SubmittableWorkResponse>>notFound(SubmittableWorkResponse.class
                                .getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
            return ApiResponse.<List<SubmittableWorkResponse>>ok(SubmittableWorkResponse.class
                            .getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(submittableWorkResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("update-classroomId-classId")
    ApiResponse<SubmittableWorkUpdateClassClassroomRequest> updateClassClassroom(
            @RequestBody @Valid SubmittableWorkUpdateClassClassroomRequest submittableWorkUpdateClassClassroomRequest
    ) {
        try {
        SubmittedWorkResponse submittedWorkResponse = submittableWorkService.updateClassClassroom(submittableWorkUpdateClassClassroomRequest);
        if (submittedWorkResponse == null) {
            return ApiResponse.<SubmittableWorkUpdateClassClassroomRequest>notFound(SubmittableWorkUpdateDeadlineRequest.class.getSimpleName())
                    .setResponseMsg("Can't update! ID: "+submittableWorkUpdateClassClassroomRequest.getSubmittable_work_id()+" doesn't exist");
        }
        return ApiResponse.<SubmittableWorkUpdateClassClassroomRequest>ok(SubmittableWorkUpdateDeadlineRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(submittableWorkUpdateClassClassroomRequest);
    } catch (Exception e) {
            return ApiResponse.<SubmittableWorkUpdateClassClassroomRequest>badRequest("")
                    .setResponseMsg("Can't update! Because of violates foreign key constraint from classId and classroomId");
        }
    }
}