package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.CommentInsertRequest;
import com.kshrd.tnakrean.model.classmaterials.request.CommentUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
import com.kshrd.tnakrean.model.classmaterials.response.CommentByClassClassroomStudentResponse;
import com.kshrd.tnakrean.model.classmaterials.response.CommentResponse;
import com.kshrd.tnakrean.repository.CommentRepository;
import com.kshrd.tnakrean.service.serviceImplementation.CommentServiceImp;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    final CommentServiceImp commentServiceImp;
    final CommentRepository commentRepository;

    public CommentController(CommentServiceImp commentServiceImp, CommentRepository commentRepository) {
        this.commentServiceImp = commentServiceImp;
        this.commentRepository = commentRepository;
    }
    @GetMapping("get-all")
    ApiResponse<List<CommentResponse>> getAll(){
        try {
            List<CommentResponse> commentResponses = commentServiceImp.getAll();
            if (commentResponses.isEmpty()){
                return ApiResponse.<List<CommentResponse>>ok(CommentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(commentResponses);
            }
            return ApiResponse.<List<CommentResponse>>ok(CommentResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(commentResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }
    @GetMapping("get-by-id/{id}")
    ApiResponse<CommentResponse> getById(@RequestParam Integer id){
        try {
            CommentResponse commentResponses = commentServiceImp.getById(id);
            if (commentResponses == null){
                return ApiResponse.<CommentResponse>ok(CommentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(null);
            }
            return ApiResponse.<CommentResponse>ok(CommentResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ONE_RECORD_SUCCESS.getMessage())
                    .setData(commentResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }
    @GetMapping("get-by-claaId-classroomId-studentId/{claaId}/{classroomId}/{studentId}")
    ApiResponse<List<CommentByClassClassroomStudentResponse>> getByClassClassroomStudent(
            @RequestParam Integer classroom_id,
            @RequestParam Integer class_id,
            @RequestParam Integer student_id
            ){
        try {
            List <CommentByClassClassroomStudentResponse> commentResponses = commentServiceImp.getByClassClassroomStudent(classroom_id,class_id,student_id);
            if (commentResponses.isEmpty()){
                return ApiResponse.<List<CommentByClassClassroomStudentResponse>>ok(CommentByClassClassroomStudentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(null);
            }
            return ApiResponse.<List<CommentByClassClassroomStudentResponse>>ok(CommentByClassClassroomStudentResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                    .setData(commentResponses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }
    @DeleteMapping("delete-by-id/{id}")
    ApiResponse<Boolean> deleteById(@RequestParam Integer id){
        commentServiceImp.deleteById(id);
        return ApiResponse.<Boolean>ok("Comment with id:" +id)
                .setResponseMsg(BaseMessage.Success.DELETE_SUCCESS.getMessage())
                .setData(true);
    }

    @PostMapping("insert")
    ApiResponse<CommentInsertRequest> insertcomment(
            @RequestBody @Valid CommentInsertRequest commentInsertRequest
    ){
        commentServiceImp.insert(commentInsertRequest);
        return ApiResponse.<CommentInsertRequest>ok(CommentInsertRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                .setData(commentInsertRequest);
    }

    @PutMapping("update")
    ApiResponse<CommentUpdateRequest> update(
            @RequestBody @Valid CommentUpdateRequest commentUpdateRequest
    ){
        commentServiceImp.update(commentUpdateRequest);
        return ApiResponse.<CommentUpdateRequest>ok(CommentUpdateRequest.class.getSimpleName())
                .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                .setData(commentUpdateRequest);
    }
}