package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classmaterials.request.GetClassRequest;
import com.kshrd.tnakrean.model.classmaterials.request.ClassroomRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassroomUpdateResponse;
import com.kshrd.tnakrean.model.classmaterials.response.ClassroomResponse;
import com.kshrd.tnakrean.model.classmaterials.response.GetClassByTeacherIdResponse;

import com.kshrd.tnakrean.model.user.response.GetAllStudentResponse;
import com.kshrd.tnakrean.model.user.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.repository.ClassroomRepository;
import com.kshrd.tnakrean.service.serviceImplementation.ClassroomServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classroom")
public class ClassroomController {

    final ClassroomServiceImp classroomServiceImp;
    final ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomController(ClassroomServiceImp classroomServiceImp, ClassroomRepository classroomRepository) {
        this.classroomServiceImp = classroomServiceImp;
        this.classroomRepository = classroomRepository;
    }

    @GetMapping("/get-all-classroom")
    public ApiResponse<List<ClassroomResponse>> getAllClassroom() {
        try {
            List<ClassroomResponse> classroomResponses = classroomServiceImp.getAllClassroom();
            if (classroomResponses.isEmpty()) {
                return ApiResponse.<List<ClassroomResponse>>ok(GetClassRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classroomResponses);
            } else {
                return ApiResponse.<List<ClassroomResponse>>ok(ClassroomResponse.class.getSimpleName())
                        .setData(classroomResponses);
            }

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-classroom-by-id")
    public ApiResponse<ClassroomResponse> getClassroomById(Integer id) {
        try {
            ClassroomResponse classroomResponses = classroomServiceImp.getClassroomByID(id);
            if (classroomResponses == null) {
                return ApiResponse.<ClassroomResponse>ok(ClassroomResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(null);
            } else {
                return ApiResponse.<ClassroomResponse>ok(ClassroomResponse.class.getSimpleName())
                        .setData(classroomResponses);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("/insert-classroom")
    public ApiResponse<ClassroomRequest> insertClassroom(ClassroomRequest classroomRequest) {
        try {
            Integer classId = classroomRequest.getClass_id(), createdby = classroomRequest.getCreated_by();
            String dec = classroomRequest.getDes(), name = classroomRequest.getName();
            classroomServiceImp.insertClassroom(classId, createdby, dec, name);
            if (classroomRequest == null) {
                return ApiResponse.<ClassroomRequest>setError(ClassroomRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.INSERT_ERROR.getMessage());
            } else {
                return ApiResponse.<ClassroomRequest>ok(ClassroomRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(new ClassroomRequest(classId, createdby, dec, name));
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/get-classroom-by-teacher-id")
    public  ApiResponse<List<GetClassByTeacherIdResponse>> getClassByTeacherId(){
        Integer userId=AuthRestController.user_id;
        GetClassByTeacherIdResponse obj = new GetClassByTeacherIdResponse();
        Integer classId= obj.getClass_id(), classroomId=obj.getClassroom_id();
        String teacher= obj.getTeacher_name(), className=obj.getClass_name();
        try {
            if (!userId.equals(0)){
                List<GetClassByTeacherIdResponse> getClassByTeacherIdResponses = classroomServiceImp.getClassByTeacherId(classId,classroomId,teacher,className,userId);
                if (getClassByTeacherIdResponses .isEmpty()) {
                    return ApiResponse.<List<GetClassByTeacherIdResponse>>setError(GetStudentByClassIDResponse.class.getSimpleName())
                            .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                            .setData(getClassByTeacherIdResponses );
                } else {
                    return ApiResponse.<List<GetClassByTeacherIdResponse>>ok(GetClassByTeacherIdResponse.class.getSimpleName())
                            .setData(getClassByTeacherIdResponses);
                }
            }else {
                return ApiResponse.<List<GetClassByTeacherIdResponse>>unAuthorized(GetClassByTeacherIdResponse.class.getSimpleName())
                        .setResponseMsg("Unauthorized!");
            }

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PutMapping("/update-classroom")
    public ApiResponse<ClassroomUpdateResponse> updateClassroom(ClassroomUpdateResponse classroomUpdateResponse) {
        try {
            classroomServiceImp.updateClassroom(classroomUpdateResponse.getClassroom_id(), classroomUpdateResponse.getCreated_by(), classroomUpdateResponse.getDes(), classroomUpdateResponse.getName());
            Boolean a = classroomRepository.checkIfClassExists(classroomUpdateResponse.getClassroom_id(), classroomUpdateResponse.getCreated_by());
            if (classroomUpdateResponse.equals(null)) {
                return ApiResponse.<ClassroomUpdateResponse>setError(GetAllStudentResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.UPDATE_ERROR.getMessage());
            } else if (a.equals(false)) {
                return ApiResponse.<ClassroomUpdateResponse>setError(GetAllStudentResponse.class.getSimpleName())
                        .setResponseMsg("Your classroom ID and classID Not Matched!");
            } else {
                return ApiResponse
                        .<ClassroomUpdateResponse>updateSuccess(ClassroomUpdateResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Success.UPDATE_SUCCESS.getMessage())
                        .setData(new ClassroomUpdateResponse(classroomUpdateResponse.getClassroom_id(), classroomUpdateResponse.getCreated_by(), classroomUpdateResponse.getDes(), classroomUpdateResponse.getName()));
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }


    }
}
