package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.FolderDetailRequest;
import com.kshrd.tnakrean.model.classmaterials.request.FolderRequest;
import com.kshrd.tnakrean.model.classmaterials.response.FolderDetailResponse;
import com.kshrd.tnakrean.model.classmaterials.response.FolderResponse;
import com.kshrd.tnakrean.service.serviceImplementation.FolderServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/folder")
public class FolderController {
    final FolderServiceImp folderServiceImp;

    public FolderController(FolderServiceImp folderServiceImp) {
        this.folderServiceImp = folderServiceImp;
    }

    @PostMapping("/create")
    ApiResponse<FolderRequest> createFolder(@RequestBody FolderRequest folderRequest) {
        try {
            boolean t = folderServiceImp.createFolder(folderRequest);
            System.out.println(t);
            return ApiResponse.<FolderRequest>successCreate().setData(folderRequest);
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
            return ApiResponse.<FolderDetailRequest>successCreate().setData(folderDetailRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    ApiResponse<FolderResponse> getFolderByClassId(int id) {
        FolderResponse folderResponse = folderServiceImp.getFolderByClassId(id);
        System.out.println(BaseMessage.Success.INSERT_SUCCESS.getMessage());
        try {
            if (folderResponse == null) {
                return ApiResponse.<FolderResponse>notFound().setData(folderResponse);
            } else {
                return ApiResponse.<FolderResponse>ok().setData(folderResponse);
            }
        } catch (Exception e) {
            return ApiResponse.<FolderResponse>exception(e);
        }
    }


}
