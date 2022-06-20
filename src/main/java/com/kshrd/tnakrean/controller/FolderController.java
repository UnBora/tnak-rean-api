package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.FolderDetailRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import com.kshrd.tnakrean.model.classmaterials.response.FolderResponse;
import com.kshrd.tnakrean.service.serviceImplementation.FolderServiceImp;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/folder")
public class FolderController {
    final FolderServiceImp folderServiceImp;

    public FolderController(FolderServiceImp folderServiceImp) {
        this.folderServiceImp = folderServiceImp;
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

    @PostMapping("/createDetail")
    ApiResponse<FolderDetailRequest> createFolderDetail(@RequestBody FolderDetailRequest folderDetailRequest) {

        try {
            boolean folderDetailResponse = folderServiceImp.createFolderDetail(folderDetailRequest);
            System.out.println("in controller" + folderDetailResponse);
            return ApiResponse.<FolderDetailRequest>successCreate(FolderDetailRequest.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage()).setData(folderDetailRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    ApiResponse<FolderResponse> getFolderById(int id) {
        FolderResponse folderResponse = folderServiceImp.getFolderById(id);
        try {
            if (folderResponse == null) {
                return ApiResponse.<FolderResponse>notFound(FolderResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage()).setData(folderResponse);
            } else {
                return ApiResponse.<FolderResponse>ok(FolderResponse.class.getSimpleName()).setData(folderResponse);
            }
        } catch (Exception e) {
            return ApiResponse.badRequest(FolderResponse.class.getSimpleName());
        }
    }

    @GetMapping("/get-folders-by-class-id/{classId}/{classRoomId}")
    ApiResponse<List<FolderResponse>> getFolderByClassId(@RequestParam int classId, @RequestParam int classRoomId) {
        List<FolderResponse> responseList = folderServiceImp.getListFolderByClassId(classId,classRoomId);
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


    @GetMapping("/get-folders-by-teacher-id/{id}")
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

}
