package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
import com.kshrd.tnakrean.model.classmaterials.response.ClassroomResponse;
import com.kshrd.tnakrean.model.user.request.TeacherStatusRequest;
import com.kshrd.tnakrean.model.user.response.TeacherByClassAndClassroomResponse;
import com.kshrd.tnakrean.model.user.response.TeacherResponse;

import java.util.List;

public interface TeacherService {
    List<TeacherResponse> getAllTeacher();
    TeacherResponse getTeacherById(Integer user_id);

    Boolean teacherDeleteAccount(Integer user_id);

    Boolean deactivateTeacherAccount(Integer user_id);

    Boolean activateTeacherAccount(Integer user_id);

    List<TeacherByClassAndClassroomResponse> getByClassAndClassrooms(Integer user_id,Integer class_id, Integer classroom_id);
}
