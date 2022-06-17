package com.kshrd.tnakrean.controller;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classModel.request.GetClassRequest;
import com.kshrd.tnakrean.model.classroom.request.ClassroomRequest;
import com.kshrd.tnakrean.model.classroom.response.ClassroomResponse;
import com.kshrd.tnakrean.model.classroom.response.GetClassByTeacherIdResponse;
import com.kshrd.tnakrean.model.student.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.repository.ClassroomRepository;
import com.kshrd.tnakrean.service.serviceImplementation.ClassroomServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/getAllClassroom")
    public ApiResponse<List<ClassroomResponse>> getAllClassroom() {
        try {
            List<ClassroomResponse> classroomResponses = classroomServiceImp.getAllClassroom();
            if (classroomResponses.isEmpty()) {
                return ApiResponse.<List<ClassroomResponse>>ok(GetClassRequest.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(classroomResponses);
            } else {
                return ApiResponse.<List<ClassroomResponse>>ok("Get All Classroom")
                        .setData(classroomResponses);
            }

        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/getClassroomID")
    public ApiResponse<ClassroomResponse> getClassroomById(Integer id) {
        try {
            ClassroomResponse classroomResponses = classroomServiceImp.getClassroomByID(id);
            if (classroomResponses == null) {
                return ApiResponse.<ClassroomResponse>ok(ClassroomResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(null);
            } else {
                return ApiResponse.<ClassroomResponse>ok("Get All Classroom By ID")
                        .setData(classroomResponses);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @PostMapping("/insertClassroom")
    public ApiResponse<ClassroomRequest> insertClassroom(ClassroomRequest classroomRequest) {
        try {
            Integer classId = classroomRequest.getClass_id(), createdby = classroomRequest.getCreated_by();
            String dec = classroomRequest.getDes(), name = classroomRequest.getName();
            classroomServiceImp.insertClassroom(classId, createdby, dec, name);
            if (classroomRequest == null) {
                return ApiResponse.<ClassroomRequest>setError("student class")
                        .setResponseMsg(BaseMessage.Error.INSERT_ERROR.getMessage())
                        .setData(null);
            } else {
                return ApiResponse.<ClassroomRequest>ok("student class")
                        .setResponseMsg(BaseMessage.Success.INSERT_SUCCESS.getMessage())
                        .setData(new ClassroomRequest(classId, createdby, dec, name));
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }

    @GetMapping("/getClassroomByTeacherID")
    public  ApiResponse<List<GetClassByTeacherIdResponse>> getClassByTeacherId(){
        Integer user_id=AuthRestController.user_id;
        GetClassByTeacherIdResponse obj = new GetClassByTeacherIdResponse();
        Integer classId= obj.getClass_id(), classroomId=obj.getClassroom_id();
        String teacher= obj.getTeacher_name(), className=obj.getClass_name();
        try {
            List<GetClassByTeacherIdResponse> getClassByTeacherIdResponses = classroomServiceImp.getClassByTeacherId(classId,classroomId,teacher,className,user_id);
            if (getClassByTeacherIdResponses .isEmpty()) {
                return ApiResponse.<List<GetClassByTeacherIdResponse>>setError(GetStudentByClassIDResponse.class.getSimpleName())
                        .setResponseMsg(BaseMessage.Error.SELECT_ERROR.getMessage())
                        .setData(getClassByTeacherIdResponses );
            } else {
                return ApiResponse.<List<GetClassByTeacherIdResponse>>ok(GetClassByTeacherIdResponse.class.getSimpleName())
                        .setData(getClassByTeacherIdResponses);
            }
        } catch (Exception e) {
            return ApiResponse.setError(e.getMessage());
        }
    }
}
