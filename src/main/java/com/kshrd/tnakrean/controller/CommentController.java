package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.CommentInsertRequest;
import com.kshrd.tnakrean.model.classmaterials.request.CommentUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittedWorkStudentWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;
import com.kshrd.tnakrean.model.user.response.TeacherResponse;
import com.kshrd.tnakrean.repository.CommentRepository;
import com.kshrd.tnakrean.service.serviceImplementation.CommentServiceImp;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
public class CommentController {
    final CommentServiceImp commentServiceImp;
    final CommentRepository commentRepository;

    public CommentController(CommentServiceImp commentServiceImp, CommentRepository commentRepository) {
        this.commentServiceImp = commentServiceImp;
        this.commentRepository = commentRepository;
    }

    @GetMapping("get-all")
    ApiResponse<List<CommentResponse>> getAll() {
        try {
            List<CommentResponse> commentResponses = commentServiceImp.getAll();
            if (commentResponses.isEmpty()) {
                return ApiResponse.<List<CommentResponse>>notFound(CommentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(commentResponses);
            }
            return ApiResponse.<List<CommentResponse>>ok(CommentResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(commentResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-commentId/")
    ApiResponse<CommentResponse> getById(@RequestParam @Min(value = 1) Integer comment_id) {
        try {
            CommentResponse commentResponses = commentServiceImp.getById(comment_id);
            if (commentResponses == null) {
                return ApiResponse.<CommentResponse>notFound(CommentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            }
            return ApiResponse.<CommentResponse>ok(CommentResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                    .setData(commentResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-studentId/")
    ApiResponse<List<CommentResponse>> getByStudentId() {
        try {
            Integer userId = AuthRestController.user_id;
            List<CommentResponse> commentResponses = commentServiceImp.getByStudentId(userId);
            if (userId == 0) {
                return ApiResponse.<List<CommentResponse>>unAuthorized(CommentResponse.class.getSimpleName())
                        .setResponseMsg("Unauthorized");
            } else if (commentResponses.isEmpty()) {
                return ApiResponse.<List<CommentResponse>>notFound(CommentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            } else {
                return ApiResponse.<List<CommentResponse>>ok(CommentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                        .setData(commentResponses);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-teacherUserId/")
    ApiResponse<List<CommentByTeacherResponse>> getByTecherId() {
        try {
            Integer userId = AuthRestController.user_id;
            List<CommentByTeacherResponse> commentResponses = commentServiceImp.getByTecherId(userId);
            if (userId == 0) {
                return ApiResponse.<List<CommentByTeacherResponse>>unAuthorized(CommentByTeacherResponse.class.getSimpleName())
                        .setResponseMsg("Unauthorized");
            } else if (commentResponses.isEmpty()){
                return ApiResponse.<List<CommentByTeacherResponse>>notFound(CommentByTeacherResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
            } else {
                return ApiResponse.<List<CommentByTeacherResponse>>ok(CommentByTeacherResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                        .setData(commentResponses);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-claaId-classroomId-studentId/")
    ApiResponse<List<CommentByClassClassroomStudentResponse>> getByClassClassroomStudent(
            @RequestParam @Min(value = 1) Integer classroom_id,
            @RequestParam @Min(value = 1) Integer class_id,
            @RequestParam @Min(value = 1) Integer student_id
    ) {
        try {
            List<CommentByClassClassroomStudentResponse> commentResponses = commentServiceImp.getByClassClassroomStudent(classroom_id, class_id, student_id);
            if (commentResponses.isEmpty()) {
                return ApiResponse.<List<CommentByClassClassroomStudentResponse>>notFound(CommentByClassClassroomStudentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(null);
            }
            return ApiResponse.<List<CommentByClassClassroomStudentResponse>>ok(CommentByClassClassroomStudentResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(commentResponses);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @DeleteMapping("delete-by-id/")
    ApiResponse<Boolean> deleteById(@RequestParam @Min(value = 1) Integer id) {
        try {
            boolean checkCommentID = commentRepository.ifCommentIdExist(id);
            if (checkCommentID == false) {
                return ApiResponse.<Boolean>notFound(CommentResponse.class.getSimpleName())
                        .setResponseMsg("Can't delete! ID: " + id + " doesn't exist");
            }
            commentServiceImp.deleteById(id);
            return ApiResponse.<Boolean>ok("Comment with id:" + id)
                    .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                    .setData(true);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("insert")
    ApiResponse<CommentInsertRequest> insertComment(
            @RequestBody @Valid CommentInsertRequest commentInsertRequest
    ) {
        Integer userId = AuthRestController.user_id;
        boolean checkUserId = commentRepository.ifUserIdExist(userId);
        boolean checkMaterialsDetailId = commentRepository.ifMaterialsDetailIdExist(commentInsertRequest.getClass_materials_detail_id());
        try {
            if (checkMaterialsDetailId == false) {
                return ApiResponse.<CommentInsertRequest>notFound(CommentInsertRequest.class.getSimpleName())
                        .setResponseMsg("The Class_materials_detail_id: "+commentInsertRequest.getClass_materials_detail_id()+ " doesn't exist in the table");
            } else if (userId == 0) {
                return ApiResponse.unAuthorized("Unauthorized");
            } else if(checkUserId == false) {
                return ApiResponse.unAuthorized("Unauthorized");
            } else {
                commentInsertRequest.setComment(commentInsertRequest.getComment().trim());
                commentServiceImp.insert(commentInsertRequest, userId);
                return ApiResponse.<CommentInsertRequest>ok(CommentInsertRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(commentInsertRequest);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("update")
    ApiResponse<CommentUpdateRequest> update(
            @RequestBody @Valid CommentUpdateRequest commentUpdateRequest
    ) {
        try {
            boolean checkCommentID = commentRepository.ifCommentIdExist(commentUpdateRequest.getComment_id());
            if (checkCommentID == false) {
                return ApiResponse.<CommentUpdateRequest>notFound(CommentUpdateRequest.class.getSimpleName())
                        .setResponseMsg("Can't update! Comment ID: " + commentUpdateRequest.getComment_id() + " doesn't exist");
            }
            commentUpdateRequest.setComment(commentUpdateRequest.getComment().trim());
            commentServiceImp.update(commentUpdateRequest);
            return ApiResponse.<CommentUpdateRequest>ok(CommentUpdateRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                    .setData(commentUpdateRequest);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-materialId/")
    ApiResponse<List<CommentByMaterialIdResponse>> getByMaterialId(@RequestParam @Min(value = 1) Integer class_material_id) {
        try {
            List<CommentByMaterialIdResponse> commentResponses = commentServiceImp.getByMaterialId(class_material_id);
            if (commentResponses.isEmpty()) {
                return ApiResponse.<List<CommentByMaterialIdResponse>>notFound(CommentByMaterialIdResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(commentResponses);
            }
            return ApiResponse.<List<CommentByMaterialIdResponse>>ok(CommentByMaterialIdResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(commentResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }
}