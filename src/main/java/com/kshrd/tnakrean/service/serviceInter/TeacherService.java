package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
import com.kshrd.tnakrean.model.classroom.response.ClassroomResponse;
import com.kshrd.tnakrean.model.teacher.request.TeacherStatusRequest;
import com.kshrd.tnakrean.model.teacher.response.TeacherResponse;

import java.util.List;

public interface TeacherService {
    List<TeacherResponse> getAllTeacher();
    TeacherResponse getTeacherById(Integer user_id);

    List<ClassMaterialResponse> getAllMaterialByCreatedById(Integer createdId);

    List<ClassroomResponse> getAllClassRoomByTeacherId(Integer createdId);

    Boolean teacherStatus(TeacherStatusRequest teacherStatusRequest);

    Boolean teacherDeleteAccount(Integer user_id);

    Boolean deactivateTeacherAccount(Integer user_id);

    Boolean activateTeacherAccount(Integer user_id);
}
