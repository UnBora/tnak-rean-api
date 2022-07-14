package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.json.ClassMaterialContent;
import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateClassClassroomRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateDeadlineRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;
import com.kshrd.tnakrean.repository.SubmittableWorkRepository;
import com.kshrd.tnakrean.service.serviceImplementation.SubmittableWorkServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

//    @PostMapping("/insert")
//    ApiResponse<SubmittableWorkRequest> insertSubmittableWork(
//            @RequestBody @Valid SubmittableWorkRequest submittableWorkRequest
//    ) {
//        boolean checkClassMaterialsDetailId = submittableWorkRepository.findClassMaterialsDetailId(submittableWorkRequest.getClass_materials_detail_id());
//        boolean checkClassroomId = submittableWorkRepository.findClassroomId(submittableWorkRequest.getClassroom_id());
//        boolean checkClassId = submittableWorkRepository.findClassId(submittableWorkRequest.getClass_id());
//        try {
//            if (checkClassMaterialsDetailId == false) {
//                return ApiResponse.<SubmittableWorkRequest>notFound(SubmittableWorkRequest.class.getSimpleName())
//                        .setResponseMsg("The Class_materials_detail_id: " + submittableWorkRequest.getClass_materials_detail_id() + " doesn't exist in the table");
//            } else if (checkClassroomId == false) {
//                return ApiResponse.<SubmittableWorkRequest>notFound(SubmittableWorkRequest.class.getSimpleName())
//                        .setResponseMsg("The ClassroomId: " + submittableWorkRequest.getClassroom_id() + " doesn't exist in the table");
//            } else if (checkClassId == false) {
//                return ApiResponse.<SubmittableWorkRequest>notFound(SubmittableWorkRequest.class.getSimpleName())
//                        .setResponseMsg("The ClassId: " + submittableWorkRequest.getClass_id() + " doesn't exist in the table");
//            } else {
//                submittableWorkService.insertSubmittableWork(submittableWorkRequest);
//                return ApiResponse.<SubmittableWorkRequest>ok(SubmittableWorkRequest.class.getSimpleName())
//                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
//                        .setData(submittableWorkRequest);
//            }
//        } catch (Exception e) {
//            return ApiResponse.setError(e.getMessage());
//        }
//    }

    @PostMapping("/assign-submittable")
    ApiResponse<?> insertMater(
            @RequestParam @Min(value = 1) int class_material_id,
            @RequestParam @Min(value = 1) int class_room_id,
            @RequestParam @Min(value = 1) int class_id,
            @RequestParam Timestamp deadline,
            @RequestParam @Min(value = 1) @Max(value = 1000) float score
    ) {
        boolean checkClassId = submittableWorkRepository.findClassId(class_id);
        boolean checkClassIdANDMaterialIdInMD = submittableWorkRepository.findClassIdANDMaterialIdInMD(class_id, class_material_id);
        try {
            if (checkClassId == false) {
                return ApiResponse.notFound("")
                        .setResponseMsg("Class Id: " + class_id + " doesn't exist ");
            } else if (checkClassIdANDMaterialIdInMD == true) {
                return ApiResponse.notFound("")
                        .setResponseMsg("Class Id: " + class_id + " and ClassMaterialId: " + class_material_id + " already exist ");
            } else {
                Integer mdt = submittableWorkRepository.insertInotClassMaterialDetail(class_material_id, class_room_id, class_id);
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.of("+07:00")));
                submittableWorkRepository.insertInotSubmitableWork(mdt, timestamp, deadline, class_room_id, class_id, score);
                return ApiResponse.ok("")
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage());
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
                        .setResponseMsg("Can't update! SubmittableId: " + submittableWorkUpdateClassClassroomRequest.getSubmittable_work_id() + " doesn't exist");
            } else if (checkClassId == false) {
                return ApiResponse.<SubmittableWorkUpdateClassClassroomRequest>notFound(SubmittableWorkUpdateClassClassroomRequest.class.getSimpleName())
                        .setResponseMsg("Can't update! ClassId: " + submittableWorkUpdateClassClassroomRequest.getClass_id() + " doesn't exist");
            } else if (checkClassroomId == false) {
                System.out.println("cc");
                return ApiResponse.<SubmittableWorkUpdateClassClassroomRequest>notFound(SubmittableWorkUpdateClassClassroomRequest.class.getSimpleName())
                        .setResponseMsg("Can't update! ClassroomId: " + submittableWorkUpdateClassClassroomRequest.getClassroom_id() + " doesn't exist");
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
    @GetMapping("get-classwork-by-folderId-with-classId")
    ApiResponse<List<ClassWorkByFolderIDClassIDResponse>> getByFolderIdClassId(
            @RequestParam @Min(value = 1) Integer class_id,
            @RequestParam @Min(value = 1) Integer classroom_id,
            @RequestParam @Min(value = 1) Integer folderId
    ) {
        try {
            List<ClassWorkByFolderIDClassIDResponse> responses = submittableWorkService.getByFolderIdClassId(classroom_id, class_id,folderId);
            if (responses.isEmpty()) {
                return ApiResponse.<List<ClassWorkByFolderIDClassIDResponse>>notFound(ClassWorkByFolderIDClassIDResponse.class
                                .getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(responses);
            }
            return ApiResponse.<List<ClassWorkByFolderIDClassIDResponse>>ok(ClassWorkByFolderIDClassIDResponse.class
                            .getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(responses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
    @GetMapping("get-classwork-by-studentId")
    ApiResponse<List<ClassWorkByStudentIdResponse>> getClassWorkByStudentId(
    ) {
        try {
            Integer user_id = AuthRestController.user_id;
            List<ClassWorkByStudentIdResponse> responses = submittableWorkService.getClassWorkByStudentId(user_id);
            if (user_id == 0){
                return ApiResponse.unAuthorized("unAuthorized");
            } else if (responses.isEmpty()) {
                return ApiResponse.<List<ClassWorkByStudentIdResponse>>notFound(ClassWorkByStudentIdResponse.class
                                .getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(responses);
            }
            return ApiResponse.<List<ClassWorkByStudentIdResponse>>ok(ClassWorkByStudentIdResponse.class
                            .getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(responses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-teacherUserId")
    ApiResponse<List<SubmittableWorkByTeacherResponse>> getByTeacherUserId() {
        try {
            Integer user_id = AuthRestController.user_id;
            List<SubmittableWorkByTeacherResponse> submittableWorkResponses = submittableWorkService.getByTeacherUserId(user_id);
            if (user_id == 0){
                return ApiResponse.unAuthorized("unAuthorized");
            } else if (submittableWorkResponses.isEmpty()) {
                return ApiResponse.<List<SubmittableWorkByTeacherResponse>>notFound(SubmittableWorkByTeacherResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(submittableWorkResponses);
            }
            return ApiResponse.<List<SubmittableWorkByTeacherResponse>>ok(SubmittableWorkByTeacherResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(submittableWorkResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
    @GetMapping("get-classwork-by-folderId-with-teacherId")
    ApiResponse<List<ClassWorkByFolderIDTeacherIDResponse>> getByFolderIdTeacherId(
            @RequestParam @Min(value = 1) Integer folderId
    ) {
        try {
            Integer user_id = AuthRestController.user_id;
            List<ClassWorkByFolderIDTeacherIDResponse> responses = submittableWorkService.getByFolderIdTeacherId(user_id,folderId);
            if (user_id == 0){
                return ApiResponse.unAuthorized("unAuthorized");
            } else if (responses.isEmpty()) {
                return ApiResponse.<List<ClassWorkByFolderIDTeacherIDResponse>>notFound(ClassWorkByFolderIDTeacherIDResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(responses);
            }
            return ApiResponse.<List<ClassWorkByFolderIDTeacherIDResponse>>ok(ClassWorkByFolderIDTeacherIDResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(responses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-classwork-has-result-by-classId")
    ApiResponse<List<ClassWorkResultByClassIdResponse>> getClassWorkResultByClassId(
            @RequestParam @Min(value = 1) Integer class_id
    ) {
        try {
            List<ClassWorkResultByClassIdResponse> responses = submittableWorkService.getClassWorkResultByClassId(class_id);
            if (responses.isEmpty()) {
                return ApiResponse.<List<ClassWorkResultByClassIdResponse>>notFound(ClassWorkResultByClassIdResponse.class
                                .getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(responses);
            }
            return ApiResponse.<List<ClassWorkResultByClassIdResponse>>ok(ClassWorkResultByClassIdResponse.class
                            .getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(responses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-classwork-has-result-by-studentId")
    ApiResponse<List<ClassWorkResultByStudentIdResponse>> getClassWorkResultByStudentId(
    ) {
        try {
            Integer user_id = AuthRestController.user_id;
            List<ClassWorkResultByStudentIdResponse> responses = submittableWorkService.getClassWorkResultByStudentId(user_id);
            if (user_id == 0){
                return ApiResponse.unAuthorized("unAuthorized");
            } else if (responses.isEmpty()) {
                return ApiResponse.<List<ClassWorkResultByStudentIdResponse>>notFound(ClassWorkResultByStudentIdResponse.class
                                .getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(responses);
            }
            return ApiResponse.<List<ClassWorkResultByStudentIdResponse>>ok(ClassWorkResultByStudentIdResponse.class
                            .getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(responses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
    @GetMapping("view-classwork-by-classMaterialId")
    ApiResponse<List<SubmittableWorkByMaterialResponse>> getByClassMaterialId(@RequestParam @Min(value = 1) Integer material_id) {
        try {
            List<SubmittableWorkByMaterialResponse> submittableWorkResponses = submittableWorkService.getByClassMaterialId(material_id);
            if (submittableWorkResponses.isEmpty()) {
                return ApiResponse.<List<SubmittableWorkByMaterialResponse>>notFound(SubmittableWorkByMaterialResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(submittableWorkResponses);
            }
            return ApiResponse.<List<SubmittableWorkByMaterialResponse>>ok(SubmittableWorkByMaterialResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(submittableWorkResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("create-quiz")
    ApiResponse<Boolean> createQuiz(
            @RequestParam @NotBlank String title,
            @RequestParam @NotBlank String description,
            @RequestBody @Valid ClassMaterialContent classMaterialContent
    ) {
        Integer user_id = AuthRestController.user_id;
        int typeId = 5;
        try {
            if (user_id == 0) {
                return ApiResponse.unAuthorized("unAuthorized");
            } else {
                Timestamp created_date = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.of("+07:00")));
                title.trim();
                description.trim();
                submittableWorkRepository.createClassworks(created_date,title,description, user_id, typeId,classMaterialContent);
                return ApiResponse.<Boolean>ok("Create Course")
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(true);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("create-homework")
    ApiResponse<Boolean> creatHomework(
            @RequestParam @NotBlank String title,
            @RequestParam @NotBlank String description,
            @RequestBody @Valid ClassMaterialContent classMaterialContent
    ) {
        Integer user_id = AuthRestController.user_id;
        int typeId = 4;
        try {
            if (user_id == 0) {
                return ApiResponse.unAuthorized("unAuthorized");
            } else {
                Timestamp created_date = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.of("+07:00")));
                title.trim();
                description.trim();
                submittableWorkRepository.createClassworks(created_date,title,description, user_id, typeId,classMaterialContent);
                return ApiResponse.<Boolean>ok("Create Homework")
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(true);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("create-assigment")
    ApiResponse<Boolean> creatassigment(
            @RequestParam @NotBlank String title,
            @RequestParam @NotBlank String description,
            @RequestBody @Valid ClassMaterialContent classMaterialContent
    ) {
        Integer user_id = AuthRestController.user_id;
        int typeId = 3;
        try {
            if (user_id == 0) {
                return ApiResponse.unAuthorized("unAuthorized");
            } else {
                Timestamp created_date = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.of("+07:00")));
                title.trim();
                description.trim();
                submittableWorkRepository.createClassworks(created_date,title,description, user_id, typeId,classMaterialContent);
                return ApiResponse.<Boolean>ok("Create Course")
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(true);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("create-quiz-in-class")
    ApiResponse<Boolean> createQuizByClass(
            @RequestParam @NotEmpty @NotBlank String title,
            @RequestParam @NotEmpty @NotBlank String description,
            @RequestBody @Valid ClassMaterialContent classMaterialContent,
            @RequestParam Timestamp deadline,
            @RequestParam @Min(value = 1) int class_id,
            @RequestParam @Min(value = 1) int classroom_id,
            @RequestParam @Min(value = 1) @Max(value = 1000) float score
    ) {
        Integer user_id = AuthRestController.user_id;
        boolean checkClassId = submittableWorkRepository.findClassId(class_id);
        boolean checkClassroomId = submittableWorkRepository.findClassroomId(classroom_id);
        try {
            if (user_id == 0) {
                return ApiResponse.unAuthorized("unAuthorized");
            } else if (checkClassId == false) {
                return ApiResponse.<Boolean>notFound(ClassMaterialRequest.class.getSimpleName())
                        .setResponseMsg("Class Id: " + class_id + " doesn't exist ");
            } else if (checkClassroomId == false) {
                return ApiResponse.<Boolean>notFound(ClassMaterialRequest.class.getSimpleName())
                        .setResponseMsg("Classroom Id: " + classroom_id + " doesn't exist ");
            } else {
                title.trim();
                description.trim();
                int material_type_id = 5;
                Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.of("+07:00")));
                Integer materialId = submittableWorkRepository.createNewClasswork(material_type_id, user_id, title, description, createdDate, classMaterialContent);
                Integer mdt = submittableWorkRepository.createClassworkInMaterialDetail(materialId, classroom_id, class_id);
                submittableWorkRepository.createClassworkByClass(mdt, createdDate, deadline, classroom_id, class_id, score);
                return ApiResponse.<Boolean>ok("Create Quiz")
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(true);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("create-homework-in-class")
    ApiResponse<Boolean> createHomeworkByClass(
            @RequestParam @NotEmpty @NotBlank String title,
            @RequestParam @NotEmpty @NotBlank String description,
            @RequestBody @Valid ClassMaterialContent classMaterialContent,
            @RequestParam Timestamp deadline,
            @RequestParam @Min(value = 1) int class_id,
            @RequestParam @Min(value = 1) int classroom_id,
            @RequestParam @Min(value = 1) @Max(value = 1000) float score
    ) {
        Integer user_id = AuthRestController.user_id;
        boolean checkClassId = submittableWorkRepository.findClassId(class_id);
        boolean checkClassroomId = submittableWorkRepository.findClassroomId(classroom_id);
        try {
            if (user_id == 0) {
                return ApiResponse.unAuthorized("unAuthorized");
            } else if (checkClassId == false) {
                return ApiResponse.<Boolean>notFound(ClassMaterialRequest.class.getSimpleName())
                        .setResponseMsg("Class Id: " + class_id + " doesn't exist ");
            } else if (checkClassroomId == false) {
                return ApiResponse.<Boolean>notFound(ClassMaterialRequest.class.getSimpleName())
                        .setResponseMsg("Classroom Id: " + classroom_id + " doesn't exist ");
            } else {
                title.trim();
                description.trim();
                int material_type_id = 4;
                Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.of("+07:00")));
                Integer materialId = submittableWorkRepository.createNewClasswork(material_type_id, user_id, title, description, createdDate, classMaterialContent);
                Integer mdt = submittableWorkRepository.createClassworkInMaterialDetail(materialId, classroom_id, class_id);
                submittableWorkRepository.createClassworkByClass(mdt, createdDate, deadline, classroom_id, class_id, score);
                return ApiResponse.<Boolean>ok("Create Homework")
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(true);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("create-assignment-in-class")
    ApiResponse<Boolean> createAssignmentByClass(
            @RequestParam @NotEmpty @NotBlank String title,
            @RequestParam @NotEmpty @NotBlank String description,
            @RequestBody @Valid ClassMaterialContent classMaterialContent,
            @RequestParam Timestamp deadline,
            @RequestParam @Min(value = 1) int class_id,
            @RequestParam @Min(value = 1) int classroom_id,
            @RequestParam @Min(value = 1) @Max(value = 1000) float score
    ) {
        Integer user_id = AuthRestController.user_id;
        boolean checkClassId = submittableWorkRepository.findClassId(class_id);
        boolean checkClassroomId = submittableWorkRepository.findClassroomId(classroom_id);
        try {
            if (user_id == 0) {
                return ApiResponse.unAuthorized("unAuthorized");
            } else if (checkClassId == false) {
                return ApiResponse.<Boolean>notFound(ClassMaterialRequest.class.getSimpleName())
                        .setResponseMsg("Class Id: " + class_id + " doesn't exist ");
            } else if (checkClassroomId == false) {
                return ApiResponse.<Boolean>notFound(ClassMaterialRequest.class.getSimpleName())
                        .setResponseMsg("Classroom Id: " + classroom_id + " doesn't exist ");
            } else {
                title.trim();
                description.trim();
                int material_type_id = 3;
                Timestamp createdDate = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.of("+07:00")));
                Integer materialId = submittableWorkRepository.createNewClasswork(material_type_id, user_id, title, description, createdDate, classMaterialContent);
                Integer mdt = submittableWorkRepository.createClassworkInMaterialDetail(materialId, classroom_id, class_id);
                submittableWorkRepository.createClassworkByClass(mdt, createdDate, deadline, classroom_id, class_id, score);
                return ApiResponse.<Boolean>ok("Create Assignment")
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(true);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    //@GetMapping("get-all-by-classId-teacherUserId")
//    ApiResponse<List<SubmittableWorkByClassIdTeacherIdResponse>> getAllByClassIdTeacherUserId(@RequestParam @Min(value = 1) Integer class_id) {
//        try {
//            Integer user_id = AuthRestController.user_id;
//            List<SubmittableWorkByClassIdTeacherIdResponse> submittableWorkResponses = submittableWorkService.getAllByClassIdTeacherUserId(user_id, class_id);
//            if (user_id == 0) {
//                return ApiResponse.unAuthorized("unAuthorized");
//            } else if (submittableWorkResponses.isEmpty()) {
//                return ApiResponse.<List<SubmittableWorkByClassIdTeacherIdResponse>>notFound(SubmittableWorkByClassIdTeacherIdResponse.class.getSimpleName())
//                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
//                        .setData(submittableWorkResponses);
//            } else {
//                return ApiResponse.<List<SubmittableWorkByClassIdTeacherIdResponse>>ok(SubmittableWorkByClassIdTeacherIdResponse.class.getSimpleName())
//                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
//                        .setData(submittableWorkResponses);
//            }
//        } catch (Exception e) {
//            return ApiResponse.setError(e.getMessage());
//        }
//    }
}