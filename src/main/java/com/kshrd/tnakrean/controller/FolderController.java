package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.FolderDetailRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import com.kshrd.tnakrean.model.classmaterials.response.FolderDetailResponse;
import com.kshrd.tnakrean.model.classmaterials.response.FolderResponse;
import com.kshrd.tnakrean.repository.FolderRepository;
import com.kshrd.tnakrean.service.serviceImplementation.FolderServiceImp;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/folder")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
public class FolderController {
    final FolderServiceImp folderServiceImp;
    final FolderRepository folderRepository;

    public FolderController(FolderServiceImp folderServiceImp, FolderRepository folderRepository) {
        this.folderServiceImp = folderServiceImp;
        this.folderRepository = folderRepository;
    }

    @PostMapping("/create")
    ApiResponse<FolderRequest> createFolder(@RequestBody @Valid FolderRequest folderRequest) {
        try {
            boolean t = folderServiceImp.createFolder(folderRequest);
            System.out.println(t);
            return ApiResponse.<FolderRequest>successCreate(FolderDetailRequest.class.getSimpleName()).setData(folderRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.<FolderRequest>exception(e).setData(null);
        }

    }

//    @GetMapping("/get/{id}")
//    ApiResponse<FolderResponse> getFolderById(int id) {
//        FolderResponse folderResponse = folderServiceImp.getFolderById(id);
//        try {
//            if (folderResponse == null) {
//                return ApiResponse.<FolderResponse>notFound(FolderResponse.class.getSimpleName())
//                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage()).setData(folderResponse);
//            } else {
//                return ApiResponse.<FolderResponse>ok(FolderResponse.class.getSimpleName()).setData(folderResponse);
//            }
//        } catch (Exception e) {
//            return ApiResponse.badRequest(FolderResponse.class.getSimpleName());
//        }
//    }

    @GetMapping("/get-folders-by-class-id")
    ApiResponse<List<FolderResponse>> getFolderByClassId(@RequestParam int classId, @RequestParam int classRoomId) {
        List<FolderResponse> responseList = folderServiceImp.getListFolderByClassId(classId, classRoomId);
        try {
            if (!responseList.isEmpty()) {
                return ApiResponse.<List<FolderResponse>>
                                ok(FolderResponse.class.getSimpleName()).
                        setData(responseList)
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage());
            }
            return ApiResponse.notFound(FolderResponse.class.getSimpleName());
        } catch (Exception e) {
            return ApiResponse.badRequest(FolderResponse.class.getSimpleName());
        }
    }


    @GetMapping("/get-folders-by-teacher-id")
    ApiResponse<List<FolderResponse>> getFolderByClassId(@RequestParam int id) {
        List<FolderResponse> responseList = folderServiceImp.getListFolderByTeacherId(id);
        try {
            if (!responseList.isEmpty()) {
                return ApiResponse.<List<FolderResponse>>
                                ok(FolderResponse.class.getSimpleName()).
                        setData(responseList)
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage());
            }
            return ApiResponse.notFound(FolderResponse.class.getSimpleName());
        } catch (Exception e) {
            return ApiResponse.badRequest(FolderResponse.class.getSimpleName());
        }
    }

    @GetMapping("/get-folder-detail-by-folder-id")
    ApiResponse<List<FolderDetailResponse>> getFolderDetail(@RequestParam int id) {
        List<FolderDetailResponse> responseList = folderRepository.getFolderDetail(id);
        try {
            if (!responseList.isEmpty()) {
                return ApiResponse.<List<FolderDetailResponse>>
                                ok(FolderResponse.class.getSimpleName()).
                        setData(responseList)
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage());
            }
            return ApiResponse.notFound(FolderResponse.class.getSimpleName());
        } catch (Exception e) {
            return ApiResponse.badRequest(FolderResponse.class.getSimpleName());
        }
    }

    @DeleteMapping("delete-by-id")
        // delete child folder too
    ApiResponse<Boolean> deleteByParentId(@RequestParam @Min(value = 1) Integer parent_id) {
        FolderResponse folderResponse = folderServiceImp.deleteByParentId(parent_id);
        if (folderResponse == null) {
            return ApiResponse.<Boolean>notFound("Folder")
                    .setResponseMsg("Can't Delete! Folder Id: " + parent_id + " is not exist");
        }
        return ApiResponse.<Boolean>ok("Folder")
                .setResponseMsg("Delete folder on Id: " + parent_id + " successfully with their child id").setData(true);
    }

    @GetMapping("/get-courseFolder-by-teacherUserId")
    ApiResponse<List<FolderResponse>> getCourseFolderByTeacher(@RequestParam @Min(value = 1) Integer classroom_id
    ) {
        Integer user_id = AuthRestController.user_id;
        List<FolderResponse> responseList = folderServiceImp.getCourseFolderByTeacher(user_id,classroom_id);
        try {
            if (!responseList.isEmpty()) {
                return ApiResponse.<List<FolderResponse>>
                                ok(FolderResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                        .setData(responseList);
            }
            return ApiResponse.<List<FolderResponse>>notFound(FolderResponse.class.getSimpleName()).setData(responseList);
        } catch (Exception e) {
            return ApiResponse.badRequest(FolderResponse.class.getSimpleName());
        }
    }
}
