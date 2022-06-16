package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classroom.request.ClassroomRequest;
import com.kshrd.tnakrean.model.classroom.response.ClassroomResponse;

import java.util.List;

public interface ClassroomService {

    List<ClassroomResponse> getAllClassroom();

    void insertClassroom(Integer class_id, Integer created_by, String des, String name);

    ClassroomResponse getClassroomByID(Integer id);
}
