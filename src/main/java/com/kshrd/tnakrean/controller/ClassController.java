package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.GetClassRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;
import com.kshrd.tnakrean.model.user.response.GetAllStudentResponse;
import com.kshrd.tnakrean.repository.ClassRepository;
import com.kshrd.tnakrean.service.serviceImplementation.ClassServiceImp;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/class")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
public class ClassController {

    final ClassServiceImp classServiceImp;
    final ClassRepository classRepository;

    @Autowired
    public ClassController(ClassServiceImp classServiceImp, ClassRepository classRepository) {
        this.classServiceImp = classServiceImp;
        this.classRepository = classRepository;
    }

    @PostMapping("/create-class")
    public ApiResponse<ClassInertResponse> insertClass(@RequestBody @Valid ClassInertResponse classInertResponse) {

        Boolean classNameCheck = classRepository.checkIfClassExistsDuplecateClassName(classInertResponse.getClassName().toUpperCase());
        System.out.println(classInertResponse.getClassName().toUpperCase());
        try {
            if (classInertResponse.equals(null)) {
                return ApiResponse.<ClassInertResponse>setError(ClassInertResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.INSERT_ERROR.getMessage())
                        .setData(new ClassInertResponse(classInertResponse.getClassName(), classInertResponse.getImage()));
            } else if (classNameCheck.equals(true)) {
                return ApiResponse.<ClassInertResponse>duplicateEntry(ClassInertResponse.class.getSimpleName())
                        .setResponseMsg("The class name already exists!")
                        .setData(new ClassInertResponse(classInertResponse.getClassName(),classInertResponse.getImage()));
            } else {
                classServiceImp.insertClass(classInertResponse.getClassName().toUpperCase(), classInertResponse.getImage());
                return ApiResponse.<ClassInertResponse>ok(ClassInertResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(new ClassInertResponse(classInertResponse.getClassName(),classInertResponse.getImage()));
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @DeleteMapping("/delete-class")
    public ApiResponse<ClassDeleteResponse> deleteClass(
            @RequestParam @Min(value = 1, message = "{validation.classId.notNegative}") Integer classId) {
        try {
            Boolean checkClassId = classRepository.checkIfClassExists(classId);
            if (classId == null) {
                return ApiResponse.<ClassDeleteResponse>setError(ClassDeleteResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.DELETE_ERROR.getMessage())
                        .setData(new ClassDeleteResponse(classId));
            } else if (checkClassId.equals(false)) {
                return ApiResponse.<ClassDeleteResponse>notFound(ClassDeleteResponse.class.getSimpleName())
                        .setResponseMsg("The Class ID:" + classId + " does not have!")
                        .setData(new ClassDeleteResponse(classId));
            } else {
                boolean del = false;
                try {
                    del = classServiceImp.deleteClass(classId);
                    if (del) {
                        return ApiResponse.<ClassDeleteResponse>ok(ClassDeleteResponse.class.getSimpleName())
                                .setResponseMsg("Class ID:"+ classId + " delete successfully")
                                .setData(new ClassDeleteResponse(classId));
                    }
                } catch (Exception e) {
                    return ApiResponse.<ClassDeleteResponse>ok(ClassDeleteResponse.class.getSimpleName())
                            .setResponseMsg("Class ID:"+classId + " cannot delete because of primary key");
                }
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
        return null;
    }

    @PutMapping("/update-class")
    public ApiResponse<ClassUpdateResponse> updateClassName(@RequestBody @Valid ClassUpdateResponse classUpdateResponse) {
        Boolean a = classRepository.checkIfClassExists(classUpdateResponse.getId());
        Boolean nameCheck = classRepository.checkIfClassExistsDuplecateClassName(classUpdateResponse.getClassname().toUpperCase());
        String oldClassName= classRepository.catchClassName(classUpdateResponse.getId());
        String newClassName= classUpdateResponse.getClassname();
        try {
            if (classUpdateResponse.equals(null)) {
                return ApiResponse.<ClassUpdateResponse>setError(GetAllStudentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage());
            } else if (a.equals(false)) {
                return ApiResponse.<ClassUpdateResponse>notFound(GetAllStudentResponse.class.getSimpleName())
                        .setResponseMsg("The Class ID:" + classUpdateResponse.getId() + " does not have!");
            } else if (!(nameCheck.equals(true)&&(oldClassName.equalsIgnoreCase(newClassName)))) {
                return ApiResponse.<ClassUpdateResponse>duplicateEntry(GetAllStudentResponse.class.getSimpleName())
                        .setResponseMsg("The class name already exists!");
            } else {
                classServiceImp.UpdateClass(classUpdateResponse.getId(), classUpdateResponse.getClassname().toUpperCase(),classUpdateResponse.getImage());
                String className = "Class ID: " + classUpdateResponse.getId() + " Class new name: " + classUpdateResponse.getClassname().toUpperCase();
                return ApiResponse
                        .<ClassUpdateResponse>ok(className)
                        .setResponseMsg(className+" update successfully")
                        .setData(new ClassUpdateResponse(classUpdateResponse.getId(), classUpdateResponse.getClassname().toUpperCase(),classUpdateResponse.getImage()));
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-all-class")
    public ApiResponse<List<GetClassRequest>> getAllStudent() {
        try {
            List<GetClassRequest> getClassRequests = classServiceImp.getAllClass();
            if (getClassRequests.isEmpty()) {
                return ApiResponse.<List<GetClassRequest>>ok(GetClassRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(getClassRequests);
            } else {
                return ApiResponse.<List<GetClassRequest>>ok(GetClassRequest.class.getSimpleName())
                        .setData(getClassRequests);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-class-by-studentUserId")
    public ApiResponse<List<ClassByStudentResponse>> getClassByStudent(
            @RequestParam @Min(value = 1) Integer user_id
    ) {
        try {
            List<ClassByStudentResponse> classByStudentResponses = classRepository.getClassByStudent(user_id);
            if (user_id == 0){
                return ApiResponse.unAuthorized("unAuthorized");
            } else if (classByStudentResponses.isEmpty()) {
                return ApiResponse.<List<ClassByStudentResponse>>ok(ClassByStudentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classByStudentResponses);
            } else {
                return ApiResponse.<List<ClassByStudentResponse>>ok(ClassByStudentResponse.class.getSimpleName())
                        .setData(classByStudentResponses);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-by-teacherUserId")
    public ApiResponse<List<ClassByUserTeacherIdResponse>> getByTeacherUserId(@RequestParam Integer classroom_id) {
        try {
            Integer user_id = AuthRestController.user_id;
            List<ClassByUserTeacherIdResponse> getClassRequests = classServiceImp.getByTeacherUserId(user_id,classroom_id);
            if (user_id == 0) {
                return ApiResponse.unAuthorized("Unauthorized");
            } else if (getClassRequests.isEmpty()) {
                return ApiResponse.<List<ClassByUserTeacherIdResponse>>notFound(ClassByUserTeacherIdResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(getClassRequests);
            }
            return ApiResponse.<List<ClassByUserTeacherIdResponse>>ok("Get All Class")
                    .setResponseMsg("Get All Class by teacherUserid: " + user_id + " Successfully")
                    .setData(getClassRequests);

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
    @GetMapping("get-sharedClass-by-folderId")
    public ApiResponse<List<SharedClassByFolderResponse>> getSharedClassByFolder(@RequestParam Integer folder_id) {
        try {
            List<SharedClassByFolderResponse> sharedClass = classServiceImp.getSharedClassByFolder(folder_id);
            if (sharedClass.isEmpty()) {
                return ApiResponse.<List<SharedClassByFolderResponse>>notFound(SharedClassByFolderResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(sharedClass);
            }
            return ApiResponse.<List<SharedClassByFolderResponse>>ok(SharedClassByFolderResponse.class.getSimpleName())
                    .setResponseMsg("Get All Class by folderId: " + folder_id + " Successfully")
                    .setData(sharedClass);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("get-notSharedClass-by-folderId")
    public ApiResponse<List<SharedClassByFolderResponse>> getNotSharedClassByFolder(@RequestParam Integer folder_id) {
        try {
            List<SharedClassByFolderResponse> sharedClass = classServiceImp.getNotSharedClassByFolder(folder_id);
            if (sharedClass.isEmpty()) {
                return ApiResponse.<List<SharedClassByFolderResponse>>notFound(SharedClassByFolderResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(sharedClass);
            }
            return ApiResponse.<List<SharedClassByFolderResponse>>ok(SharedClassByFolderResponse.class.getSimpleName())
                    .setResponseMsg("Get not shared Class by folderId: " + folder_id + " Successfully")
                    .setData(sharedClass);
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
    @GetMapping("/search-class")
    public ApiResponse<List<SearchClassResponse>> searchClass(@RequestParam String className) {
        try {
            List<SearchClassResponse> searchClass = classRepository.nameSearched(className);
            if (searchClass.isEmpty()) {
                return ApiResponse.<List<SearchClassResponse>>ok("Search Class")
                        .setResponseMsg("Don't have!")
                        .setData(searchClass);
            } else {
                return ApiResponse.<List<SearchClassResponse>>ok("Search Class")
                        .setResponseMsg("Your Class are!")
                        .setData(searchClass);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-class-by-id")
    public ApiResponse<GetClassRequest> getAllStudent(@RequestParam Integer classId) {
        try {
            GetClassRequest classResponse = classRepository.getClassByID(classId);
            if (classRepository.checkIfClassExists(classId).equals(false)) {
                return ApiResponse.<GetClassRequest>notFound("")
                        .setResponseMsg("The Class ID: "+classId+" not found!")
                        .setData(classResponse );
            } else {
                return ApiResponse.<GetClassRequest>ok(GetClassRequest.class.getSimpleName())
                        .setResponseMsg("The Class ID: "+classId+" had been found")
                        .setData(classResponse );
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}