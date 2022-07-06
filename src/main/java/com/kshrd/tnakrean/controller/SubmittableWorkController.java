package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateClassClassroomRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateDeadlineRequest;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittableWorkByClassResponse;
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
@CrossOrigin(origins = "*")
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

    @GetMapping("/get-by-id")
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
    @PutMapping("update-classroomId-classId")
    ApiResponse<SubmittableWorkUpdateClassClassroomRequest> updateClassClassroom(
            @RequestBody @Valid SubmittableWorkUpdateClassClassroomRequest submittableWorkUpdateClassClassroomRequest
    ) {
        boolean checkClassId = submittableWorkRepository.findClassId(submittableWorkUpdateClassClassroomRequest.getClass_id());
        boolean checkClassroomId = submittableWorkRepository.findClassroomId(submittableWorkUpdateClassClassroomRequest.getClassroom_id());
        boolean checkSubmittableId = submittableWorkRepository.findSubmittableId(submittableWorkUpdateClassClassroomRequest.getSubmittable_work_id());
        try {

            if (checkSubmittableId == false) {
                return ApiResponse.<SubmittableWorkUpdateClassClassroomRequest>notFound(SubmittableWorkUpdateClassClassroomRequest.class.getSimpleName())
                        .setResponseMsg("Can't update! SubmittableId: "+submittableWorkUpdateClassClassroomRequest.getSubmittable_work_id()+ " doesn't exist");
            } else if (checkClassId == false) {
                return ApiResponse.<SubmittableWorkUpdateClassClassroomRequest>notFound(SubmittableWorkUpdateClassClassroomRequest.class.getSimpleName())
                        .setResponseMsg("Can't update! ClassId: "+submittableWorkUpdateClassClassroomRequest.getClass_id()+" doesn't exist");
            } else if (checkClassroomId == false) {
                System.out.println("cc");
                return ApiResponse.<SubmittableWorkUpdateClassClassroomRequest>notFound(SubmittableWorkUpdateClassClassroomRequest.class.getSimpleName())
                        .setResponseMsg("Can't update! ClassroomId: "+submittableWorkUpdateClassClassroomRequest.getClassroom_id()+" doesn't exist");
            } else {
                submittableWorkService.updateClassClassroom(submittableWorkUpdateClassClassroomRequest);
                return ApiResponse.<SubmittableWorkUpdateClassClassroomRequest>ok(SubmittableWorkUpdateClassClassroomRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(submittableWorkUpdateClassClassroomRequest);
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
                        .setResponseMsg("Can't update! SubmittableId: " + submittableWorkUpdateDeadlineRequest.getSubmittable_work_id() + " doesn't exist");
            }
            return ApiResponse.<SubmittableWorkUpdateDeadlineRequest>ok(SubmittableWorkUpdateDeadlineRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                    .setData(submittableWorkUpdateDeadlineRequest);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @DeleteMapping("/delete-by-id")
    ApiResponse<Boolean> delete(@RequestParam Integer submittable_work_id) {
        try {
            boolean checkSubmittableWorkIdInSubmiited = submittableWorkRepository.findSubmittableIdInSubmiitedWork(submittable_work_id);
            boolean checkSubmittableWorkId = submittableWorkRepository.findSubmittableId(submittable_work_id);

            if (checkSubmittableWorkId == false) {
                return ApiResponse.<Boolean>notFound("Submittable Work")
                        .setResponseMsg("Can't delete! SubmittableWorkID: " + submittable_work_id + " doesn't exist");
            } else if (checkSubmittableWorkIdInSubmiited == true) {
                System.out.println("b");
                return ApiResponse.<Boolean>notFound("Submittable Work")
                        .setResponseMsg("Can't delete! SubmittableWorkID: " + submittable_work_id + " is still referenced from table submitted_work");
            } else {
                submittableWorkService.delete(submittable_work_id);
                return ApiResponse.<Boolean>ok("Submittable Work")
                        .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                        .setData(true);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-by-classMaterialDetailTypeId")
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

    @GetMapping("get-upComingWork-by-StudentId")
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

    @GetMapping("get-by-classId-classroomId")
    ApiResponse<List<SubmittableWorkByClassResponse>> getByClassIdAndClassId(
            @RequestParam @Min(value = 1) Integer class_id,
            @RequestParam @Min(value = 1) Integer classroom_id
    ) {
        try {
            List<SubmittableWorkByClassResponse> submittableWorkResponses = submittableWorkService.getByClassIdAndClassId(classroom_id, class_id);
            if (submittableWorkResponses.isEmpty()) {
                return ApiResponse.<List<SubmittableWorkByClassResponse>>notFound(SubmittableWorkByClassResponse.class
                                .getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
            return ApiResponse.<List<SubmittableWorkByClassResponse>>ok(SubmittableWorkByClassResponse.class
                            .getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(submittableWorkResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}