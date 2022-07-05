package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.response.GetClassByClassroomIDResponse;
import com.kshrd.tnakrean.model.classmaterials.response.ClassroomResponse;
import com.kshrd.tnakrean.model.classmaterials.response.GetClassByTeacherIdResponse;

import java.util.List;

public interface ClassroomService {

    List<ClassroomResponse> getAllClassroom();

    void insertClassroom(Integer created_by, String name, String des);

    ClassroomResponse getClassroomByID(Integer id);

    void updateClassroom( Integer classroom_id, Integer created_by, String name, String des);

    List<GetClassByClassroomIDResponse> getClassByClassroomID(Integer classroomId);

    List<GetClassByTeacherIdResponse> getClassByTeacherId(Integer classroom_id, Integer class_id, String teacher_name, String class_name, Integer user_id);
}
