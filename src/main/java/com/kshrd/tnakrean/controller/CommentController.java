package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.CommentInsertRequest;
import com.kshrd.tnakrean.model.classmaterials.request.CommentUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
import com.kshrd.tnakrean.model.classmaterials.response.CommentByClassClassroomStudentResponse;
import com.kshrd.tnakrean.model.classmaterials.response.CommentByMaterialIdResponse;
import com.kshrd.tnakrean.model.classmaterials.response.CommentResponse;
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

    @GetMapping("get-by-id/{id}")
    ApiResponse<CommentResponse> getById(@RequestParam @Min(value = 1) Integer id) {
        try {
            CommentResponse commentResponses = commentServiceImp.getById(id);
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

    @GetMapping("get-by-claaId-classroomId-studentId/{claaId}/{classroomId}/{studentId}")
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

    @DeleteMapping("delete-by-id/{id}")
    ApiResponse<Boolean> deleteById(@RequestParam @Min(value = 1) Integer id) {
        try {
            CommentResponse commentResponse = commentServiceImp.deleteById(id);
            if (commentResponse == null) {
                return ApiResponse.<Boolean>notFound(CommentResponse.class.getSimpleName())
                        .setResponseMsg("Can't delete! ID: " + id + " doesn't exist");
            }
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
        try {
            commentServiceImp.insert(commentInsertRequest);
            return ApiResponse.<CommentInsertRequest>ok(CommentInsertRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                    .setData(commentInsertRequest);
        } catch (Exception e) {
            return ApiResponse.<CommentInsertRequest>badRequest(CommentInsertRequest.class.getSimpleName())
                    .setResponseMsg("Can't insert comment because of violates foreign key constraint from student_id and class_materials_detail_id");
        }
    }

    @PutMapping("update")
    ApiResponse<CommentUpdateRequest> update(
            @RequestBody @Valid CommentUpdateRequest commentUpdateRequest
    ) {
        try {
            CommentUpdateRequest commentUpdateRequest1 = commentServiceImp.update(commentUpdateRequest);
            if (commentUpdateRequest1 == null) {
                return ApiResponse.<CommentUpdateRequest>notFound(CommentUpdateRequest.class.getSimpleName())
                        .setResponseMsg("Can't update! ID: "+commentUpdateRequest.getComment_id()+" doesn't exist");
            }
            return ApiResponse.<CommentUpdateRequest>ok(CommentUpdateRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                    .setData(commentUpdateRequest1);
        } catch (Exception e) {
            return ApiResponse.<CommentUpdateRequest>badRequest(CommentUpdateRequest.class.getSimpleName())
                    .setResponseMsg("Can't update! Because of violates foreign key constraint from student_id and class_materials_detail_id");
        }
    }

        @GetMapping("get-by-materialId/{materialId}")
        ApiResponse<List<CommentByMaterialIdResponse>> getByMaterialId (@RequestParam @Min(value = 1) Integer class_material_id){
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