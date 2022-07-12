package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.*;
import com.kshrd.tnakrean.model.classmaterials.response.*;
import com.kshrd.tnakrean.model.user.response.GetAllStudentResponse;
import com.kshrd.tnakrean.model.user.response.TeacherResponse;
import com.kshrd.tnakrean.repository.FolderRepository;
import com.kshrd.tnakrean.service.serviceImplementation.FolderServiceImp;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/get-courseFolders-by-class-id")
    ApiResponse<List<FolderByClassResponse>> getCourseFolderByClassId(
            @RequestParam @Min(value = 1) int classId,
            @RequestParam @Min(value = 1) int classRoomId) {
        List<FolderByClassResponse> responseList = folderServiceImp.getCourseFolderByClassId(classId, classRoomId);
        try {
            if (!responseList.isEmpty()) {
                return ApiResponse.<List<FolderByClassResponse>>
                                ok(FolderByClassResponse.class.getSimpleName()).
                        setData(responseList)
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage());
            }
            return ApiResponse.notFound(FolderByClassResponse.class.getSimpleName());
        } catch (Exception e) {
            return ApiResponse.badRequest(FolderByClassResponse.class.getSimpleName());
        }
    }

    @GetMapping("/get-classWorkFolders-by-class-id")
    ApiResponse<List<FolderByClassResponse>> getClassWorkFolderByClassId(
            @RequestParam @Min(value = 1) Integer classId,
            @RequestParam @Min(value = 1) Integer classRoomId) {
        List<FolderByClassResponse> responseList = folderServiceImp.getClassWorkFolderByClassId(classId, classRoomId);
        try {
            if (!responseList.isEmpty()) {
                return ApiResponse.<List<FolderByClassResponse>>ok(FolderByClassResponse.class.getSimpleName())
                        .setData(responseList)
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage());
            }
            return ApiResponse.<List<FolderByClassResponse>>notFound(FolderByClassResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(responseList);
        } catch (Exception e) {
            return ApiResponse.badRequest(FolderByClassResponse.class.getSimpleName());
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
    ApiResponse<List<FolderByTeacherResponse>> getCourseFolderByTeacher() {
        Integer user_id = AuthRestController.user_id;
        List<FolderByTeacherResponse> responseList = folderServiceImp.getCourseFolderByTeacher(user_id);
        try {
            if (!responseList.isEmpty()) {
                return ApiResponse.<List<FolderByTeacherResponse>>
                                ok(FolderResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage())
                        .setData(responseList);
            }
            return ApiResponse.<List<FolderByTeacherResponse>>notFound(FolderResponse.class.getSimpleName())
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                    .setData(responseList);
        } catch (Exception e) {
            return ApiResponse.badRequest(FolderResponse.class.getSimpleName());
        }
    }

    @GetMapping("/get-classworkFolder-by-teacherUserId")
    ApiResponse<List<FolderByTeacherResponse>> getClassworkFolderByteacherUserId() {
        Integer user_id = AuthRestController.user_id;
        List<FolderByTeacherResponse> responseList = folderServiceImp.getClassworkFolderByteacherUserId(user_id);
        try {
            if (!responseList.isEmpty()) {
                return ApiResponse.<List<FolderByTeacherResponse>>ok(FolderByTeacherResponse.class.getSimpleName())
                        .setData(responseList)
                        .setResponseMsg(BaseMessage.Success.SELECT_ALL_RECORD_SUCCESS.getMessage());
            }
            return ApiResponse.<List<FolderByTeacherResponse>>notFound(FolderByTeacherResponse.class.getSimpleName())
                    .setData(responseList)
                    .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage());
        } catch (Exception e) {
            return ApiResponse.badRequest(FolderResponse.class.getSimpleName());
        }
    }
    @PostMapping("/create-classWorkFolder")
    ApiResponse<FolderClassWorkRequest> createClassWorkFolder(
            @RequestBody @Valid FolderClassWorkRequest folderClassWorkRequest) {
        Integer userId = AuthRestController.user_id;
        boolean checkParentId = folderRepository.findParentId(folderClassWorkRequest.getParent_id());
        boolean checkMaterialTypeId = folderRepository.findMaterialTypeId(folderClassWorkRequest.getMaterial_type_id());
        try {
            if (userId == 0){
                return ApiResponse.unAuthorized("Unauthorized");
            } else if (checkParentId == false && folderClassWorkRequest.getParent_id() != null) {
                return ApiResponse.<FolderClassWorkRequest>notFound(FolderClassWorkRequest.class.getSimpleName())
                        .setResponseMsg("The parent_id: " + folderClassWorkRequest.getParent_id() + " doesn't exist in the table");
            } else if (checkMaterialTypeId == false) {
                return ApiResponse.<FolderClassWorkRequest>notFound(FolderClassWorkRequest.class.getSimpleName())
                        .setResponseMsg("The material_type_id: " + folderClassWorkRequest.getMaterial_type_id() + " doesn't exist in the table");
            } else {
                folderClassWorkRequest.setFolder_name(folderClassWorkRequest.getFolder_name().trim());
                folderServiceImp.createClassWorkFolder(folderClassWorkRequest,userId);
                return ApiResponse.<FolderClassWorkRequest>ok(FolderClassWorkRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(folderClassWorkRequest);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("/create-courseWorkFolder")
    ApiResponse<FolderCourseRequest> createCourseWorkFolder(
            @RequestBody @Valid FolderCourseRequest folderCourseRequest) {
        Integer userId = AuthRestController.user_id;
        boolean checkParentId = folderRepository.findParentId(folderCourseRequest.getParent_id());
        try {
            if (userId == 0){
                return ApiResponse.unAuthorized("Unauthorized");
            } else if (checkParentId == false && folderCourseRequest.getParent_id() != null) {
                return ApiResponse.<FolderCourseRequest>notFound(FolderCourseRequest.class.getSimpleName())
                        .setResponseMsg("The parent_id: " + folderCourseRequest.getParent_id() + " doesn't exist in the table");
            } else {
                folderCourseRequest.setFolder_name(folderCourseRequest.getFolder_name().trim());
                folderServiceImp.createCourseWorkFolder(folderCourseRequest,userId);
                return ApiResponse.<FolderCourseRequest>ok(FolderCourseRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(folderCourseRequest);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("assign-folder-in-any-class")
    public ApiResponse<FolderAssignClassRequest> folderAssignClass(
            @RequestBody @Valid FolderAssignClassRequest folderAssignClassRequest) {
        Boolean checkClassId = folderRepository.findClassId(folderAssignClassRequest.getClass_id());
        Boolean checkClassIdInCMF = folderRepository.findClassIdInCMF(folderAssignClassRequest.getClass_id());
        Boolean checkFolderIdInCMF = folderRepository.findFolderIdInCMF(folderAssignClassRequest.getFolder_id());
        Boolean checkFolderIdAndClassIdInCMF = folderRepository.findFolderIdAndClassIdInCMF(folderAssignClassRequest.getFolder_id(),folderAssignClassRequest.getClass_id());
        Boolean checkClassroomId = folderRepository.findClassroomId(folderAssignClassRequest.getClassroom_id());
        try {
            if (checkClassId == false) {
                return ApiResponse.<FolderAssignClassRequest>setError(FolderAssignClassRequest.class.getSimpleName())
                        .setResponseMsg("ClassId: "+folderAssignClassRequest.getClass_id()+" does not exist");
            } else if (checkFolderIdAndClassIdInCMF == true) {
                return ApiResponse.<FolderAssignClassRequest>notFound(FolderAssignClassRequest.class.getSimpleName())
                        .setResponseMsg("ClassIdInCMF:"+folderAssignClassRequest.getClass_id()+ " and FolderIdInCMF:"+folderAssignClassRequest.getFolder_id()+" already exist");
            }  else if (checkClassroomId == false) {
                return ApiResponse.<FolderAssignClassRequest>notFound(FolderAssignClassRequest.class.getSimpleName())
                        .setResponseMsg("ClassroomId: "+folderAssignClassRequest.getClassroom_id()+" does not exist");
            } else {
                folderServiceImp.folderAssignClass(folderAssignClassRequest);
                return ApiResponse.<FolderAssignClassRequest>ok(FolderAssignClassRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(folderAssignClassRequest);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
    @PostMapping("/create-courseFolder-in-class")
    ApiResponse<FolderInClassRequest> createCourseFolderInClass(@RequestBody @Valid FolderInClassRequest folder) {
        Integer userId = AuthRestController.user_id;
        boolean checkParentId = folderRepository.findParentId(folder.getParent_id());
        Boolean checkClassId = folderRepository.findClassId(folder.getClass_id());
        Boolean checkClassroomId = folderRepository.findClassroomId(folder.getClassroom_id());
        try {
            if (userId == 0){
                return ApiResponse.unAuthorized("Unauthorized");
            } else if (checkParentId == false && folder.getParent_id() != null) {
                return ApiResponse.<FolderInClassRequest>notFound(FolderInClassRequest.class.getSimpleName())
                        .setResponseMsg("The parent_id: " +folder.getParent_id()+ " doesn't exist in the table");
            } else if (checkClassId == false) {
                return ApiResponse.<FolderInClassRequest>setError(FolderInClassRequest.class.getSimpleName())
                        .setResponseMsg("ClassId: "+folder.getClass_id()+" does not exist");
            } else if (checkClassroomId == false) {
                return ApiResponse.<FolderInClassRequest>notFound(FolderInClassRequest.class.getSimpleName())
                        .setResponseMsg("ClassroomId: "+folder.getClassroom_id()+" does not exist");
            } else {
                folder.setFolder_name(folder.getFolder_name().trim());
                Integer folderId = folderRepository.createCourseFolderForClass(folder.getFolder_name(),folder.getParent_id(),userId);
                folderRepository.createCourseFolderInClass(folderId,folder.getClassroom_id(),folder.getClass_id());
                return ApiResponse.<FolderInClassRequest>ok(FolderInClassRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(folder);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
    @PostMapping("/create-classworkFolder-in-class")
    ApiResponse<FolderInClassRequest> createClassWorkFolderInClass(@RequestBody @Valid FolderInClassRequest folder
    ) {
        Integer userId = AuthRestController.user_id;
        boolean checkParentId = folderRepository.findParentId(folder.getParent_id());
        Boolean checkClassId = folderRepository.findClassId(folder.getClass_id());
        Boolean checkClassroomId = folderRepository.findClassroomId(folder.getClassroom_id());
        try {
            if (userId == 0){
                return ApiResponse.unAuthorized("Unauthorized");
            } else if (checkParentId == false && folder.getParent_id() != null) {
                return ApiResponse.<FolderInClassRequest>notFound(FolderInClassRequest.class.getSimpleName())
                        .setResponseMsg("The parent_id: " +folder.getParent_id()+ " doesn't exist in the table");
            } else if (checkClassId == false) {
                return ApiResponse.<FolderInClassRequest>setError(FolderInClassRequest.class.getSimpleName())
                        .setResponseMsg("ClassId: "+folder.getClass_id()+" does not exist");
            } else if (checkClassroomId == false) {
                return ApiResponse.<FolderInClassRequest>notFound(FolderInClassRequest.class.getSimpleName())
                        .setResponseMsg("ClassroomId: "+folder.getClassroom_id()+" does not exist");
            } else {
                folder.setFolder_name(folder.getFolder_name().trim());
                Integer folderId = folderRepository.createClassWorkFolderForClass(folder.getFolder_name(),folder.getParent_id(),userId);
                folderRepository.createClassWorkFolderInClass(folderId,folder.getClassroom_id(),folder.getClass_id());
                return ApiResponse.<FolderInClassRequest>ok(FolderInClassRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(folder);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
